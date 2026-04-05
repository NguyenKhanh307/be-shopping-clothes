package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.entity.BlogCategory;
import com.khanhdev.be_shopping_clothes.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog-categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BlogCategoryController {

    private final BlogService blogService;

    /** GET /blog-categories */
    @GetMapping
    public ResponseEntity<List<BlogCategory>> getAllBlogCategories() {
        return ResponseEntity.ok(blogService.getAllBlogCategories());
    }
}
