package org.Akhil.common.config.security.config;

import org.Akhil.common.model.UserRequestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public UserRequestContext userRequestContext(){
        return  new UserRequestContext();
    }

    @Bean
    public FeignClientConfig feignClientConfig(){
        return new FeignClientConfig();
    }
}
