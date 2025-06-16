package com.projects.ecommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.ecommerce.Entity.Users;
import com.projects.ecommerce.Repository.UsersRepo;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    public List<Users> saveAllUsers(List<Users> userList) {
        return usersRepo.saveAll(userList);
    }

}
