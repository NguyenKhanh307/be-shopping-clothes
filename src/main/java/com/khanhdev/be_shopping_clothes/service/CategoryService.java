package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.Category;
import com.khanhdev.be_shopping_clothes.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAllByIsActiveTrueOrderBySortOrderAsc();
    }
}
