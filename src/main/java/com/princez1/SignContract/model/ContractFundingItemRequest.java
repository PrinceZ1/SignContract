package com.princez1.SignContract.model;

import com.princez1.SignContract.entity.FundingItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractFundingItemRequest {
    private FundingItem fundingItem;
    private BigDecimal value;
} 