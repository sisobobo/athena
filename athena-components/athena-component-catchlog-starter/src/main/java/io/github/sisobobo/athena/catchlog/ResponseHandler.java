package io.github.sisobobo.athena.catchlog;

public interface ResponseHandler {

    public Object handle(Class returnType, String errCode, String errMsg);

}