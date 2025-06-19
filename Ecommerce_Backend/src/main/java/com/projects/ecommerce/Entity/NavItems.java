package com.projects.ecommerce.Entity;

import java.util.Map;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "nav_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavItems {
    @Id
    @UuidGenerator
    String itemId;
    @Column(nullable = false, unique = true)
    String heading;
    @ElementCollection
    @CollectionTable(name = "nav_item_details", joinColumns = @JoinColumn(name = "item_id"))
    @MapKeyColumn(name = "item_subheading")
    @Column(name = "item_url")
    Map<String, String> items;
}
