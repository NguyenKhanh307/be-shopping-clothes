package com.khanhdev.be_shopping_clothes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId; //

    @Column(nullable = false, length = 150)
    private String name; //

    @Column(columnDefinition = "TEXT")
    private String description; //

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; //

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}