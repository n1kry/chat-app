package com.iongroup.restraining.entity.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class DeleteMessageDTO {
    private Long principal;

    private Long recipient;

    private Long timestamp;
}
