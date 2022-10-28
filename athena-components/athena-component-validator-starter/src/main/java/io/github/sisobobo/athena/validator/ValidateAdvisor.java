package io.github.sisobobo.athena.validator;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

class ValidateAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    @Override
    public Pointcut getPointcut() {
        return new ValidatePointCut();
    }

}
