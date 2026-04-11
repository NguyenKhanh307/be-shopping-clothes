package com.khanhdev.be_shopping_clothes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Transient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@Getter @Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    private Long productId;
    private Integer quantity;
    private String color;
    private Double price;

    @Transient
    private String productName;

    @Transient
    private String imageUrl;
}