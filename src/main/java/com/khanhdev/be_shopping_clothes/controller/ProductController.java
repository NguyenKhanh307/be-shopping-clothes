package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.entity.Category;
import com.khanhdev.be_shopping_clothes.entity.Product;
import com.khanhdev.be_shopping_clothes.service.FileStorageService;
import com.khanhdev.be_shopping_clothes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;
    private final FileStorageService fileStorageService;

    // 1. Lấy danh sách sản phẩm phân trang
    @GetMapping
    public ResponseEntity<Page<Product>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAll(page, size));
    }

    // 2. Xem chi tiết sản phẩm
    @GetMapping("/{id}")
    public ResponseEntity<Product> detail(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    // 3. Tìm kiếm sản phẩm theo tên
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> search(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        return ResponseEntity.ok(productService.searchByName(name, page, size));
    }

    // 4. Thêm mới sản phẩm + Upload ảnh vào thư mục FE
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Product> add(
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("description") String description,
            @RequestParam("category_id") Integer categoryId,
            @RequestParam("image") MultipartFile file) {

        // Lưu file vật lý vào thư mục fe/assets/images/ thông qua Service
        String fileName = fileStorageService.storeFile(file);

        // Tạo đối tượng Product
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setImage(fileName); // Lưu tên file vào DB

        // Gán Category
        Category cat = new Category();
        cat.setCategoryId(categoryId);
        product.setCategory(cat);

        return ResponseEntity.ok(productService.create(product));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Product> edit(
            @PathVariable Integer id,
            @RequestParam("name") String name,
            @RequestParam("price") java.math.BigDecimal price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("description") String description,
            @RequestParam("category_id") Integer categoryId,
            @RequestParam(value = "image", required = false) MultipartFile file) {
        // 1. Tìm sản phẩm hiện tại trong Database
        Product existingProduct = productService.getById(id);
        // 2. Mặc định lấy lại tên ảnh cũ
        String fileName = existingProduct.getImage();
        // 3. Nếu người dùng có chọn file mới (file không null)
        if (file != null && !file.isEmpty()) {
            fileName = fileStorageService.storeFile(file);
        }
        existingProduct.setName(name);
        existingProduct.setPrice(price);
        existingProduct.setQuantity(quantity);
        existingProduct.setDescription(description);
        existingProduct.setImage(fileName); // Cập nhật tên file (mới hoặc cũ)
        // 5. Gán lại Category
        com.khanhdev.be_shopping_clothes.entity.Category cat = new com.khanhdev.be_shopping_clothes.entity.Category();
        cat.setCategoryId(categoryId);
        existingProduct.setCategory(cat);
        // 6. Lưu lại vào DB
        return ResponseEntity.ok(productService.create(existingProduct));
    }

    // 6. Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}