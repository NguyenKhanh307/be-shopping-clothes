package com.khanhdev.be_shopping_clothes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @Column(name = "created_at")
    private String createdAt;
}