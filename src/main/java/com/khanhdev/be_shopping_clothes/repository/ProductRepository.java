package com.khanhdev.be_shopping_clothes.repository;

import com.khanhdev.be_shopping_clothes.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Flash Sale: sản phẩm đang có flash_sale hoạt động tại thời điểm hiện tại
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.colors
        JOIN FlashSale fs ON fs.product = p
        WHERE fs.isActive = true
          AND :now BETWEEN fs.startAt AND fs.endAt
          AND p.isActive = true
    """)
    List<Product> findFlashSaleProducts(@Param("now") LocalDateTime now);

    // Trending: lọc theo slug của category, sắp xếp theo reviewsCount DESC
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.colors
        WHERE p.category.slug = :slug
          AND p.isActive = true
        ORDER BY p.reviewsCount DESC
    """)
    List<Product> findTrendingByCategory(@Param("slug") String slug);

    // Best Selling: reviewsCount DESC (top 4 để dùng layout 3+1)
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.colors
        WHERE p.isActive = true
        ORDER BY p.reviewsCount DESC
    """)
    List<Product> findBestSelling();

    // Favourite: rating DESC
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.colors
        WHERE p.isActive = true
        ORDER BY p.rating DESC
    """)
    List<Product> findFavourite();

    // New Arrivals: is_new = true, createdAt DESC
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.colors
        WHERE p.isNew = true
          AND p.isActive = true
        ORDER BY p.createdAt DESC
    """)
    List<Product> findNewArrivals();

    // Special: có discount_percent (đang giảm giá)
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.colors
        WHERE p.discountPercent IS NOT NULL
          AND p.isActive = true
        ORDER BY p.discountPercent DESC
    """)
    List<Product> findSpecial();

    // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường, tìm kiếm gần đúng)
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.colors
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
          AND p.isActive = true
        ORDER BY p.name ASC
    """)
    List<Product> searchByName(@Param("keyword") String keyword);
}
