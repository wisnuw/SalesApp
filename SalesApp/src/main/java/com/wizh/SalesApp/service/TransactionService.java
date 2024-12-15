package com.wizh.SalesApp.service;

import com.wizh.SalesApp.dto.CheckoutItemDto;
import com.wizh.SalesApp.dto.CheckoutRequestDto;
import com.wizh.SalesApp.dto.CheckoutResponseDto;
import com.wizh.SalesApp.entity.Product;
import com.wizh.SalesApp.entity.Transaction;
import com.wizh.SalesApp.entity.TransactionDetail;
import com.wizh.SalesApp.entity.User;
import com.wizh.SalesApp.repository.ProductRepository;
import com.wizh.SalesApp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CalculationService calculationService;

    public CheckoutResponseDto checkout(User user, CheckoutRequestDto checkoutRequestDto) {
        List<Product> updatedProducts = new ArrayList<>();
        List<TransactionDetail> txnDetailList = new ArrayList<>();
        BigDecimal grandTotalPrice = new BigDecimal("0");

        for(CheckoutItemDto item: checkoutRequestDto.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                int remainStock = product.getStock() - item.getAmount();
                if(remainStock >=0) {
                    BigDecimal totalPrice = BigDecimal.valueOf((long) product.getPrice().intValue() * item.getAmount());
                    grandTotalPrice = calculationService.add(grandTotalPrice, totalPrice);

                    txnDetailList.add(TransactionDetail.builder()
                            .amount(item.getAmount())
                            .productId(product.getId())
                            .totalPrice(totalPrice)
                            .createdBy(user.getUsername())
                            .updatedBy(user.getUsername())
                            .build());

                    product.setStock(remainStock);
                    product.setUpdatedBy(user.getUsername());
                    updatedProducts.add(product);
                }
                else {
                    return CheckoutResponseDto.builder()
                            .status("CHECKOUT FAIL")
                            .message("Insufficient product stock")
                            .build();
                }
            } else {
                return CheckoutResponseDto.builder()
                        .status("CHECKOUT FAIL")
                        .message("Some products not found")
                        .build();
            }
        }

        transactionRepository.save(Transaction.builder()
                .grandTotalPrice(grandTotalPrice)
                .userId(user.getId())
                .createdBy(user.getUsername())
                .updatedBy(user.getUsername())
                .items(txnDetailList)
                .build());

        productRepository.saveAllAndFlush(updatedProducts);

        return CheckoutResponseDto.builder()
                .status("SUCCESS")
                .message("Thank you!")
                .build();
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
