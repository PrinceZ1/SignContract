package com.princez1.SignContract.repository;

import com.princez1.SignContract.entity.FundingItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FundingItemRepository extends JpaRepository<FundingItemEntity, Long> {
    Optional<FundingItemEntity> findByName(String name);
} 