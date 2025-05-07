package com.example.productmgmt.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
