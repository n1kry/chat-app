package com.iongroup.restraining.entity.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UpdateMessageDTO {
    Long recipient;
    String text;
}
