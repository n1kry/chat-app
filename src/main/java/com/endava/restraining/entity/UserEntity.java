package com.endava.restraining.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Column(nullable = false, unique = true)
    private String username;

    @Length(min = 6, message = "Password length must be more than 5 characters")
    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;
}
