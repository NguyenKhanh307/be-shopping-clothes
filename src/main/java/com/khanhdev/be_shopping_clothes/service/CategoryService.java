package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.Category;
import com.khanhdev.be_shopping_clothes.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    // Lấy tất cả (phân trang cho Admin)
    public Page<Category> getAllPaged(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
    }
    // Lấy tất cả (cho Menu Frontend)
    public List<Category> getAllList() {
        return categoryRepository.findAll();
    }
    // Tìm theo ID
    public Category getById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục ID: " + id));
    }
    // Tìm kiếm theo tên
    public List<Category> search(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
    // Thêm mới với Validation
    @Transactional
    public Category create(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new RuntimeException("Tên danh mục không được để trống");
        }
        return categoryRepository.save(category);
    }
    // Cập nhật
    @Transactional
    public Category update(Integer id, Category details) {
        Category category = getById(id);
        category.setName(details.getName());
        category.setDescription(details.getDescription());
        return categoryRepository.save(category);
    }
    // Xóa
    @Transactional
    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("ID " + id + " không tồn tại để xóa");
        }
        categoryRepository.deleteById(id);
    }
    // Thống kê số lượng sản phẩm
    public List<Map<String, Object>> getCategoryStats() {
        return categoryRepository.countProductsByCategory();
    }
}