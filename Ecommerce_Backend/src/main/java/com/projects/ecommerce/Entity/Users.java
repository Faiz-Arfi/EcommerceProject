package com.projects.ecommerce.Entity;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String UserId;

    @Column(nullable = false)
    private String firstName;
    
    private String lastName;
    
    @Email(message = "Email already registred")
    @NotNull(message = "Email cannot be null")
    private String email;
    
    @Column(nullable = false, length = 8)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<CouponsCentral> couponsCentrals;
    @OneToMany(mappedBy = "user")
    private List<HomePageDeals> homePageDeals;

}
