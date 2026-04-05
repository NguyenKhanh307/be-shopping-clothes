package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.Product;
import com.khanhdev.be_shopping_clothes.repository.ProductRepository;
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
}
