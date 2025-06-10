package com.projects.ecommerce.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HomePageDeals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long dealId;
    @Column(nullable = false)
    String dealName;
    String dealImageUrl1;
    String dealImageUrl2;
    String dealImageUrl3;
    String dealImageUrl4;
}

