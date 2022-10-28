package io.github.sisobobo.athena.validator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

public class ValidateInterceptor implements MethodInterceptor {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> targetClass = getTargetClass(invocation.getThis());
        Assert.notNull(targetClass, "targetClass不能为空");
        Method method = invocation.getMethod();
        AthenaValid athenaValid = ValidateHolder.getInstance().getAthenaValid(method, targetClass);
        if (Objects.nonNull(athenaValid)) {
            Object[] arguments = invocation.getArguments();
            for (Object obj : arguments) {
                BeanValidator.validate(obj);
            }
        }
        return invocation.proceed();
    }

    private Class<?> getTargetClass(Object obj) {
        return AopProxyUtils.ultimateTargetClass(obj);
    }


}
