package com.khanhdev.be_shopping_clothes.repository;

import com.khanhdev.be_shopping_clothes.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // Tất cả bài đã đăng, mới nhất lên đầu
    List<Blog> findAllByIsPublishedTrueOrderByPublishedAtDesc();

    // Bài viết phổ biến (sidebar)
    List<Blog> findAllByIsPublishedTrueAndIsPopularTrueOrderByPublishedAtDesc();
}
