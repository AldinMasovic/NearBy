package com.example.demo.domain.product;

import com.example.demo.data.product.Product;
import com.example.demo.domain.category.CategoryMapper;
import com.example.demo.domain.product.gps.GpsCoordinateMapper;
import com.example.demo.domain.product.view.ImageMapper;

import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {
    }

    public static Product mapToProduct(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setGpsCoordinate(GpsCoordinateMapper.mapToGpsCoordinate(dto.getGpsCoordinate()));
        product.setAvailableInStock(dto.getAvailableInStock());
        product.setCategory(CategoryMapper.mapToCategory(dto.getCategory()));
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    public static ProductDto mapToProductDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDto(product.getId(),
                product.getName(),
                product.getDescription(),
                CategoryMapper.mapToDto(product.getCategory()),
                product.getPrice(),
                GpsCoordinateMapper.mapToDto(product.getGpsCoordinate()),
                product.getImageUrl(),
                product.getAvailableInStock());
    }

    public static ProductDetailDto mapToProductDetailDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDetailDto(product.getId(),
                product.getName(),
                product.getDescription(),
                CategoryMapper.mapToDto(product.getCategory()),
                product.getPrice(),
                GpsCoordinateMapper.mapToDto(product.getGpsCoordinate()),
                product.getViews().stream().map(ImageMapper::mapToDto).collect(Collectors.toList()),
                product.getImageUrl(),
                product.getAvailableInStock());
    }
}
