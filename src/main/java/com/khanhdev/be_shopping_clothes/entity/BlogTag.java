package com.khanhdev.be_shopping_clothes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_tags")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BlogTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;
}
