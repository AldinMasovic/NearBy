package com.example.demo.domain.product.gps;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class GpsCoordinateDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
