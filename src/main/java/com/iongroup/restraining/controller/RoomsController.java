package com.iongroup.restraining.controller;

import com.iongroup.restraining.entity.dto.RoomDTO;
import com.iongroup.restraining.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class RoomsController {
    private final RoomService roomService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/fetchallrooms")
    public List<RoomDTO> fetchAll(Model model, Principal principal) {
        return roomService.findRoomsByUser(principal.getName());
    }

    @GetMapping("/writetofound")
    public RoomDTO writeToFoundUser(Model model, @RequestParam Long principalId, @RequestParam Long recipientId) {
        simpMessagingTemplate.convertAndSend("/topic/newdialog/"+recipientId,principalId);
        return RoomDTO.getRoomDtoFromRoom(roomService.insert(principalId, recipientId));
    }
}
