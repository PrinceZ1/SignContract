package com.princez1.SignContract.controller;

import com.princez1.SignContract.entity.Sponsor;
import com.princez1.SignContract.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sponsors")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @GetMapping("/active")
    public ResponseEntity<List<Sponsor>> getActiveSponsors() {
        List<Sponsor> sponsors = sponsorService.getActiveSponsors();
        return ResponseEntity.ok(sponsors);
    }
} 