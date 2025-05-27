package com.princez1.SignContract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "contract_funding_items")
public class ContractFundingItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;

    @ManyToOne
    @JoinColumn(name = "funding_item_id")
    private FundingItemEntity fundingItem;

    @Column(nullable = false)
    private BigDecimal value;
}
