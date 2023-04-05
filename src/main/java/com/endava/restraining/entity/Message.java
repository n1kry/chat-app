package com.endava.restraining.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Entity
@Setter
@Getter
public class Message {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String text;
//
//    @ManyToOne
//    @JoinColumn(name = "sender_id")
//    private UserEntity sender;
//
//    @ManyToOne
//    @JoinColumn(name = "receiver_id")
//    private UserEntity receiver;
//
//    @Column(nullable = false)
//    private LocalDateTime timestamp;
    private String username;
    private String text;

    public Message() {}

    public Message(String username, String text) {
        this.username = username;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}