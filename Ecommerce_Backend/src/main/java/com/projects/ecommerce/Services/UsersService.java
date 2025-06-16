package com.projects.ecommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.Entity.Users;
import com.projects.ecommerce.Repository.UsersRepo;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Users> saveAllUsers(List<Users> userList) {
        return usersRepo.saveAll(userList);
    }

    public Users saveUser(Users user) {
        if(usersRepo.existsByEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user already exists with the same email");
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
        }
        else if(user.getFirstName() == null || user.getFirstName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name cannot be null or empty");
        }
        else if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return usersRepo.save(user);
    }

    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    public Users getUserById(String userId) {
        return usersRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
    }

}
