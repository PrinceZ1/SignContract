package com.princez1.SignContract.service;

import com.princez1.SignContract.entity.SponsorEntity;
import java.util.List;

public interface SponsorService {
    List<SponsorEntity> getAllSponsors();
    List<SponsorEntity> getActiveSponsors();
    SponsorEntity createSponsor(SponsorEntity sponsor);
    SponsorEntity getSponsorById(Long id);
    SponsorEntity updateSponsor(Long id, SponsorEntity sponsor);
    SponsorEntity changeStatus(Long id, boolean active);
} 