package com.iongroup.restraining.dao;

import com.iongroup.restraining.entity.RoomEntity;
import com.iongroup.restraining.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDAO extends JpaRepository<RoomEntity, Integer> {
    Boolean existsByUser1_IdAndUser2_Id(Long user1Id, Long user2Id);
    @Query("SELECT c FROM RoomEntity c WHERE (c.user1.username = :user1 AND c.user2.username = :user2) OR (c.user2.username = :user1 AND c.user1.username = :user2)")
    RoomEntity findByUser1UsernameAndUser2UsernameOrUser2UsernameAndUser1Username(@Param("user1") String user1, @Param("user2") String user2);
}
