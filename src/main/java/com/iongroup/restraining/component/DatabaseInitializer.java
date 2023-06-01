package com.iongroup.restraining.component;

import com.iongroup.restraining.entity.MessageEntity;
import com.iongroup.restraining.entity.RoomEntity;
import com.iongroup.restraining.entity.UserEntity;
import com.iongroup.restraining.service.MessageEntService;
import com.iongroup.restraining.service.RoomService;
import com.iongroup.restraining.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final UserEntity user1;
    private final UserEntity user2;
    private final UserEntity user3;

    private final RoomEntity room1;
    private final RoomEntity room2;

    private final MessageEntity message1;
    private final MessageEntity message2;

    private final UserService service;
    private final RoomService roomService;
    private final MessageEntService messageEntService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);

        room1.setUser1(user1);
        room1.setUser2(user2);

        roomService.insert(room1);

        room2.setUser1(user1);
        room2.setUser2(user3);

        roomService.insert(room2);

        message1.setRoom(room1);
        message1.setUser(user1);
        message1.setText("Hello1");
        message1.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        messageEntService.save(message1);

        message2.setRoom(room1);
        message2.setUser(user2);
        message2.setText("Hello2");
        message2.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        messageEntService.save(message2);

//        System.out.println(messageEntService.findAllMessagesInChatRoom(room1.getId(), user1));
//        System.out.println(messageEntService.findMessagesByUsers(user1.getUsername(),user3.getUsername()));
//        System.out.println(service.findAllUsersThatPrincipleKnows(user1.getUsername()));
//        System.out.println(roomService.findByUsersUsernames("n1kry2","n1kry"));
    }
}
