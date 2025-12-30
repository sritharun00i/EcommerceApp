package com.learn.Ecom.controller;

import com.learn.Ecom.model.User;
import com.learn.Ecom.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    record LoginRequest(String email, String password) {}

    @PostMapping({"/api/login", "/login"})
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.authenticate(request.email(), request.password())
                .map(user -> {
                    String token = authService.createToken(user);
                    User safe = user.safeCopy();
                    return ResponseEntity.ok(Map.of("token", token, "user", safe));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials")));
    }
}
