package com.projects.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.ecommerce.Entity.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, String>{

    boolean existsByEmail(String email);

    Users findByEmail(String email);

    Users findByReferralCode(String referralCode);

    boolean existsByReferralCode(String code);
}
