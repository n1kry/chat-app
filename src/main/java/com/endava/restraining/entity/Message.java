package com.endava.restraining.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Entity
@Setter
@Getter
public class Message {
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