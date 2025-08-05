package com.flyingfish.config;

import com.flyingfish.condition.CustomizeCondition;
import com.flyingfish.interfacecustom.TestInterface;
import com.flyingfish.interfacecustom.UserMapper;
import com.flyingfish.service.IUserService;
import com.flyingfish.service.PersonService;
import com.flyingfish.service.TestService;
import com.flyingfish.service.impl.UserServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(prefix = "button.operate", name = "enable", havingValue = "true")
@Import({DynamicControllerRegistrar.class})
public class ConditionalConfiguration {

    @Conditional(CustomizeCondition.class)
    @Bean
    public PersonService personService(){
        return new PersonService();
    }

    @Bean
    public TestInterface getTestInterface(){
        return new TestService();
    }

//    @Bean
//    @ConditionalOnProperty(prefix = "button.operate", name = "enable", havingValue = "true")
//    public IUserService getUserService(UserMapper userMapper) {
//        return new UserServiceImpl(userMapper);
//    }
}
