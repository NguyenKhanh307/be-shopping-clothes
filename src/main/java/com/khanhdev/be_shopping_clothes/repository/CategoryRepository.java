package com.khanhdev.be_shopping_clothes.repository;

import com.khanhdev.be_shopping_clothes.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Tìm kiếm theo tên (không phân biệt hoa thường)
    List<Category> findByNameContainingIgnoreCase(String name);

    // Truy vấn custom: Đếm số sản phẩm trong mỗi danh mục
    @Query(value = "SELECT c.category_id as id, c.name as name, COUNT(p.product_id) as count " +
            "FROM categories c LEFT JOIN products p ON c.category_id = p.category_id " +
            "GROUP BY c.category_id", nativeQuery = true)
    List<Map<String, Object>> countProductsByCategory();
}