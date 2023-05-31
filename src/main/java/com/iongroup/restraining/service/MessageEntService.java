package com.iongroup.restraining.service;

import com.iongroup.restraining.dao.MessageEntDao;
import com.iongroup.restraining.entity.MessageEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageEntService {

    private final MessageEntDao messageEntDao;

    public void save(MessageEntity message) {
        messageEntDao.save(message);
    }
}
