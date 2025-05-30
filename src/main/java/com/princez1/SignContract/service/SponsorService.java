package com.princez1.SignContract.service;

import com.princez1.SignContract.model.Sponsor;
import java.util.List;

public interface SponsorService {
    List<Sponsor> getAllSponsors();
    List<Sponsor> getActiveSponsors();
    Sponsor createSponsor(Sponsor sponsor);
    Sponsor getSponsorById(Long id);
    Sponsor updateSponsor(Long id, Sponsor sponsor);
    Sponsor changeStatus(Long id, boolean active);
} 