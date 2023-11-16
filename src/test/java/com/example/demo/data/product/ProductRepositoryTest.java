package com.example.demo.data.product;

import com.example.demo.data.category.Category;
import com.example.demo.data.category.CategoryRepository;
import com.example.demo.data.gps.GpsCoordinate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldSaveProductWhenAllDataIsValid() {
        Category category1 = new Category("category1");
        categoryRepository.save(category1);

        // given
        Product product = buildProduct(BigDecimal.valueOf(123), new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)), 3, category1);
        Product savedProduct = underTest.save(product);
        // when
        Optional<Product> productInDb = underTest.findById(savedProduct.getId());
        // then
        assertThat(productInDb.isPresent()).isTrue();
    }

    @Test
    public void itShouldReturnProductsThatAreAvailableInStockGreaterThanZero() {
        Category category = new Category("category1");
        categoryRepository.save(category);

        // given
        Product product = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                3,
                category);
        underTest.save(product);
        Product product2 = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                0,
                category);
        underTest.save(product2);
        Product product3 = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                3,
                category);
        underTest.save(product3);


        //when
        Page<Product> products = underTest.findByAvailableInStockGreaterThanAndPriceBetweenAndCategoryInOrderByPriceAsc(0,
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000),
                category,
                PageRequest.of(0, 10));

        assertThat(products.getContent().size()).isEqualTo(2);
    }

    @Test
    void itShouldReturnProductsThatAreAvailableInStockGreaterThanZeroWithoutUsingFiltersOnPriceAndCategory() {
        Category category = new Category("category1");
        categoryRepository.save(category);

        // given
        Product product = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                3,
                category);
        underTest.save(product);
        Product product2 = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                2,
                category);
        underTest.save(product2);
        Product product3 = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                3,
                category);
        underTest.save(product3);


        //when
        Page<Product> products = underTest.findByAvailableInStockGreaterThanAndPriceBetweenAndCategoryInOrderByPriceDesc(0,
                null,
                null,
                null,
                PageRequest.of(0, 10));

        assertThat(products.getContent().size()).isEqualTo(3);
    }

    @Test
    void itShouldReturnEmptyListWhenMinPriceIsGreaterThanAllProductsPrice() {
        Category category = new Category("category1");
        categoryRepository.save(category);

        // given
        Product product = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                3,
                category);
        underTest.save(product);
        Product product2 = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                2,
                category);
        underTest.save(product2);
        Product product3 = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                3,
                category);
        underTest.save(product3);


        //when
        Page<Product> products = underTest.findByAvailableInStockGreaterThanAndPriceBetweenAndCategoryInOrderByPriceDesc(0,
                BigDecimal.valueOf(150),
                null,
                null,
                PageRequest.of(0, 10));

        assertThat(products.getContent().size()).isEqualTo(0);
    }

    @Test
    void itShouldReturnClosestProductWhenGpsCoordinateAreClose() {
        Category category = new Category("category1");
        categoryRepository.save(category);

        // given
        Product product = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                3,
                category);
        underTest.save(product);
        Product product2 = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(123.332123), BigDecimal.valueOf(123.123123)),
                2,
                category);
        underTest.save(product2);
        Product product3 = buildProduct(BigDecimal.valueOf(123),
                new GpsCoordinate(BigDecimal.valueOf(987.654321), BigDecimal.valueOf(987.654321)),
                3,
                category);
        underTest.save(product3);


        //when
        Page<Product> products = underTest.findClosestProducts(BigDecimal.valueOf(987.654322),
                BigDecimal.valueOf(987.654322),
                PageRequest.of(0, 10));

        assertThat(products.getContent().size()).isEqualTo(3);
        assertThat(products.getContent().get(0).getId()).isEqualTo(product3.getId());
    }


    private static Product buildProduct(BigDecimal price, GpsCoordinate gpsCoordinate, int availableInStock, Category category) {
        Product product = new Product();
        product.setName("name");
        product.setDescription("description");
        product.setPrice(price);
        product.setGpsCoordinate(gpsCoordinate);
        product.setAvailableInStock(availableInStock);
        product.setCategory(category);
        product.setImageUrl("");
        return product;
    }
}
