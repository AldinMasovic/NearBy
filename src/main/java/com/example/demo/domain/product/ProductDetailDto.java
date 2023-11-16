package com.example.demo.domain.product;

import com.example.demo.domain.category.CategoryDto;
import com.example.demo.domain.product.gps.GpsCoordinateDto;
import com.example.demo.domain.product.view.ImageDto;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class ProductDetailDto {
    Long id;
    String name;
    String description;
    CategoryDto category;
    BigDecimal price;
    GpsCoordinateDto gpsCoordinate;
    List<ImageDto> views;
    String imageUrl;
    Integer availableInStock;
}
