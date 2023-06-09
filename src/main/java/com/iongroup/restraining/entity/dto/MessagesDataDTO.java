package com.iongroup.restraining.entity.dto;

import com.iongroup.restraining.entity.MessageEntity;
import com.iongroup.restraining.entity.RoomEntity;
import com.iongroup.restraining.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MessagesDataDTO {
    private Long id;

    private RoomDTO room;

    private UserDTO user;

    private String text;

    private Timestamp timestamp;

    public static MessagesDataDTO getMessageDataDtoFromMessageEntity(MessageEntity message) {
        return new MessagesDataDTO(
                message.getId(),
                RoomDTO.getRoomDtoFromRoom(message.getRoom()),
                UserDTO.getUserDtoFromUser(message.getUser()),
                message.getText(),
                message.getTimestamp()
        );
    }
}
