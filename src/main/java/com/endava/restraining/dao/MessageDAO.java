package com.endava.restraining.dao;

import com.endava.restraining.entity.Message;
import com.endava.restraining.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderAndRecipientOrSenderAndRecipient(String sender, String recipient, String recipientReverse, String senderReverse);
}
