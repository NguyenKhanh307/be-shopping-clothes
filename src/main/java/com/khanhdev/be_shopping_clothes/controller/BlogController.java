package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.entity.Blog;
import com.khanhdev.be_shopping_clothes.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BlogController {

    private final BlogService blogService;

    /** GET /blogs  (hỗ trợ ?limit= không bắt buộc — FE truyền nhưng không xử lý phân trang ở đây) */
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs(
            @RequestParam(required = false) Integer limit) {
        List<Blog> blogs = blogService.getAllBlogs();
        if (limit != null && limit > 0 && limit < blogs.size()) {
            blogs = blogs.subList(0, limit);
        }
        return ResponseEntity.ok(blogs);
    }

    /** GET /blogs/popular */
    @GetMapping("/popular")
    public ResponseEntity<List<Blog>> getPopularBlogs() {
        return ResponseEntity.ok(blogService.getPopularBlogs());
    }
}
