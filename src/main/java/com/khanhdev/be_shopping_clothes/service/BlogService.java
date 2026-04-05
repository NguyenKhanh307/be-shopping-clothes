package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.Blog;
import com.khanhdev.be_shopping_clothes.entity.BlogCategory;
import com.khanhdev.be_shopping_clothes.entity.BlogTag;
import com.khanhdev.be_shopping_clothes.repository.BlogCategoryRepository;
import com.khanhdev.be_shopping_clothes.repository.BlogRepository;
import com.khanhdev.be_shopping_clothes.repository.BlogTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogService {

    private final BlogRepository      blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final BlogTagRepository   blogTagRepository;

    // Tất cả bài viết đã đăng (mới nhất lên đầu)
    public List<Blog> getAllBlogs() {
        return blogRepository.findAllByIsPublishedTrueOrderByPublishedAtDesc();
    }

    // Bài viết phổ biến (sidebar)
    public List<Blog> getPopularBlogs() {
        return blogRepository.findAllByIsPublishedTrueAndIsPopularTrueOrderByPublishedAtDesc();
    }

    // Tất cả danh mục blog
    public List<BlogCategory> getAllBlogCategories() {
        return blogCategoryRepository.findAll();
    }

    // Tất cả tags
    public List<BlogTag> getAllBlogTags() {
        return blogTagRepository.findAll();
    }
}
