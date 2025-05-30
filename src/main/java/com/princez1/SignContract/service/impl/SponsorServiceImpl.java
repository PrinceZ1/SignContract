package com.princez1.SignContract.service.impl;

import com.princez1.SignContract.entity.SponsorEntity;
import com.princez1.SignContract.model.Sponsor;
import com.princez1.SignContract.repository.SponsorRepository;
import com.princez1.SignContract.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SponsorServiceImpl implements SponsorService {

    @Autowired
    private SponsorRepository sponsorRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public List<Sponsor> getAllSponsors() {
        return sponsorRepository.findAll().stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Sponsor> getActiveSponsors() {
        return sponsorRepository.findByActiveTrue().stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public Sponsor createSponsor(Sponsor sponsor) {
        validateSponsor(sponsor);
        SponsorEntity entity = convertToEntity(sponsor);
        entity.setActive(true);
        return convertToModel(sponsorRepository.save(entity));
    }

    @Override
    public Sponsor getSponsorById(Long id) {
        return convertToModel(sponsorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sponsor not found with id: " + id)));
    }

    @Override
    public Sponsor updateSponsor(Long id, Sponsor sponsor) {
        SponsorEntity existingSponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sponsor not found with id: " + id));
        
        existingSponsor.setName(sponsor.getName());
        existingSponsor.setEmail(sponsor.getEmail());
        existingSponsor.setPhone(sponsor.getPhone());
        existingSponsor.setAddress(sponsor.getAddress());
        existingSponsor.setActive(sponsor.isActive());
        
        return convertToModel(sponsorRepository.save(existingSponsor));
    }

    @Override
    public Sponsor changeStatus(Long id, boolean active) {
        SponsorEntity sponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sponsor not found with id: " + id));
        sponsor.setActive(active);
        return convertToModel(sponsorRepository.save(sponsor));
    }

    private void validateSponsor(Sponsor sponsor) {
        if (!StringUtils.hasText(sponsor.getName())) {
            throw new IllegalArgumentException("Tên nhà tài trợ không được rỗng");
        }
        if (!StringUtils.hasText(sponsor.getContact())) {
            throw new IllegalArgumentException("Thông tin liên hệ không được rỗng");
        }
        if (!StringUtils.hasText(sponsor.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại không được rỗng");
        }
        if (!StringUtils.hasText(sponsor.getEmail())) {
            throw new IllegalArgumentException("Email không được rỗng");
        }
        if (!EMAIL_PATTERN.matcher(sponsor.getEmail()).matches()) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (!StringUtils.hasText(sponsor.getAddress())) {
            throw new IllegalArgumentException("Địa chỉ không được rỗng");
        }
    }

    private Sponsor convertToModel(SponsorEntity entity) {
        Sponsor model = new Sponsor();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setContact(entity.getContact());
        model.setPhone(entity.getPhone());
        model.setEmail(entity.getEmail());
        model.setAddress(entity.getAddress());
        model.setActive(entity.isActive());
        return model;
    }

    private SponsorEntity convertToEntity(Sponsor model) {
        SponsorEntity entity = new SponsorEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setContact(model.getContact());
        entity.setPhone(model.getPhone());
        entity.setEmail(model.getEmail());
        entity.setAddress(model.getAddress());
        entity.setActive(model.isActive());
        return entity;
    }
} 