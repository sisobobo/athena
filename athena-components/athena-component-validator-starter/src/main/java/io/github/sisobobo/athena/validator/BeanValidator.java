package io.github.sisobobo.athena.validator;


import com.alibaba.fastjson2.JSON;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanValidator {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * @param object
     * @param <T>
     */
    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(object);
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return;
        }
        throw new ValidationException(convertErrorMsg(constraintViolations));
    }

    public static <T> void validate(T object, Class<?>... group) {
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(object, group);
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return;
        }
        throw new ValidationException(convertErrorMsg(constraintViolations));
    }

    public static <T> String convertErrorMsg(Set<ConstraintViolation<T>> set) {
        Map<String, StringBuilder> errorMap = new HashMap<String, StringBuilder>();
        for (ConstraintViolation<T> cv : set) {
            String property = cv.getPropertyPath().toString();
            if (errorMap.get(property) != null) {
                StringBuilder sb = errorMap.get(property);
                sb.append("," + cv.getMessage());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(cv.getMessage());
                errorMap.put(property, sb);
            }
        }
        return JSON.toJSONString(errorMap);
    }
}
