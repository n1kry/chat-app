package com.endava.restraining.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ToString.Include
    private String sender;

    @ToString.Include
    private String recipient;

    @ToString.Include
    @Column(columnDefinition = "text")
    @NotBlank
    private String text;

//    @ToString.Include
//    private Date date;
}