package com.projects.ecommerce.Entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productImageUrl;
    @Column(nullable = false)
    private String productDescription;
    @Column(nullable = false)
    private String productCategory;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private double productPrice;
    private String productSize;
}
