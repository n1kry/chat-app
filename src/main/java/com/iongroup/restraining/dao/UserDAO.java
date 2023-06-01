package com.iongroup.restraining.dao;

import com.iongroup.restraining.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    List<UserEntity> findAllByUsernameNot(String username);

    @Query("SELECT CASE " +
            "WHEN r.user1.username = :username THEN r.user2.id " +
            "WHEN r.user2.username = :username THEN r.user1.id " +
            "END " +
            "FROM RoomEntity r " +
            "WHERE r.user1.username = :username OR r.user2.username = :username")
    List<Long> findConversationParticipantIdsByUsername(@Param("username") String username);
}
