package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.entity.Brand;
import com.khanhdev.be_shopping_clothes.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BrandController {

    private final BrandService brandService;

    /** GET /brands */
    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }
}
