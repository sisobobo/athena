package io.github.sisobobo.exception;

public class DaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DaoException(Throwable e) {
        super(e);
    }

}