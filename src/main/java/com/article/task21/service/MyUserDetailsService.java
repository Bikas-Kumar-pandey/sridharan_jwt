package com.article.task21.service;

import com.article.task21.entity.User;
import com.article.task21.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional=userRepo.findByName(username);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found with name : "+username);
        }
        User user=userOptional.get();
        return new MyUSerPrinciple(user);
    }
}
