package com.learn.Ecom.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// DTO for User response (since we deleted User entity, we need a DTO or reuse a common model)
// For now, let's create a UserDTO or verify if we can Map to a simple object.
// Ideally we should have a shared library or inner DTO class.
// I will create a UserDTO in com.learn.Ecom.dto package.

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);

    // Simple DTO class to verify user exists
    record UserDTO(Long id, String email, String name, String role) {
    }
}
