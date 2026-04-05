package com.khanhdev.be_shopping_clothes.repository;

import com.khanhdev.be_shopping_clothes.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsActiveTrueOrderBySortOrderAsc();
}
