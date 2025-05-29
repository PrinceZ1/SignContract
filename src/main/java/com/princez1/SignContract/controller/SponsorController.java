package com.princez1.SignContract.controller;

import com.princez1.SignContract.entity.SponsorEntity;
import com.princez1.SignContract.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sponsors")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @GetMapping
    public ResponseEntity<List<SponsorEntity>> getAllSponsors() {
        List<SponsorEntity> sponsors = sponsorService.getAllSponsors();
        return ResponseEntity.ok(sponsors);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<SponsorEntity> getSponsorById(@PathVariable Long id) {
        SponsorEntity sponsor = sponsorService.getSponsorById(id);
        return ResponseEntity.ok(sponsor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SponsorEntity> updateSponsor(@PathVariable Long id, @RequestBody SponsorEntity sponsor) {
        SponsorEntity updated = sponsorService.updateSponsor(id, sponsor);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SponsorEntity> changeStatus(@PathVariable Long id, @RequestParam boolean active) {
        SponsorEntity updated = sponsorService.changeStatus(id, active);
        return ResponseEntity.ok(updated);
    }
} 