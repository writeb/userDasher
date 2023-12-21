package com.example.userdasher.service;

import com.example.userdasher.config.JwtService;
import com.example.userdasher.model.*;
import com.example.userdasher.repository.FoodRepository;
import com.example.userdasher.repository.PermissionRepository;
import com.example.userdasher.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserMethodsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final JwtService jwtService;
    private final FoodRepository foodRepository;

    public AuthenticationResponse addUser(User user){
        if (userRepository.findByEmail(user.getEmail()) == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userRepository.save(user);
            changeUserRole(newUser.getId(), Long.valueOf(1));
            String jwtToken = jwtService.generateToken(newUser);
            AuthenticationResponse response = new AuthenticationResponse();
            response.setToken(jwtToken);
            return response;
        }
        return null;
    }

    public AuthenticationResponse login(LoginReq req){
        User user = userRepository.findByEmail(req.getEmail());
        String jwtToken = jwtService.generateToken(user);
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);
        return response;
    }

    public void changeUserRole(Long userId, Long roleId){
        User user = userRepository.findById(userId).orElse(null);
        if (user!=null){
            List<Permission> permissions = permissionRepository.findPermissionsById(roleId);
            user.setPermissions(permissions);
            userRepository.save(user);
        }
    }

    public Food orderFood(Food food, Long userId){
        Food newFood = new Food();
        newFood.setUser(userRepository.findUserById(userId));
        newFood.setName(food.getName());
        newFood.setPrice(food.getPrice());
        newFood.setCafe(food.getCafe());

        return foodRepository.save(newFood);
    }

    public List<Food> getOrders(){
        return foodRepository.findAll();
    }

    public AuthenticationResponse registerDasher(User user){
        if (userRepository.findByEmail(user.getEmail()) == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userRepository.save(user);
            changeUserRole(newUser.getId(), Long.valueOf(3));
            String jwtToken = jwtService.generateToken(newUser);
            AuthenticationResponse response = new AuthenticationResponse();
            response.setToken(jwtToken);
            return response;
        }
        return null;
    }


}