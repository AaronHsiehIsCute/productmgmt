package com.example.productmgmt.service;

import com.example.productmgmt.dto.ProductRequest;
import com.example.productmgmt.entity.Product;
import com.example.productmgmt.entity.User;
import com.example.productmgmt.mapper.ProductMapper;
import com.example.productmgmt.exception.ProductNotFoundException;
import com.example.productmgmt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    public void createProduct(ProductRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = currentUser.getUsername();

        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("找不到使用者：" + username);
        }

        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .createdBy(user.getId())
                .createdAt(LocalDateTime.now())
                .build();

        productMapper.insertProduct(product);
    }

    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }

    public List<Product> searchProducts(Long id, String name, String sku, int offset, int limit) {
        List<Product> result = productMapper.searchProducts(id, name, sku, offset, limit);
        if (result == null || result.isEmpty()) {
            throw new ProductNotFoundException("查無符合條件的產品");
        }
        return result;
    }


    public List<Product> getAllProductByUser(Long userId){
        List<Product> result = productMapper.getAllProductByUser(userId);
        if (result == null || result.isEmpty()) {
            throw new ProductNotFoundException("查無符合條件的產品");
        }
        return result;
    }

}
