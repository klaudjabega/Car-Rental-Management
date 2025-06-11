package com.example.drivenimbus.controller;
import com.example.drivenimbus.model.Users;
import com.example.drivenimbus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class UserController {

    @Autowired
    private UserService userService;

//    HTTP Method | Endpoint | Purpose
//    POST | /users | Create a new user
//    GET | /users | Get all users
//    GET | /users/{id} | Get a user by ID
//    PUT | /users/{id} | Update a user's details
//    DELETE | /users/{id} | Delete a user


    @Operation(summary = "Fetch all users // ADMIN")
    @Order(1)
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @Operation(summary = "Fetch user by userID // ADMIN")
    @Order(2)
    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable Long userId) {
        Users user = userService.fetchUserById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create a new user // ADMIN")
    @Order(6)
    @PostMapping
    public ResponseEntity<Users> saveUser(@RequestBody Users user) {
        userService.saveUser(user);
        return ResponseEntity.status(201).body(user);
    }

    @Operation(summary = "Update user's details // ADMIN")
    @Order(4)
    @PutMapping("/{userId}")
    public ResponseEntity<Users> updateUser(@RequestBody Users updatedUser, @PathVariable Long userId) {
        userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user by userID // ADMIN")
    @Order(3)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        if (userService.deleteUserById(userId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}/deactivate")
    @Order(5)
    public ResponseEntity<String> deactivateUserById(@PathVariable Long userId) {
        boolean deactivationSuccess = userService.deactivateUser(userId);
        return deactivationSuccess ? ResponseEntity.ok("User deactivated") : ResponseEntity.notFound().build();
    }
    // TODO Add endpoints for CLIENT USERS (AUTHENTICATION IMPLEMENTATION)

}
