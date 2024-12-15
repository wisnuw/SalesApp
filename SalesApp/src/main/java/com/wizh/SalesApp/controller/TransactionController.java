package com.wizh.SalesApp.controller;

import com.wizh.SalesApp.dto.CheckoutItemDto;
import com.wizh.SalesApp.dto.CheckoutRequestDto;
import com.wizh.SalesApp.dto.CheckoutResponseDto;
import com.wizh.SalesApp.entity.Product;
import com.wizh.SalesApp.entity.Transaction;
import com.wizh.SalesApp.entity.TransactionDetail;
import com.wizh.SalesApp.repository.ProductRepository;
import com.wizh.SalesApp.repository.TransactionDetailRepository;
import com.wizh.SalesApp.repository.TransactionRepository;
import com.wizh.SalesApp.service.TransactionService;
import com.wizh.SalesApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDto> checkout(final Authentication authentication, @RequestBody CheckoutRequestDto checkoutRequestDto) {
        final var user = userService.getUserByUsername(authentication.getName());

        CheckoutResponseDto response = transactionService.checkout(user, checkoutRequestDto);
        return new ResponseEntity<CheckoutResponseDto>(response, HttpStatus.OK);
    }

    @GetMapping
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }
}
