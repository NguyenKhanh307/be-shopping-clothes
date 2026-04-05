package com.khanhdev.be_shopping_clothes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blogs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_category_id")
    @JsonIgnore
    private BlogCategory blogCategory;

    // author_id không serialize — chỉ expose authorName
    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "image_url")
    private String imageUrl;

    private String excerpt;

    @Column(name = "comments_count")
    private Integer commentsCount;

    @Column(name = "is_popular")
    private Boolean isPopular;

    @Column(name = "is_published")
    @JsonIgnore
    private Boolean isPublished;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createdAt;
}
