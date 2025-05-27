package com.princez1.SignContract.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ContractFundingItem {
    private FundingItem fundingItem;
    private BigDecimal value;
} 