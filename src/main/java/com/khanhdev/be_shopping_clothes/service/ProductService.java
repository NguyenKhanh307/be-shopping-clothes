package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.Category;
import com.khanhdev.be_shopping_clothes.entity.Product;
import com.khanhdev.be_shopping_clothes.entity.User;
import com.khanhdev.be_shopping_clothes.repository.CategoryRepository;
import com.khanhdev.be_shopping_clothes.repository.ProductRepository;
import com.khanhdev.be_shopping_clothes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // Map tab key của FE → category slug trong DB
    private static final Map<String, String> TAB_TO_SLUG = Map.of(
        "western", "thoi-trang-nam",
        "tops",    "thoi-trang-nu",
        "bags",    "lam-dep-suc-khoe",
        "shoes",   "thoi-trang-nam"
    );

    public List<Product> getFlashSaleProducts() {
        return productRepository.findFlashSaleProducts(LocalDateTime.now());
    }

    public List<Product> getTrendingProducts(String category) {
        String slug = TAB_TO_SLUG.getOrDefault(category, "thoi-trang-nam");
        return productRepository.findTrendingByCategory(slug);
    }

    public List<Product> getBestSellingProducts() {
        return productRepository.findBestSelling();
    }

    public List<Product> getFavouriteProducts() {
        return productRepository.findFavourite();
    }

    public List<Product> getNewArrivalProducts() {
        return productRepository.findNewArrivals();
    }

    public List<Product> getSpecialProducts() {
        return productRepository.findSpecial();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // ── CRUD ────────────────────────────────────────────────────────────────
    @Transactional
    public Product createProduct(Product product) {
        // 1. Xử lý Category (Bắt buộc theo schema)
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new RuntimeException("Lỗi: category_id không được để trống!");
        }
        // Tìm category trong DB để đảm bảo tồn tại (tránh lỗi FK_product_category)
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy danh mục!"));
        product.setCategory(category);
        if (product.getVendor() == null) {
            User defaultVendor = userRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy nhà bán hàng mặc định!"));
            product.setVendor(defaultVendor);
        }
        if (product.getSalePrice() == null) {
            product.setSalePrice(java.math.BigDecimal.ZERO);
        }
        if (product.getIsActive() == null) {
            product.setIsActive(true);
        }
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    /** Cập nhật sản phẩm theo ID */
    @Transactional
    public Optional<Product> updateProduct(Long id, Product updated) {
        return productRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setSlug(updated.getSlug());
            existing.setImageUrl(updated.getImageUrl());
            existing.setSalePrice(updated.getSalePrice());
            existing.setOriginalPrice(updated.getOriginalPrice());
            existing.setDiscountPercent(updated.getDiscountPercent());
            existing.setStock(updated.getStock());
            existing.setRating(updated.getRating());
            existing.setReviewsCount(updated.getReviewsCount());
            existing.setIsNew(updated.getIsNew());
            existing.setIsActive(updated.getIsActive());
            existing.setDescription(updated.getDescription());
            existing.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(existing);
        });
    }

    /** Xóa sản phẩm theo ID */
    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    /** Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường) */
    public List<Product> searchByName(String keyword) {
        return productRepository.searchByName(keyword);
    }
}
