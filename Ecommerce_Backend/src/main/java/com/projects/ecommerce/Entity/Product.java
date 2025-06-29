package com.projects.ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

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
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private double productPrice;
    private String productSize;
}
