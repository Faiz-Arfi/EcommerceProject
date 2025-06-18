package com.projects.ecommerce.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePageDealsDTO {
    private String dealId;
    private String dealName;
    private String imageUrl;
    private String heading;
    private String subHeading;
    private String category;
}
