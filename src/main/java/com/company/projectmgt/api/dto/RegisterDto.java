package com.company.projectmgt.api.dto;

import java.time.LocalDate;

public class RegisterDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String fullName;
    private LocalDate dob;

    public RegisterDto(LocalDate dob, String fullName, String phone, String email, String password, String username) {
        this.dob = dob;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.username = username;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
