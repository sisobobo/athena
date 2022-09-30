package io.github.sisobobo.athena.catchlog;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class CatchLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CatchLogAspect.class)
    public CatchLogAspect catchLogAspect(ResponseHandler responseHandler) {
        return new CatchLogAspect(responseHandler);
    }

    @Bean
    @ConditionalOnMissingBean(ResponseHandler.class)
    public ResponseHandler responseHandler(){
        return new DefaultResponseHandler();
    }

}