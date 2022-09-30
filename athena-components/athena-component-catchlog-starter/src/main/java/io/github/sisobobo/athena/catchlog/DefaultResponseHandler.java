package io.github.sisobobo.athena.catchlog;

import io.github.sisobobo.athena.dto.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultResponseHandler implements ResponseHandler {

    @Override
    public Object handle(Class returnType, String errCode, String errMsg) {
        if (isAthenaResponse(returnType)) {
            return handleAthenaResponse(returnType, errCode, errMsg);
        }
        return null;
    }


    private static Object handleAthenaResponse(Class returnType, String errCode, String errMsg) {
        try {
            Response response = (Response) returnType.newInstance();
            response.setSuccess(false);
            response.setErrCode(errCode);
            response.setErrMessage(errMsg);
            return response;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    private static boolean isAthenaResponse(Class returnType) {
        return returnType == Response.class || returnType.getGenericSuperclass() == Response.class;
    }
}