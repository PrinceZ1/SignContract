package com.princez1.SignContract.entity;

import com.princez1.SignContract.enums.ContractStatus;
import com.princez1.SignContract.enums.ContractType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "contracts")
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sponsor_id")
    private SponsorEntity sponsor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "signed_by")
    private UserEntity signedBy;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ContractType type;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractFundingItemEntity> contractFundingItems;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    private String terms;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    private ContractStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
