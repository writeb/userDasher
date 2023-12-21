package com.example.userdasher.contoller;

import com.example.userdasher.model.AuthenticationResponse;
import com.example.userdasher.model.Food;
import com.example.userdasher.model.LoginReq;
import com.example.userdasher.model.User;
import com.example.userdasher.service.UserMethodsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserMethodsService userMethodsService;

    @PostMapping(value = "/auth/login")
    public AuthenticationResponse login(@RequestBody LoginReq req){
        return userMethodsService.login(req);
    }

    @PostMapping(value = "/auth/register")
    public AuthenticationResponse signUp(@RequestBody User userDTO){
        return userMethodsService.addUser(userDTO);
    }

    @PostMapping(value = "/order/{id}")
    public Food orderFood(@RequestBody Food food, @PathVariable(name = "id") Long id){
        return userMethodsService.orderFood(food, id);
    }

    @GetMapping(value = "/get-orders")
    public List<Food> getOrders(){
        return userMethodsService.getOrders();
    }

    @PostMapping(value = "/auth/register-dasher")
    public AuthenticationResponse register(@RequestBody User user){
        return userMethodsService.registerDasher(user);
    }




}
