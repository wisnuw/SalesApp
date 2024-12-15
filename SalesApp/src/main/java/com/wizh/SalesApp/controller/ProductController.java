package com.wizh.SalesApp.controller;

import com.wizh.SalesApp.dto.ProductRequestDto;
import com.wizh.SalesApp.entity.Product;
import com.wizh.SalesApp.repository.ProductRepository;
import com.wizh.SalesApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    @PostMapping
    public Product createProduct(final Authentication authentication, @RequestBody ProductRequestDto product) {
        final var user = userService.getUserByUsername(authentication.getName());

        Product newProduct = Product.builder()
                .name(product.getName())
                .price(BigDecimal.valueOf(product.getPrice()))
                .stock(product.getStock())
                .createdBy(user.getUsername())
                .updatedBy(user.getUsername())
                .build();
        return productRepository.saveAndFlush(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }
}
