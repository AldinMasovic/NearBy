package com.example.demo.domain.product;

import com.example.demo.domain.category.CategoryDto;
import com.example.demo.domain.product.gps.GpsCoordinateDto;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductDto {
    Long id;
    String name;
    String description;
    CategoryDto category;
    BigDecimal price;
    GpsCoordinateDto gpsCoordinate;
    String imageUrl;
    Integer availableInStock;

}
