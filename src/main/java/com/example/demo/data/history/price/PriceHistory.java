package com.example.demo.data.history.price;

import com.example.demo.data.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private BigDecimal price;
    private Timestamp createdAt;

    protected PriceHistory() {
    }

    public PriceHistory(Product product, BigDecimal price, Timestamp createdAt) {
        this.product = product;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
