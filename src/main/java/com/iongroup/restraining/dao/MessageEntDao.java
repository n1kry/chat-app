package com.iongroup.restraining.dao;

import com.iongroup.restraining.entity.MessageEntity;
import com.iongroup.restraining.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageEntDao extends JpaRepository<MessageEntity, Integer> {
    @Query("SELECT m FROM MessageEntity m WHERE m.room.id = :roomId " +
            "AND (m.user = :user OR m.room.user1 = :user OR m.room.user2 = :user)")
    List<MessageEntity> findMessagesByRoomAndUser(@Param("roomId") Long roomId, @Param("user") UserEntity user);

    @Query("SELECT m FROM MessageEntity m JOIN m.room r " +
            "WHERE ((r.user1.username = :currentUser AND r.user2.username = :recipient) OR " +
            "(r.user1.username = :recipient AND r.user2.username = :currentUser)) " +
            "AND (m.user.username = :currentUser OR m.user.username = :recipient) " +
            "ORDER BY m.timestamp ASC")
    List<MessageEntity> findMessagesByUsers(@Param("currentUser") String currentUser, @Param("recipient") String recipient);
}
