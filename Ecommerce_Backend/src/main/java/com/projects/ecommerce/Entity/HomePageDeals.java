package com.projects.ecommerce.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "home_page_deals")
@Getter
@Setter
public class HomePageDeals {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long dealId;
    @Column(nullable = false)
    String dealName;
    @Column(nullable = false)
    String imageUrl;
    @Column(nullable = false)
    String heading;
    @Column(nullable = false)
    String subHeading;
}

