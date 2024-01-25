package com.iongroup.restraining.controller;

import com.iongroup.restraining.entity.UserEntity;
import com.iongroup.restraining.entity.dto.UserDTO;
import com.iongroup.restraining.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class UsersController {

    private final UserService userService;

//    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/fetchallusers")
    public List<UserDTO> fetchAll(Model model, Principal principal, @RequestParam String searchTerm) {
        return userService.findUsersWithoutRoomByUser(principal.getName(), searchTerm);
    }

    @GetMapping("/fetchknownusers")
    public List<UserDTO> fetchKnown(Model model, Principal principal) {
        return userService.findAllUsersThatPrincipalKnows(principal.getName());
    }

    @GetMapping("/getprincipal")
    public UserDTO getPrincipal(Model model, Principal principal) {
        UserEntity user = userService.findByUsername(principal.getName());
//        simpMessagingTemplate.convertAndSend("/topic/getprinc","Hello");

        return  UserDTO.getUserDtoFromUser(user);
    }

    @GetMapping("/fetchuser")
    public UserDTO getUser(Model model, @RequestParam Long id) {
        return UserDTO.getUserDtoFromUser(userService.findById(id));
    }
}
