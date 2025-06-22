package com.projects.ecommerce.Entity;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @NotNull(message = "FirstName cannot be null")
    private String firstName;
    
    private String lastName;
    
    @Email(message = "Email already registred")
    @NotNull(message = "Email cannot be null")
    private String email;
    
    @Column(nullable = false)
    @NotNull(message = "Password cannot be null")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_coupons_central", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "coupons_central_id"))
               
    private List<CouponsCentral> couponsCentrals;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_home_page_deals", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "deal_id"))
    private List<HomePageDeals> homePageDeals;

    private List<String> roles;

}
