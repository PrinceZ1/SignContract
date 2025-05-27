package com.princez1.SignContract.controller;

import com.princez1.SignContract.entity.SponsorEntity;
import com.princez1.SignContract.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sponsors")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @GetMapping("/active")
    public ResponseEntity<List<SponsorEntity>> getActiveSponsors() {
        List<SponsorEntity> sponsors = sponsorService.getActiveSponsors();
        return ResponseEntity.ok(sponsors);
    }

    @PostMapping
    public ResponseEntity<SponsorEntity> createSponsor(@RequestBody SponsorEntity sponsor) {
        SponsorEntity created = sponsorService.createSponsor(sponsor);
        return ResponseEntity.ok(created);
    }
} 