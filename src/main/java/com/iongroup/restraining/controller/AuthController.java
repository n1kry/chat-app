package com.iongroup.restraining.controller;

import com.iongroup.restraining.entity.UserEntity;
import com.iongroup.restraining.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/registration")
    public String registerForm(Model model, UserEntity userEntity) {
        model.addAttribute("user", userEntity);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerSubmit(@Valid UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        userService.addUser(userEntity);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("chat/logout")
    public String logout() {
//        simpMessagingTemplate.convertAndSend("/topic/getprinc","Hello");
        return "redirect:/logout";
    }
}
