package org.Akhil.common.config.security;

import org.Akhil.common.model.UserRequestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public UserRequestContext userRequestContext(){
        return  new UserRequestContext();
    }
}
