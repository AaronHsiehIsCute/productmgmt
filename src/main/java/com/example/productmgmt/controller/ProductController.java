package com.example.productmgmt.controller;

import com.example.productmgmt.dto.ProductRequest;
import com.example.productmgmt.entity.Product;
import com.example.productmgmt.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "商品管理 API")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "新增商品", description = "需為商戶或公司人員")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('STAFF', 'MERCHANT')") //可以移除, SecurityConfig已經有寫，這裡是為了將來細粒度權限控制方便使用
    public void createProduct(@RequestBody ProductRequest request) {
        productService.createProduct(request);
    }

    @Operation(summary = "查詢所有商品")
    @PreAuthorize("hasAnyAuthority('MERCHANT', 'STAFF')")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "依條件查詢商品")
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MERCHANT', 'STAFF')")
    public List<Product> searchProducts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sku,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int offset = (page - 1) * size;
        if (offset < 0) offset = 0;
        return productService.searchProducts(id, name, sku, offset, size);
    }

    @Operation(summary = "查詢用戶所有產品")
    @GetMapping("/users/{userId}/products")
    @PreAuthorize("hasAnyAuthority('STAFF')")
    public List<Product> getAllProductByUser(@PathVariable("userId") Long userId) {
        return productService.getAllProductByUser(userId);
    }
}
