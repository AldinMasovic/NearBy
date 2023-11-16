package com.example.demo.data.product;

import com.example.demo.data.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {


    @Query("""
            select p from Product p
            where p.availableInStock > :availableInStock
            and p.price between :minPrice
            and :maxPrice
            and p.category in :categories
            order by p.price ASC""")
    Page<Product> findByAvailableInStockGreaterThanAndPriceBetweenAndCategoriesInOrderByPriceAsc(
            @Param("availableInStock") Integer availableInStock,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("categories") Set<Category> categories,
            Pageable pageable
    );

    @Query("""
            select p from Product p
            where p.availableInStock > :availableInStock
            and p.price between :minPrice
            and :maxPrice
            and p.category in :categories
            order by p.price DESC""")
    Page<Product> findByAvailableInStockGreaterThanAndPriceBetweenAndCategoryInOrderByPriceDesc(
            @Param("availableInStock") Integer availableInStock,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("categories") Set<Category> categories,
            Pageable pageable
    );

    @Query("""
            select p from Product p
            where p.availableInStock > :availableInStock
            and p.price between :minPrice
            and :maxPrice
            order by p.price DESC""")
    Page<Product> findByAvailableInStockGreaterThanAndPriceBetweenOrderByPriceDesc(
            @Param("availableInStock") Integer availableInStock,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );

    @Query("""
        SELECT p FROM Product p
        WHERE p.availableInStock > 0
        ORDER BY acos(sin(:latitude) * sin(p.gpsCoordinate.latitude) +
        cos(:latitude) * cos(p.gpsCoordinate.latitude) * cos(:longitude - p.gpsCoordinate.longitude)) ASC""")
    Page<Product> findClosestProducts(
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            Pageable pageable
    );


    //TODO swagger
    //TODO run the project instruction in help.md
    //TODO implement tests
    //TODO insert data by flyway
    //TODO validation on DTO

}
