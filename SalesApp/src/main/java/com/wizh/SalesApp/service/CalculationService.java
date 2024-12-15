package com.wizh.SalesApp.service;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CalculationService {

    public BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
        return num1.multiply(num2);
    }

    public BigDecimal add(BigDecimal num1, BigDecimal num2) {
        return num1.add(num2);
    }
}
