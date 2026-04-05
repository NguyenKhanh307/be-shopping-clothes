package com.khanhdev.be_shopping_clothes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_colors")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(name = "hex_code", nullable = false)
    private String hexCode;

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "sort_order")
    private Integer sortOrder;
}
