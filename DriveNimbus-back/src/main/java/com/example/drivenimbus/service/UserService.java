package com.example.drivenimbus.service;

import com.example.drivenimbus.dto.UserLoginDTO;
import com.example.drivenimbus.dto.UserRegistrationRequestDTO;
import com.example.drivenimbus.model.Role;
import com.example.drivenimbus.model.Users;
import com.example.drivenimbus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        String token = UUID.randomUUID().toString();

        Users user = new Users();
        user.setFullName(request.getFullName());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(hashedPassword);
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setUserRole(Role.USER); // or Role.USER if enum is capitalized
        user.setActive(false);
        user.setEmailVerified(false);
        user.setVerificationToken(token);
        user.setCreatedAt(new Date());
        user.setProfilePicture(null);

        userRepository.save(user);
        sendConfirmationEmail(user.getEmail(), token);
    }

    private void sendConfirmationEmail(String email, String token) {
        String confirmUrl = "http://localhost:8080/auth/confirm?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Confirm your email");
        message.setText("To confirm your email, please click on the link below:\n" + confirmUrl);
        mailSender.send(message);
    }

    public void confirmEmail(String token) {
        Users user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid confirmation token!"));
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setActive(true);

        userRepository.save(user);
    }

    public Users confirmLogin(UserLoginDTO loginRequest) {
        Optional<Users> userOpt = userRepository.findByemail(loginRequest.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        Users user = userOpt.get();
        if (!user.isActive()) {
            throw new RuntimeException("User is not active");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    public boolean changePasswordRequest(String email) {
        Optional<Users> userOpt = userRepository.findByemail(email);
        if (userOpt.isEmpty()) {
            return false;
        }
        Users user = userOpt.get();

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);
        sendResetPasswordEmail(email, token);
        return true;
    }

    private void sendResetPasswordEmail(String email, String token) {
        String resetUrl = "http://localhost:8080/password-reset/verify?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset your password");
        message.setText("To reset your password, please click on the link below:\n" + resetUrl);
        mailSender.send(message);
    }

    public boolean verifyToken(String token) {
        Optional<Users> userOpt = userRepository.findByVerificationToken(token);
        if (userOpt.isEmpty()) {
            return false;
        }
        return true;
    }

    public void resetPassword(String token, String password) {
        Optional<Users> userOpt = userRepository.findByVerificationToken(token);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid token or expired");
        }
        Users user = userOpt.get();
        String newHashedPassword = passwordEncoder.encode(password);
        user.setPasswordHash(newHashedPassword);
        user.setVerificationToken(null);
        userRepository.save(user);
    }


    public List<Users> fetchAllUsers() {
        return (List<Users>) userRepository.findAll();
    }

    public Users fetchUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    public Boolean deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


    public Users updateUser(Long userId, Users updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setFullName(updatedUser.getFullName());
            user.setEmail(updatedUser.getEmail());
            user.setPasswordHash(updatedUser.getPasswordHash());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setAddress(updatedUser.getAddress());
            user.setProfilePicture(updatedUser.getProfilePicture());
            user.setUserRole(updatedUser.getUserRole());
            user.setActive(updatedUser.isActive());
            user.setCreatedAt(updatedUser.getCreatedAt());
            return userRepository.save(user);
        }).orElse(null);
    }

    public boolean deactivateUser(Long userId) {
        Optional<Users> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            user.setActive(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
