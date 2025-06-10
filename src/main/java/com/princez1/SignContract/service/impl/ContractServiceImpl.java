package com.princez1.SignContract.service.impl;

import com.princez1.SignContract.entity.ContractEntity;
import com.princez1.SignContract.entity.FundingItemEntity;
import com.princez1.SignContract.entity.SponsorEntity;
import com.princez1.SignContract.entity.UserEntity;
import com.princez1.SignContract.enums.ContractStatus;
import com.princez1.SignContract.repository.ContractRepository;
import com.princez1.SignContract.repository.SponsorRepository;
import com.princez1.SignContract.repository.FundingItemRepository;
import com.princez1.SignContract.repository.UserRepository;
import com.princez1.SignContract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundingItemRepository fundingItemRepository;

    @Override
    @Transactional
    public ContractEntity createContract(ContractEntity contractEntity) {
        contractEntity.setStatus(ContractStatus.SIGNED);
        contractEntity.setCreatedAt(LocalDateTime.now());

        if (contractEntity.getSponsor() != null && contractEntity.getSponsor().getId() != null) {
            SponsorEntity sponsor = sponsorRepository.findById(contractEntity.getSponsor().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sponsor"));
            contractEntity.setSponsor(sponsor);
        }

        if (contractEntity.getSignedBy() != null && contractEntity.getSignedBy().getId() != null) {
            UserEntity user = userRepository.findById(contractEntity.getSignedBy().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người ký"));
            contractEntity.setSignedBy(user);
        }

        java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
        if (contractEntity.getContractFundingItems() != null) {
            for (var item : contractEntity.getContractFundingItems()) {
                if (item.getFundingItem() != null && item.getFundingItem().getId() != null) {
                    FundingItemEntity fundingItem = fundingItemRepository.findById(item.getFundingItem().getId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy hạng mục tài trợ"));
                    item.setFundingItem(fundingItem);
                }
                if (item.getValue() != null) {
                    totalAmount = totalAmount.add(item.getValue());
                }
                item.setContract(contractEntity);
            }
        }
        contractEntity.setAmount(totalAmount);

        return contractRepository.save(contractEntity);
    }

    @Override
    public List<ContractEntity> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public ContractEntity getContractById(Long id) {
        return contractRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contract not found"));
    }
}
