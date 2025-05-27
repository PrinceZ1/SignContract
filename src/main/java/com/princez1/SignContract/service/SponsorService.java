package com.princez1.SignContract.service;

import com.princez1.SignContract.entity.SponsorEntity;

import java.util.List;

public interface SponsorService {
    List<SponsorEntity> getActiveSponsors();
    SponsorEntity createSponsor(SponsorEntity sponsor);
}
