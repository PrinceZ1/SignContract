package com.princez1.SignContract.controller;

import com.princez1.SignContract.model.Sponsor;
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
    public ResponseEntity<List<Sponsor>> getAllSponsors() {
        List<Sponsor> sponsors = sponsorService.getAllSponsors();
        return ResponseEntity.ok(sponsors);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Sponsor>> getActiveSponsors() {
        List<Sponsor> sponsors = sponsorService.getActiveSponsors();
        return ResponseEntity.ok(sponsors);
    }
    
    @PostMapping
    public ResponseEntity<Sponsor> createSponsor(@RequestBody Sponsor sponsor) {
        Sponsor created = sponsorService.createSponsor(sponsor);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sponsor> getSponsorById(@PathVariable Long id) {
        Sponsor sponsor = sponsorService.getSponsorById(id);
        return ResponseEntity.ok(sponsor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sponsor> updateSponsor(@PathVariable Long id, @RequestBody Sponsor sponsor) {
        Sponsor updated = sponsorService.updateSponsor(id, sponsor);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Sponsor> changeStatus(@PathVariable Long id, @RequestParam boolean active) {
        Sponsor updated = sponsorService.changeStatus(id, active);
        return ResponseEntity.ok(updated);
    }
} 