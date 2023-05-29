package com.endava.restraining.service;

import com.endava.restraining.dao.UserDAO;
import com.endava.restraining.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDAO userDao;

    private final BCryptPasswordEncoder passwordEncoder;

    public void addUser(final UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public List<String> findAllWithoutPrinciple(Principal principal) {
        return userDao.findAllByUsernameNot(principal.getName()).stream().map(UserEntity::getUsername).collect(Collectors.toList());
    }

    public UserEntity findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public boolean ifExist(String username) {
        return userDao.existsByUsername(username);
    }
}
