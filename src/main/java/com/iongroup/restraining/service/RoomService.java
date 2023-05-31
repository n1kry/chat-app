package com.iongroup.restraining.service;

import com.iongroup.restraining.dao.RoomDAO;
import com.iongroup.restraining.entity.RoomEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomDAO roomDAO;

    public void save(RoomEntity room) {
        roomDAO.save(room);
    }

    public RoomEntity insert(RoomEntity room) {
        Long user1Id = room.getUser1().getId();
        Long user2Id = room.getUser2().getId();

        // Проверяем наличие комнаты с зеркальным сходством идентификаторов пользователей
        if (roomDAO.existsByUser1_IdAndUser2_Id(user2Id, user1Id)) {
            throw new IllegalArgumentException("Комната уже существует");
        }

        return roomDAO.save(room);
    }
}
