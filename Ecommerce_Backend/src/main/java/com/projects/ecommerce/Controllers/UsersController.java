package com.projects.ecommerce.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projects.ecommerce.Entity.Users;
import com.projects.ecommerce.Services.UsersService;

@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/users/dummyusers")
    public List<Users> addDummyUsers(){
        List<Users> userList = new ArrayList<>();
        Users user1 = new Users(null, "Kishore", "Kumar", "kishorekumar1@mail.com", "12345678", null, null);
        Users user2 = new Users(null, "Kishore", "Kumar", "kishorekumar2@mail.com", "12345678", null, null);
        Users user3 = new Users(null, "Kishore", "Kumar", "kishorekumar3@mail.com", "12345678", null, null);
        Users user4 = new Users(null, "Kishore", "Kumar", "kishorekumar4@mail.com", "12345678", null, null);
        Users user5 = new Users(null, "Kishore", "Kumar", "kishorekumar5@mail.com", "12345678", null, null);
        Users user6 = new Users(null, "Kishore", "Kumar", "kishorekumar6@mail.com", "12345678", null, null);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);

        return usersService.saveAllUsers(userList);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Users registerUser(@RequestBody Users user){
        return usersService.saveUser(user);
    }

    @GetMapping("/users")
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public Users getUserById(@PathVariable String userId) {
        System.out.println("User ID: " + userId);
        return usersService.getUserById(userId);
    }

}
