package com.princez1.SignContract.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ContractFundingItemModel {
    private FundingItemModel fundingItem;
    private BigDecimal value;
} 