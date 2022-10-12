package io.github.sisobobo.athena.exception;

public class DaoException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "DAO_ERROR";

    public DaoException(Throwable e) {
        super(DEFAULT_ERR_CODE, e.getMessage(), e);
    }

}