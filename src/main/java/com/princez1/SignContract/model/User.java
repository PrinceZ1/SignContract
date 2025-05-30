package com.princez1.SignContract.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String position;
    private String password;
} 