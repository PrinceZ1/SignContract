package com.princez1.SignContract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sponsors")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contact;
    private String phone;
    private String email;
    private String address;
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL)
    private List<Contract> contracts;
}

