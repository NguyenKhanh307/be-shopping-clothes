package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.entity.BlogTag;
import com.khanhdev.be_shopping_clothes.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog-tags")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BlogTagController {

    private final BlogService blogService;

    /** GET /blog-tags */
    @GetMapping
    public ResponseEntity<List<BlogTag>> getAllBlogTags() {
        return ResponseEntity.ok(blogService.getAllBlogTags());
    }
}
