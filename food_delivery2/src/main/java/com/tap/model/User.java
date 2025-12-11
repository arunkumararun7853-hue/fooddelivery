package com.tap.model;

import java.sql.Timestamp;

public class User {
    private int userId;             // Unique identifier (managed by database)
    private String name;           // User's full name
    private String username;       // Login username (userName in constructor)
    private String password;       // User password
    private String email;          // User email
    private String phone;          // Phone number
    private String address;        // Physical address
    private String city;           // City
    private String role;           // User role (customer, admin, etc.)
    private Timestamp createdDate; // Account creation date
    private Timestamp lastLoginDate; // Last login timestamp

    public User() { }

    // Constructor matching your specification
    public User(String name, String userName, String password, String email, String phone, String address, String role, Timestamp createdDate, Timestamp lastLoginDate) {
        super();
        this.name = name;
        this.username = userName;  // Note: userName parameter maps to username field
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createdDate = createdDate;
        this.lastLoginDate = lastLoginDate;
    }

    // Simple constructor for signup (without timestamps)
    public User(String name, String username, String password, String email, String phone, String address, String city) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.role = "customer"; // Default role
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", role='" + role + '\'' +
                ", createdDate=" + createdDate +
                ", lastLoginDate=" + lastLoginDate +
                '}';
    }
}
