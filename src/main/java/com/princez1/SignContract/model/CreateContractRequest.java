package com.princez1.SignContract.model;

import com.princez1.SignContract.entity.Sponsor;
import com.princez1.SignContract.enums.ContractType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateContractRequest {
    private Long sponsorId;
    private Sponsor sponsor;
    private ContractType type;
    private List<ContractFundingItemRequest> items;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String terms;
} 