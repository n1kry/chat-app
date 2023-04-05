package com.endava.restraining.service;

import com.endava.restraining.dao.UserDAO;
import com.endava.restraining.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private final UserDAO userDao;

    private BCryptPasswordEncoder passwordEncoder;

    UserService(UserDAO userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    public void addUser(final UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public UserEntity findByUsername(String username) {
        return userDao.findByUsername(username);
    }

//    @Lazy
//    @Autowired
//    public void setPasswordEncoder(final BCryptPasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public void registerUser(UserDTO user) {
//        final UserEntity userEntity = new UserEntity();
//        userEntity.setUsername(validateUsername(user.getUsername()));
//        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
//        userDao.save(userEntity);
//    }
//
//    private String validateUsername(final String username) {
//        if (userDao.existsByUsername(username)) {
//            throw new RuntimeException("User already registered!");
//        }
//        return username;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
//        UserEntity userEntity = userDao.findByUsername(username);
//        return new User(
//                userEntity.getUsername(),
//                userEntity.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//        );
//    }
}
