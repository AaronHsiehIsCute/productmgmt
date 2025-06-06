package com.example.productmgmt.mapper;

import com.example.productmgmt.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper {

    @Insert("""
        INSERT INTO products (name, sku, description, price, stock, created_by)
        VALUES (#{name}, #{sku}, #{description}, #{price}, #{stock}, #{createdBy})
    """)
    void insertProduct(Product product);

    @Select("SELECT * FROM products ORDER BY created_at DESC")
    List<Product> findAll();

    List<Product> searchProducts(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("sku") String sku,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    @Select("SELECT * FROM products WHERE created_by = #{userId}")
    List<Product> getAllProductByUser(@Param("userId") Long userId);
}
