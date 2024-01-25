package com.iongroup.restraining.service;

import com.iongroup.restraining.dao.MessageEntDAO;
import com.iongroup.restraining.entity.MessageEntity;
import com.iongroup.restraining.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageEntService {

    private final MessageEntDAO messageEntDao;

    public MessageEntity save(MessageEntity message) {
        return messageEntDao.save(message);
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
    @Transactional
    public void deleteByTimestamp(Long time) {
        messageEntDao.deleteByTimestamp(new Timestamp(time));
    }

    public void delete(MessageEntity message) {
        messageEntDao.delete(message);
    }

    public MessageEntity findByTimestamp(Long time) {
        return messageEntDao.findByTimestamp(new Timestamp(time));
    }
}
