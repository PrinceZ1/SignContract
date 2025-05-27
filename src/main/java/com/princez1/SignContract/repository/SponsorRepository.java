package com.princez1.SignContract.repository;

import com.princez1.SignContract.entity.SponsorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorRepository extends JpaRepository<SponsorEntity, Long> {
    List<SponsorEntity> findByActiveTrue();
} 