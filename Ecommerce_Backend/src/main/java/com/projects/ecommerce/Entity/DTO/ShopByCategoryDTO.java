package com.projects.ecommerce.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopByCategoryDTO {
    private String id;
    private String heading;
    private String category;
    private String imageUrl;
}
