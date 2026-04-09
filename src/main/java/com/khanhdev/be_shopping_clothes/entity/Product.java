package com.khanhdev.be_shopping_clothes.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quan hệ — không serialize để tránh circular
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private User vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @JsonIgnore
    private Brand brand;

    // Expose category & brand id/name cho FE nếu cần (thông qua transient hoặc DTO)
    // Hiện tại giữ đơn giản — FE không cần chúng trong ProductCard

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    private Integer stock;

    private BigDecimal rating;

    @Column(name = "reviews_count")
    private Integer reviewsCount;

    // Dùng @JsonProperty để tránh Jackson đổi "isNew" → "new"
    @Column(name = "is_new")
    @JsonProperty("isNew")
    private Boolean isNew;

    @Column(name = "is_active")

    private Boolean isActive;

    private String description;

    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    // Màu sắc — load cùng để tránh N+1 (dùng JOIN FETCH trong query)
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<ProductColor> colors = new ArrayList<>();
}
