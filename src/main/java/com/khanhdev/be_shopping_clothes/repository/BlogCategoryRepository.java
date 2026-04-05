package com.khanhdev.be_shopping_clothes.repository;

import com.khanhdev.be_shopping_clothes.entity.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
}
