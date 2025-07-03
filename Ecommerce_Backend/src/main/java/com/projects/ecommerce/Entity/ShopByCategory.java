package com.projects.ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopByCategory {

    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String heading;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String imageUrl;
}
