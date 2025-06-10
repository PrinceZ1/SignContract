package com.princez1.SignContract.repository;

import com.princez1.SignContract.entity.FundingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FundingItemRepository extends JpaRepository<FundingItem, Long> {
    Optional<FundingItem> findByName(String name);
} 