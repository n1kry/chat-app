package com.iongroup.restraining.controller;

import com.iongroup.restraining.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/fetchallusers")
    public List<String> fetchAll(Model model, Principal principal) {
        return userService.findAllWithoutPrinciple(principal);
    }

    @GetMapping("/getprincipal")
    public String getPrincipal(Model model, Principal principal) {
        return principal.getName();
    }
}
