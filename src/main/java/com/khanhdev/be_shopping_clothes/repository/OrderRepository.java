package com.khanhdev.be_shopping_clothes.repository;

import com.khanhdev.be_shopping_clothes.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
