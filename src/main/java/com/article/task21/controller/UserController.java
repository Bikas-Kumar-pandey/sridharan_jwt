package com.article.task21.controller;


import com.article.task21.entity.Address;
import com.article.task21.entity.LoginRequest;
import com.article.task21.entity.UserRequest;
import com.article.task21.entity.UserResponse;
import com.article.task21.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("/user")
    public UserResponse register(@RequestBody UserRequest request){
        return service.registerUser(request);
    }

    @GetMapping("/user/{id}")
    public UserResponse getById(@PathVariable int id){
        return service.getById(id);
    }

    @GetMapping("/user")
    public List<UserResponse> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping("/user/{id}")
    public UserResponse addAddress(@RequestBody List<Address> address, @PathVariable int id){
          return  service.addAddress(address,id);
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request){
        return service.validateLogin(request);
    }


}
