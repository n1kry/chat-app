package com.endava.restraining.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Entity
@Setter
@Getter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
//    @Length(min = 5, message = "Nickname should have more than 5 characters")
    @Column(nullable = false, unique = true)
    private String username;

//    @Length(min = 6, message = "Password length must be more than 5 characters")
    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
//    @Email(message = "Please enter a correct email")
    private String email;
}
