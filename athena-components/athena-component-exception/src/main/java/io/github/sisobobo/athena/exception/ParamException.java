package io.github.sisobobo.athena.exception;

/**
 * 参数类异常
 */
public class ParamException extends BizException {

    private static final String PARAM_ERROR_CDOE = "PARAM_ERROR";

    public ParamException(String errMessage) {
        super(PARAM_ERROR_CDOE, errMessage);
    }

}
