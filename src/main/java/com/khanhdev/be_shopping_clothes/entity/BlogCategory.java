package com.khanhdev.be_shopping_clothes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blog_categories")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BlogCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "post_count")
    private Integer postCount;
}
