package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.Product;
import com.khanhdev.be_shopping_clothes.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Product getById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));
    }

    public Page<Product> getByCategoryId(Integer categoryId, int page, int size) {
        return productRepository.findByCategoryCategoryId(categoryId, PageRequest.of(page, size));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> searchByName(String name, int page, int size) {
        return productRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, size));
    }
    public Product update(Integer id, Product details) {
        Product product = getById(id);
        product.setName(details.getName());
        product.setPrice(details.getPrice());
        product.setQuantity(details.getQuantity());
        product.setDescription(details.getDescription());
        product.setImage(details.getImage());
        product.setCategory(details.getCategory());
        return productRepository.save(product);
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
