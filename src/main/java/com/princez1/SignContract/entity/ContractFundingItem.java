package com.princez1.SignContract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "contract_funding_items")
public class ContractFundingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "funding_item_id")
    private FundingItem fundingItem;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal value;
}
