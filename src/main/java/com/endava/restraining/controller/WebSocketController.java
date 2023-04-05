package com.endava.restraining.controller;

import com.endava.restraining.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {
    private final ChatService chatService;

    public WebSocketController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    public void sendMessage(@Payload String text, Principal principal) {
        System.out.println(principal.getName());
        chatService.sendMessage(text, principal);
    }
}