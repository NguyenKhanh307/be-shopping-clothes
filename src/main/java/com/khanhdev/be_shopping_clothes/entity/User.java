package com.khanhdev.be_shopping_clothes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String address;

    @Column(name = "is_active")
    private Boolean isActive;

    public enum Role { admin, vendor, customer }
}
