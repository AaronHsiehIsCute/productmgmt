package com.example.productmgmt.controller;

import com.example.productmgmt.dto.ProductRequest;
import com.example.productmgmt.entity.Product;
import com.example.productmgmt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('STAFF', 'MERCHANT')")
    public void createProduct(@RequestBody ProductRequest request) {
        productService.createProduct(request);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT', 'STAFF')")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

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


}
