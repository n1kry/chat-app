package com.iongroup.restraining.dao;

import com.iongroup.restraining.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageEntDao extends JpaRepository<MessageEntity, Integer> {
}
