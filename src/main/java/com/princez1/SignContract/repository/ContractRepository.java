package com.princez1.SignContract.repository;

import com.princez1.SignContract.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface    ContractRepository extends JpaRepository<ContractEntity, Long> {
} 