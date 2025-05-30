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
    private Sponsor sponsor;
    private User signedBy;
    private ContractType type;
    private List<ContractFundingItem> fundingItems;
    private BigDecimal totalAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String terms;
    private ContractStatus status;
    private LocalDateTime createdAt;

    public void calculateTotalAmount() {
        totalAmount = BigDecimal.ZERO;
        
        if (fundingItems != null) {
            for (ContractFundingItem item : fundingItems) {
                if (item.getValue() != null) {
                    totalAmount = totalAmount.add(item.getValue());
                }
            }
        }
    }
} 