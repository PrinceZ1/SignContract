package com.princez1.SignContract.service.impl;

import com.princez1.SignContract.entity.Sponsor;
import com.princez1.SignContract.repository.SponsorRepository;
import com.princez1.SignContract.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SponsorServiceImpl implements SponsorService {

    @Autowired
    private SponsorRepository sponsorRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public List<Sponsor> getActiveSponsors() {
        return sponsorRepository.findByActiveTrue();
    }

    @Override
    public Sponsor createSponsor(Sponsor sponsor) {
        validateSponsor(sponsor);
        sponsor.setActive(true);
        return sponsorRepository.save(sponsor);
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
} 