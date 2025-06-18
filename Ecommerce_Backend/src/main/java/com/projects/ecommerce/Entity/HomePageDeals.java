package com.projects.ecommerce.Entity;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "home_page_deals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomePageDeals {
    @Id
    @GeneratedValue
    @UuidGenerator
    String dealId;
    @Column(nullable = false, unique = true)
    String dealName;
    @Column(nullable = false)
    String imageUrl;
    @Column(nullable = false)
    String heading;
    @Column(nullable = false)
    String subHeading;
    @Column(nullable = false)
    String category;

    @ManyToMany(mappedBy = "homePageDeals")
    private List<Users> user;
}

