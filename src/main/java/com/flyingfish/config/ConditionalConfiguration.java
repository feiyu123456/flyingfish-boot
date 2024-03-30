package com.flyingfish.config;

import com.flyingfish.condition.CustomizeCondition;
import com.flyingfish.service.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalConfiguration {

    @Conditional(CustomizeCondition.class)
    @Bean
    public PersonService personService(){
        return new PersonService();
    }
}
