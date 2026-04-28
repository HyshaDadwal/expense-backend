package com.expense.app.controller;

import com.expense.app.model.User;
import com.expense.app.service.UserService;
import com.expense.app.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existingUser = userService.login(user.getEmail(), user.getPassword());
        return jwtUtil.generateToken(existingUser.getEmail());
    }
}