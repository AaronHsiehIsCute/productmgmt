package com.example.productmgmt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan("com.example.productmgmt.mapper")
public class ProductmgmtApplication {
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
		SpringApplication.run(ProductmgmtApplication.class, args);
	}
}
