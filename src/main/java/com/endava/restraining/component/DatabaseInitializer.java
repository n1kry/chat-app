package com.endava.restraining.component;

import com.endava.restraining.entity.UserEntity;
import com.endava.restraining.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final UserEntity user1;
    private final UserEntity user2;
    private final UserEntity user3;

    private final UserService service;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);
    }
}
