package com.article.task21.service;


import com.article.task21.entity.*;
import com.article.task21.exception.LoginUnSuccess;
import com.article.task21.exception.UserNotFound;
import com.article.task21.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public static final String AUTHORIZED="Authorized";
    public static final String USER_NOT_FOUND="User not present with id : ";
    public static final String UNAUTHORIZED="Invalid username/password";
    public static final String MAIL_ALREADY_EXISTS="Mail id already exists";
    public static final String MOBILE_ALREADY_EXISTS="Mobile no: already exists";

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponse registerUser(UserRequest request){
        User user=new User();
        String name= request.getFirstName()+" "+request.getLastName();
        user.setName(name);
        if(userRepo.existsByMobile(request.getMobile())){
            throw new RuntimeException(MOBILE_ALREADY_EXISTS);
        }
        user.setMobile(request.getMobile());
        if(userRepo.existsByMail(request.getMail())){
            throw new RuntimeException(MAIL_ALREADY_EXISTS);
        }
        user.setMail(request.getMail());
        user.setPassword(request.getPassword());

        User savedUser = userRepo.save(user);
        UserResponse userResponse=new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setName(savedUser.getName());
        userResponse.setMobile(savedUser.getMobile());
        userResponse.setMail(savedUser.getMail());
        List<Address> addresses = savedUser.getAddresses();
        List<String> addressResponse=new ArrayList<>();
        for(Address address:addresses){
            addressResponse.add(address.getAddress());
        }
        userResponse.setAddress(addressResponse);

        return userResponse;
    }

    public UserResponse getById(int id){
        Optional<User> byId = userRepo.findById(id);
        if(byId.isEmpty()){
            throw new UserNotFound(USER_NOT_FOUND+id);
        }
        User user = byId.get();
        UserResponse response=new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setMobile(user.getMobile());
        response.setMail(user.getMail());
        List<Address> addresses = user.getAddresses();
        List<String> addressResponse=new ArrayList<>();
        for(Address address:addresses){
            addressResponse.add(address.getAddress());
        }
        response.setAddress(addressResponse);
        return response;
    }
    public List<UserResponse> getAllUsers(){
        List<User> allUsers = userRepo.findAll();
        List<UserResponse> userResponses=new ArrayList<>();
        for(User user:allUsers){
            UserResponse response=new UserResponse();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setMobile(user.getMobile());
            response.setMail(user.getMail());
            List<Address> addresses = user.getAddresses();
            List<String> addressResponse=new ArrayList<>();
            if(addresses!=null) {
                for (Address address : addresses) {
                    addressResponse.add(address.getAddress());
                }
                response.setAddress(addressResponse);
            }
            userResponses.add(response);
        }
        return userResponses;
    }

    public UserResponse addAddress(List<Address> address,int id){
        Optional<User> byId = userRepo.findById(id);
        if(byId.isEmpty()){
            throw new UserNotFound(USER_NOT_FOUND+id);
        }
        User user = byId.get();
        List<Address> addresses=null;
        if(user.getAddresses()!=null){
             addresses = user.getAddresses();
        }else {
            addresses=new ArrayList<>();
        }

        addresses.addAll(address);

        user.setAddresses(addresses);
        User savedUser=userRepo.save(user);
        UserResponse userResponse=new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setName(savedUser.getName());
        userResponse.setMobile(savedUser.getMobile());
        userResponse.setMail(savedUser.getMail());
        List<Address> addressesSaved = savedUser.getAddresses();
        List<String> addressResponse=new ArrayList<>();
        for(Address i:addressesSaved){
            addressResponse.add(i.getAddress());
        }
        userResponse.setAddress(addressResponse);

        return userResponse;
    }

    public Object validateLogin(LoginRequest request){
        String password = request.getPassword();
        String username = request.getUsername();
        if(userRepo.existsByMail(username) || userRepo.existsByMobile(username)){
            User user=null;
            if(userRepo.existsByMail(username)){
                user=userRepo.findByMail(username);
            }
            else {
                user=userRepo.findByMobile(username);
            }
            String password1 = user.getPassword();
            if(password.equals(password1)) {
                AuthorizedLogin authorizedLogin=new AuthorizedLogin();
                authorizedLogin.setStatus(AUTHORIZED);
                authorizedLogin.setName(user.getName());
               return authorizedLogin;
            }
            else throw new LoginUnSuccess(UNAUTHORIZED);
        }else {
            throw new LoginUnSuccess(UNAUTHORIZED);
        }
    }
}
