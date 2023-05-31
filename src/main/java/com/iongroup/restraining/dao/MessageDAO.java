package com.iongroup.restraining.dao;

import com.iongroup.restraining.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageDAO extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderAndRecipientOrSenderAndRecipient(String sender, String recipient, String recipientReverse, String senderReverse);
}
