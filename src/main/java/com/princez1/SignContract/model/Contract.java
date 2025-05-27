package com.princez1.SignContract.model;

import com.princez1.SignContract.enums.ContractStatus;
import com.princez1.SignContract.enums.ContractType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Contract {
    private Long id;
    private Long sponsorId;
    private Sponsor sponsor;
    private ContractType type;
    private List<ContractFundingItem> fundingItems;
    private BigDecimal totalAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String terms;
    private ContractStatus status;
    private LocalDateTime createdAt;

    public void calculateTotalAmount() {
        this.totalAmount = fundingItems.stream()
            .map(ContractFundingItem::getValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 