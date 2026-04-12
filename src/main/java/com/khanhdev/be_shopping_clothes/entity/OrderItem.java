package com.khanhdev.be_shopping_clothes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    private String color;

    private Integer quantity;

    @Transient
    private String productName;

    @Transient
    private String imageUrl;
}
