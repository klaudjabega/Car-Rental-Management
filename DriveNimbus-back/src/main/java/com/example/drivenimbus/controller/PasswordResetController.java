package com.example.drivenimbus.controller;


import com.example.drivenimbus.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/password-reset")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})

public class PasswordResetController {
    @Autowired
    private UserService userService;

    @Order(2)
    @Operation(summary = "Handle an incoming password reset request")
    @PostMapping("/request")
    public ResponseEntity<String> changePasswordRequest(@RequestBody Map<String, String> email) {
        String emailAddress = email.get("email");
        if (emailAddress == null) {
            return ResponseEntity.badRequest().body("Email address is required");
        }

        if (userService.changePasswordRequest( emailAddress )) {
            return ResponseEntity.ok("Password reset request sent to " + emailAddress);
        };
        return ResponseEntity.badRequest().body("Email address not found");
    }

    @Order(1)
    @Operation(summary = "Verify a password reset token")
    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        if (userService.verifyToken( token )) {
            return ResponseEntity.ok("Token is valid. Please enter your new password.");
        }
        return ResponseEntity.badRequest().body("Invalid token or expired");
    }

    @Order(3)
    @Operation(summary = "Reset a user's password")
    @PostMapping("/submit")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> passwordReset) {
        String token = passwordReset.get("token");
        String password = passwordReset.get("password");
        if (token == null || password == null) {
            return ResponseEntity.badRequest().body("Token and password are required");
        }
        userService.resetPassword( token, password);
        return ResponseEntity.ok("Password reset successful");
    }
}
