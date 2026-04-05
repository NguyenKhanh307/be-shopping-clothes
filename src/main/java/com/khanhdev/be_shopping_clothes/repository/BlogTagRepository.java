package com.khanhdev.be_shopping_clothes.repository;

import com.khanhdev.be_shopping_clothes.entity.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogTagRepository extends JpaRepository<BlogTag, Long> {
}
