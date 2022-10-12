package io.github.sisobobo.athena.catchlog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.github.sisobobo.athena.exception.BizException;
import io.github.sisobobo.athena.exception.DaoException;
import io.github.sisobobo.athena.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

@Aspect
@Slf4j
@Order(1)
public class CatchLogAspect {

    private final ResponseHandler responseHandler;

    public CatchLogAspect(final ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    /**
     * The syntax of pointcut : https://blog.csdn.net/zhengchao1991/article/details/53391244
     */
    @Pointcut("@within(CatchLog) && execution(public * *(..))")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        Object response = null;
        try {
            response = joinPoint.proceed();
        } catch (Throwable e) {
            response = handleException(joinPoint, e);
        } finally {
            logResponse(startTime, response);
        }

        return response;
    }

    private Object handleException(ProceedingJoinPoint joinPoint, Throwable e) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Class returnType = ms.getReturnType();

        if (e instanceof BizException) {
            log.warn("业务异常 : " + e.getMessage());
            //在Debug的时候，对于BizException也打印堆栈
            if (log.isDebugEnabled()) {
                log.error(e.getMessage(), e);
            }
            return responseHandler.handle(returnType, ((BizException) e).getErrCode(), e.getMessage());
        }
        //记录错误的请求信息
        logRequest(joinPoint);

        if (e instanceof DaoException) {
            log.error("数据库异常 :");
            log.error(e.getMessage(), e);
            return responseHandler.handle(returnType, ((DaoException) e).getErrCode(), e.getMessage());
        }

        if (e instanceof SysException) {
            log.error("系统异常 :");
            log.error(e.getMessage(), e);
            return responseHandler.handle(returnType, ((SysException) e).getErrCode(), e.getMessage());
        }

        log.error("未知异常 :");
        log.error(e.getMessage(), e);

        return responseHandler.handle(returnType, "UNKNOWN_ERROR", e.getMessage());
    }

    private void logResponse(long startTime, Object response) {
        try {
            long endTime = System.currentTimeMillis();
            log.debug("结果 : " + JSON.toJSONString(response));
            log.debug("耗时 : " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            //swallow it
            log.error("logResponse error : " + e);
        }
    }

    private void logRequest(ProceedingJoinPoint joinPoint) {
        try {
            log.error("执行方法: " + joinPoint.getSignature().toShortString());
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                log.error("参数 : " + JSON.toJSONString(arg, SerializerFeature.IgnoreErrorGetter));
            }
        } catch (Exception e) {
            //swallow it
            log.error("logReqeust error : " + e);
        }
    }
}
