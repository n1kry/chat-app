package com.iongroup.restraining.service;

import com.iongroup.restraining.dao.MessageEntDao;
import com.iongroup.restraining.entity.MessageEntity;
import com.iongroup.restraining.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageEntService {

    private final MessageEntDao messageEntDao;

    public void save(MessageEntity message) {
        messageEntDao.save(message);
    }

    public List<MessageEntity> findAllMessagesInChatRoom(Long roomId, UserEntity user) {
        return messageEntDao.findMessagesByRoomAndUser(roomId, user);
    }

    public List<MessageEntity> findMessagesByUsersNames(String currentUser, String recipient) {
        return messageEntDao.findMessagesByUsersNames(currentUser, recipient);
    }

    public List<MessageEntity> findMessagesByUsersId(Long currentUser, Long recipient) {
        return messageEntDao.findMessagesByUsersId(currentUser, recipient);
    }
}
