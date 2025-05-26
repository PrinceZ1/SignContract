package com.princez1.SignContract.service;

import com.princez1.SignContract.entity.Sponsor;

import java.util.List;

public interface SponsorService {
    List<Sponsor> getActiveSponsors();
    Sponsor createSponsor(Sponsor sponsor);
}
