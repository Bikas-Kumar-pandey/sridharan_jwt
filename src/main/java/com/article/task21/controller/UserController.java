package com.article.task21.controller;


import com.article.task21.dto.LoginRequest;
import com.article.task21.entity.Address;
import com.article.task21.dto.UserRequest;
import com.article.task21.dto.UserResponse;
import com.article.task21.jwtutil.JwtUtil;
import com.article.task21.service.MyUserDetailsService;
import com.article.task21.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    private final MyUserDetailsService myUserDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public UserController(UserService service, MyUserDetailsService myUserDetailsService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.service = service;
        this.myUserDetailsService = myUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/user/register")
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

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginRequest request){
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException e){
            throw new RuntimeException("Invalid username/password");
        }
        UserDetails userDetails= myUserDetailsService.loadUserByUsername(request.getUsername());
        return jwtUtil.generateToken(userDetails);
    }




}
