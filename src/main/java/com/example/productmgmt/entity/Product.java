package com.example.productmgmt.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Long id;
    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long createdBy;
    private LocalDateTime createdAt;
}
