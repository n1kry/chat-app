package com.iongroup.restraining.entity.dto;

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
public class MessageDTO {
    private String sender;

    private String recipient;

    private String text;

    private Timestamp timestamp;
}
