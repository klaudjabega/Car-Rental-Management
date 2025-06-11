package com.example.drivenimbus.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String fullName;

    @Column(unique = true)
    private String email; // Unique
    private String passwordHash;
    private String phoneNumber;
    private String address;
    private String profilePicture; // URL || file path

    @Enumerated(EnumType.STRING)
    private Role userRole; // User || ADMIN
    private Boolean isActive;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private Boolean emailVerified;
    private String verificationToken; // UUID or random string for email confirmation


    public Users() {
    }

    public Users(Long userId, String fullName, String email, String passwordHash, String phoneNumber, String address, String profilePicture, Role userRole) {
        this.userId = userId;
        this.fullName= fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.profilePicture = profilePicture;
        this.userRole = userRole;
        this.isActive = false;
        createdAt = new Date();
        emailVerified = false;
        verificationToken = null;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userID) {
        this.userId = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
         this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Boolean getEmailVerified() {
        return emailVerified;
    }
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    public String getVerificationToken() {
        return verificationToken;
    }
    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}
