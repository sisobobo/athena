package io.github.sisobobo.athena.validator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
public class ValidateAutoConfiguration {


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ValidateAdvisor validateAdvisor() {
        ValidateAdvisor validateAdvisor = new ValidateAdvisor();
        validateAdvisor.setAdvice(new ValidateInterceptor());
        return validateAdvisor;
    }


}
