package com.example.demo.domain.product;

import com.example.demo.data.gps.GpsCoordinate;
import com.example.demo.data.product.PriceRange;
import com.example.demo.data.product.Product;
import com.example.demo.data.product.ProductService;
import com.example.demo.data.util.SortingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/sale")
    public Page<ProductDto> getProductsInStock(@RequestParam(required = false, defaultValue = "0") BigDecimal minPrice,
                                               @RequestParam(required = false, defaultValue = Long.MAX_VALUE + "") BigDecimal maxPrice,
                                               @RequestParam(required = false, defaultValue = "ASC") SortingOrder sortingOrder,
                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                               @RequestParam(required = false, defaultValue = "20") Integer size) {
        Page<Product> productsInStock = productService.getProductsInStock(new PriceRange(minPrice, maxPrice),
                                                                            sortingOrder,
                                                                            PageRequest.of(page, size));
        return productsInStock.map(ProductMapper::mapToProductDto);
    }

    @GetMapping("/closest")
    public Page<ProductDto> getClosestProducts(@RequestParam BigDecimal latitude,
                                               @RequestParam BigDecimal longitude,
                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                               @RequestParam(required = false, defaultValue = "20") Integer size) {
        Page<Product> closestProducts = productService.getClosestProducts(new GpsCoordinate(latitude, longitude), PageRequest.of(page, size));
        return closestProducts.map(ProductMapper::mapToProductDto);
    }

    @GetMapping("/details/{id}")
    public ProductDetailDto getProductDetail(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ProductMapper.mapToProductDetailDto(product);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto dto) {
        Product product = productService.createProduct(ProductMapper.mapToProduct(dto));
        return ProductMapper.mapToProductDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id,
                                    @RequestBody ProductDto dto) {
        Product product = productService.updateProduct(id, ProductMapper.mapToProduct(dto));
        return ProductMapper.mapToProductDto(product);
    }
}
