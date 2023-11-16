package com.example.demo.data.product;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PriceRange {
    BigDecimal minPrice;
    BigDecimal maxPrice;
}
