package com.example.demo.data.product;

import com.example.demo.data.category.Category;
import com.example.demo.data.exceptions.BadRequestException;
import com.example.demo.data.exceptions.NotFoundException;
import com.example.demo.data.gps.GpsCoordinate;
import com.example.demo.data.history.price.PriceHistory;
import com.example.demo.data.history.price.PriceHistoryRepository;
import com.example.demo.data.util.SortingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
    }

    @Transactional
    public Product createProduct(Product product) {
        if (product.getId() != null) {
            throw new BadRequestException("Product already exist");
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long productId, Product updatedProduct) {
        Product product = getProductById(productId);

        if (updatedProduct.getName() != null && !updatedProduct.getName().isEmpty()) {
            product.setName(updatedProduct.getName());
        }
        if (updatedProduct.getDescription() != null && !updatedProduct.getDescription().isEmpty()) {
            product.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getAvailableInStock() != null) {
            product.setAvailableInStock(updatedProduct.getAvailableInStock());
        }
        if (updatedProduct.getGpsCoordinate() != null
                && updatedProduct.getGpsCoordinate().getLatitude() != null
                && updatedProduct.getGpsCoordinate().getLongitude() != null) {
            product.setGpsCoordinate(updatedProduct.getGpsCoordinate());
        }
        if (updatedProduct.getPrice() != null) {
            PriceHistory priceHistory = new PriceHistory(product, product.getPrice(), Timestamp.from(Instant.now()));
            priceHistoryRepository.save(priceHistory);

            product.setPrice(updatedProduct.getPrice());
        }

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    public Page<Product> getClosestProducts(GpsCoordinate gpsCoordinate, PageRequest pageRequest) {
        return productRepository.findClosestProducts(gpsCoordinate.getLatitude(),
                gpsCoordinate.getLongitude(),
                pageRequest);
    }

    public Page<Product> getProductsInStock(PriceRange priceRange, Category category, PageRequest pageRequest) {
        return getProductsInStock(priceRange, category, SortingOrder.ASC, pageRequest);
    }

    public Page<Product> getProductsInStock(PriceRange priceRange, SortingOrder sortingOrder, PageRequest pageRequest) {
        return getProductsInStock(priceRange, null, sortingOrder, pageRequest);
    }

    public Page<Product> getProductsInStock(Category category, SortingOrder sortingOrder, PageRequest pageRequest) {
        return getProductsInStock(new PriceRange(BigDecimal.valueOf(0), BigDecimal.valueOf(Integer.MAX_VALUE)),
                category,
                sortingOrder,
                pageRequest);
    }

    private Page<Product> getProductsInStock(PriceRange priceRange,
                                             Category category,
                                             SortingOrder sortingOrder,
                                             PageRequest pageRequest) {

        Integer unavailableInStock = 0;

        if (sortingOrder.equals(SortingOrder.ASC)) {
            return productRepository.findByAvailableInStockGreaterThanAndPriceBetweenAndCategoryInOrderByPriceAsc(
                    unavailableInStock,
                    priceRange.getMinPrice(),
                    priceRange.getMaxPrice(),
                    category,
                    pageRequest);
        } else {
            return productRepository.findByAvailableInStockGreaterThanAndPriceBetweenAndCategoryInOrderByPriceDesc(
                    unavailableInStock,
                    priceRange.getMinPrice(),
                    priceRange.getMaxPrice(),
                    category,
                    pageRequest);
        }
    }
}
