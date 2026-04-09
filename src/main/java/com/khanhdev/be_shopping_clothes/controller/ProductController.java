package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.entity.Product;
import com.khanhdev.be_shopping_clothes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    /** GET /products/flash-sale */
    @GetMapping("/flash-sale")
    public ResponseEntity<List<Product>> getFlashSaleProducts() {
        return ResponseEntity.ok(productService.getFlashSaleProducts());
    }

    /** GET /products/trending?category=western */
    @GetMapping("/trending")
    public ResponseEntity<List<Product>> getTrendingProducts(
            @RequestParam(defaultValue = "western") String category) {
        return ResponseEntity.ok(productService.getTrendingProducts(category));
    }

    /** GET /products/best-selling */
    @GetMapping("/best-selling")
    public ResponseEntity<List<Product>> getBestSellingProducts() {
        return ResponseEntity.ok(productService.getBestSellingProducts());
    }

    /** GET /products/favourite */
    @GetMapping("/favourite")
    public ResponseEntity<List<Product>> getFavouriteProducts() {
        return ResponseEntity.ok(productService.getFavouriteProducts());
    }

    /** GET /products/new-arrivals */
    @GetMapping("/new-arrivals")
    public ResponseEntity<List<Product>> getNewArrivalProducts() {
        return ResponseEntity.ok(productService.getNewArrivalProducts());
    }

    /** GET /products/special */
    @GetMapping("/special")
    public ResponseEntity<List<Product>> getSpecialProducts() {
        return ResponseEntity.ok(productService.getSpecialProducts());
    }

    /** GET /products/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /products/search?keyword=áo */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchByName(keyword));
    }

    /** POST /products */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.status(201).body(created);
    }
    /** DELETE /products/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
