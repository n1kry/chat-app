package com.endava.restraining.config;

import com.endava.restraining.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserEntity user1() {
        UserEntity user = new UserEntity();
        user.setUsername("n1kry");
        user.setEmail("1@mail.com");
        user.setPassword("1");

        return user;
    }

    @Bean
    public UserEntity user2() {
        UserEntity user = new UserEntity();
        user.setUsername("n1kry2");
        user.setEmail("2@mail.com");
        user.setPassword("1");

        return user;
    }

    @Bean
    public UserEntity user3() {
        UserEntity user = new UserEntity();
        user.setUsername("n1kry3");
        user.setEmail("3@mail.com");
        user.setPassword("1");

        return user;
    }
}
