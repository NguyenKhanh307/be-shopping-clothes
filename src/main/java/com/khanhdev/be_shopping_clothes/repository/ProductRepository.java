package com.khanhdev.be_shopping_clothes.repository;

import com.khanhdev.be_shopping_clothes.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Lọc sản phẩm theo ID danh mục
    Page<Product> findByCategoryCategoryId(Integer categoryId, Pageable pageable);
    // Tìm kiếm theo tên sản phẩm
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}