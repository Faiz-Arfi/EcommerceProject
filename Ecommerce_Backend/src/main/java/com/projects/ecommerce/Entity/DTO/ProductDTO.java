package com.projects.ecommerce.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String productId;

    private String productName;

    private String productImageUrl;

    private String productDescription;

    private String categoryId;

    private String brand;

    private double productPrice;

    private String productSize;
}
