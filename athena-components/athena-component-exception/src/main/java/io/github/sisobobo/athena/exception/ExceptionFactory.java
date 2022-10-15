package io.github.sisobobo.athena.exception;

/**
 * 异常工厂实现
 */
public class ExceptionFactory {

    public static BizException bizException(Error error) {
        return new BizException(error.errorCode(), error.errorMsg());
    }

    public static BizException bizException(String errorMessage) {
        return new BizException(errorMessage);
    }

    public static BizException bizException(String errorCode, String errorMessage) {
        return new BizException(errorCode, errorMessage);
    }

    public static SysException sysException(Error error) {
        return new SysException(error.errorCode(), error.errorMsg());
    }

    public static SysException sysException(String errorMessage) {
        return new SysException(errorMessage);
    }

    public static SysException sysException(String errorCode, String errorMessage) {
        return new SysException(errorCode, errorMessage);
    }

    public static SysException sysException(String errorMessage, Throwable e) {
        return new SysException(errorMessage, e);
    }

    public static SysException sysException(String errorCode, String errorMessage, Throwable e) {
        return new SysException(errorCode, errorMessage, e);
    }

}