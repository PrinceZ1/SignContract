package com.princez1.SignContract.repository;

import com.princez1.SignContract.entity.FundingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingItemRepository extends JpaRepository<FundingItem, Long> {
} 