package com.princez1.SignContract.service.impl;

import com.princez1.SignContract.entity.Contract;
import com.princez1.SignContract.entity.FundingItem;
import com.princez1.SignContract.entity.Sponsor;
import com.princez1.SignContract.entity.User;
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
    public Contract createContract(Contract contract) {
        contract.setStatus(ContractStatus.SIGNED);
        contract.setCreatedAt(LocalDateTime.now());

        if (contract.getSponsor() != null && contract.getSponsor().getId() != null) {
            Sponsor sponsor = sponsorRepository.findById(contract.getSponsor().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sponsor"));
            contract.setSponsor(sponsor);
        }

        if (contract.getSignedBy() != null && contract.getSignedBy().getId() != null) {
            User user = userRepository.findById(contract.getSignedBy().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người ký"));
            contract.setSignedBy(user);
        }

        java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
        if (contract.getContractFundingItems() != null) {
            for (var item : contract.getContractFundingItems()) {
                if (item.getFundingItem() != null && item.getFundingItem().getId() != null) {
                    FundingItem fundingItem = fundingItemRepository.findById(item.getFundingItem().getId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy hạng mục tài trợ"));
                    item.setFundingItem(fundingItem);
                }
                if (item.getValue() != null) {
                    totalAmount = totalAmount.add(item.getValue());
                }
                item.setContract(contract);
            }
        }
        contract.setAmount(totalAmount);

        return contractRepository.save(contract);
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contract getContractById(Long id) {
        return contractRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contract not found"));
    }
}
