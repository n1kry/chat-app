package com.iongroup.restraining.service;

import com.iongroup.restraining.dao.RoomDAO;
import com.iongroup.restraining.entity.RoomEntity;
import com.iongroup.restraining.entity.UserEntity;
import com.iongroup.restraining.entity.dto.RoomDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomDAO roomDAO;

    private final UserService userService;

    public void save(RoomEntity room) {
        Long user1Id = room.getUser1().getId();
        Long user2Id = room.getUser2().getId();

        // Проверяем наличие комнаты с зеркальным сходством идентификаторов пользователей
        if (roomDAO.existsByUser1_IdAndUser2_Id(user2Id, user1Id)) {
            throw new IllegalArgumentException("Комната уже существует");
        }

        roomDAO.save(room);
    }

    public RoomEntity insert(Long user1Id, Long user2Id) {

        RoomEntity room = roomDAO.findByUser1_IdAndUser2_Id(user1Id, user2Id);

        // Проверяем наличие комнаты с зеркальным сходством идентификаторов пользователей
        if (room != null) {
            return room;
        }

        UserEntity user1 = userService.findById(user1Id);
        UserEntity user2 = userService.findById(user2Id);

        room = new RoomEntity();
        room.setUser1(user1);
        room.setUser2(user2);

        return roomDAO.save(room);
    }

    public RoomEntity findByUsersUsernames(String user1, String user2) {
        return roomDAO.findByUser1UsernameAndUser2UsernameOrUser2UsernameAndUser1Username(user1, user2);
    }

    public RoomEntity findByUsersIds(Long user1, Long user2) {
        return roomDAO.findByUser1IdAndUser2IdOrUser2IdAndUser1Id(user1, user2);
    }

    public List<RoomDTO> findRoomsByUser(String userName) {
        return roomDAO.findRoomsByUser(userName).stream().map(RoomDTO::getRoomDtoFromRoom).toList();
    }
}
