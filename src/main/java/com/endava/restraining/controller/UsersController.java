package com.endava.restraining.controller;

import com.endava.restraining.entity.UserEntity;
import com.endava.restraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class UsersController {
    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/fetchallusers")
    public List<String> fetchAll(Model model, Principal principal) {
        return userService.findAllWithoutPrinciple(principal);
    }

    @GetMapping("/getprincipal")
    public String getPrincipal(Model model, Principal principal) {
        return principal.getName();
    }
}
