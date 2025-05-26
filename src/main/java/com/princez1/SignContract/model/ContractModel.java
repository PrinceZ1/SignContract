package com.princez1.SignContract.model;

import com.princez1.SignContract.enums.ContractStatus;
import com.princez1.SignContract.enums.ContractType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContractModel {
    private Long id;
    private SponsorModel sponsor;
    private ContractType type;
    private List<ContractFundingItemModel> fundingItems;
    private BigDecimal totalAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String terms;
    private ContractStatus status;
    private LocalDateTime createdAt;

    public void calculateTotalAmount() {
        this.totalAmount = fundingItems.stream()
            .map(ContractFundingItemModel::getValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 