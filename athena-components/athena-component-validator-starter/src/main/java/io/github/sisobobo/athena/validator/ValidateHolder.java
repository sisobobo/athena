package io.github.sisobobo.athena.validator;

import org.springframework.core.MethodClassKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ValidateHolder {

    private ValidateHolder() {

    }

    public static ValidateHolder getInstance() {
        return ValidateHolderInstance.INSTANCE;
    }

    private static class ValidateHolderInstance {
        private static final ValidateHolder INSTANCE = new ValidateHolder();
    }

    private final Map<MethodClassKey, AthenaValid> attributeCache = new ConcurrentHashMap(1024);

    public AthenaValid getAthenaValid(Method method, Class<?> targetClass) {
        MethodClassKey methodClassKey = new MethodClassKey(method, targetClass);
        AthenaValid athenaValid = attributeCache.get(methodClassKey);
        if (Objects.isNull(athenaValid)) {
            athenaValid = getAthenaValid(method);
            if (Objects.nonNull(athenaValid)) {
                attributeCache.put(methodClassKey, athenaValid);
            }
        }
        return athenaValid;
    }

    private AthenaValid getAthenaValid(Method method) {
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        if (paramAnnotations != null) {
            for (Annotation[] annotations : paramAnnotations) {
                if (Objects.nonNull(annotations) && annotations.length > 0) {
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType() == AthenaValid.class) {
                            return (AthenaValid) annotation;
                        }
                    }
                }
            }
        }
        return null;
    }

}
