package io.github.sisobobo.athena.validator;


import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AthenaValid {

    Class<?>[] groups() default {};

    Class<?>[] excludeGroups() default {};

    boolean isFailFast() default true;

}
