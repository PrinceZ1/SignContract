package com.princez1.SignContract.model;

import lombok.Data;

@Data
public class Sponsor {
    private Long id;
    private String name;
    private String contact;
    private String phone;
    private String email;
    private String address;
    private boolean active;
} 