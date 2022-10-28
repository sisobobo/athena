package io.github.sisobobo.athena.validator;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import java.lang.reflect.Method;
import java.util.Objects;

class ValidatePointCut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        boolean match = isMatch(method , targetClass);
        if(match){
            System.out.println(targetClass + " , " + method);
        }
        return match;
    }

    private boolean isMatch(Method method, Class<?> targetClass) {
        return Objects.nonNull(ValidateHolder.getInstance().getAthenaValid(method, targetClass));
    }

}

