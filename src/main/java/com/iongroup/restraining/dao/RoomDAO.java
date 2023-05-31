package com.iongroup.restraining.dao;

import com.iongroup.restraining.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDAO extends JpaRepository<RoomEntity, Integer> {
    public Boolean existsByUser1_IdAndUser2_Id(Long user1Id, Long user2Id);
}
