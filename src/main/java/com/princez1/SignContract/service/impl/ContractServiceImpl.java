package com.princez1.SignContract.service.impl;

import com.princez1.SignContract.entity.ContractEntity;
import com.princez1.SignContract.entity.ContractFundingItemEntity;
import com.princez1.SignContract.entity.FundingItemEntity;
import com.princez1.SignContract.entity.SponsorEntity;
import com.princez1.SignContract.enums.ContractStatus;
import com.princez1.SignContract.model.Contract;
import com.princez1.SignContract.model.ContractFundingItem;
import com.princez1.SignContract.model.FundingItem;
import com.princez1.SignContract.model.Sponsor;
import com.princez1.SignContract.repository.ContractRepository;
import com.princez1.SignContract.repository.SponsorRepository;
import com.princez1.SignContract.repository.FundingItemRepository;
import com.princez1.SignContract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private FundingItemRepository fundingItemRepository;

    @Override
    @Transactional
    public Contract createContract(Contract contract) {
        contract.calculateTotalAmount();

        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setType(contract.getType());
        contractEntity.setStartDate(contract.getStartDate());
        contractEntity.setEndDate(contract.getEndDate());
        contractEntity.setTerms(contract.getTerms());
        contractEntity.setAmount(contract.getTotalAmount());
        contractEntity.setStatus(ContractStatus.SIGNED);
        contractEntity.setCreatedAt(LocalDateTime.now());

        SponsorEntity sponsor = sponsorRepository.findById(contract.getSponsorId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà tài trợ"));
        contractEntity.setSponsor(sponsor);

        List<ContractFundingItemEntity> fundingItems = new ArrayList<>();
        
        for (ContractFundingItem item : contract.getFundingItems()) {
            if (item.getValue() == null) {
                throw new RuntimeException("Giá trị hạng mục tài trợ không được để trống");
            }
            
            Long itemId = item.getFundingItem().getId();
            FundingItemEntity fundingItemEntity = fundingItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hạng mục tài trợ với ID: " + itemId));
            
            ContractFundingItemEntity contractFundingItem = new ContractFundingItemEntity();
            contractFundingItem.setFundingItem(fundingItemEntity);
            contractFundingItem.setValue(item.getValue());
            contractFundingItem.setContract(contractEntity);
            
            fundingItems.add(contractFundingItem);
        }
        
        contractEntity.setContractFundingItems(fundingItems);

        ContractEntity savedContract = contractRepository.save(contractEntity);
        return convertToModel(savedContract);
    }

    @Override
    public List<Contract> getAllContracts() {
        List<ContractEntity> contracts = contractRepository.findAll();
        return contracts.stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public Contract getContractById(Long id) {
        ContractEntity contract = contractRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contract not found"));
        return convertToModel(contract);
    }

    private Contract convertToModel(ContractEntity contractEntity) {
        Contract model = new Contract();
        model.setId(contractEntity.getId());
        
        Sponsor sponsorModel = new Sponsor();
        sponsorModel.setName(contractEntity.getSponsor().getName());
        sponsorModel.setContact(contractEntity.getSponsor().getContact());
        sponsorModel.setPhone(contractEntity.getSponsor().getPhone());
        sponsorModel.setEmail(contractEntity.getSponsor().getEmail());
        sponsorModel.setAddress(contractEntity.getSponsor().getAddress());
        model.setSponsor(sponsorModel);

        model.setType(contractEntity.getType());
        model.setTotalAmount(contractEntity.getAmount());
        model.setStartDate(contractEntity.getStartDate());
        model.setEndDate(contractEntity.getEndDate());
        model.setTerms(contractEntity.getTerms());
        model.setStatus(contractEntity.getStatus());
        model.setCreatedAt(contractEntity.getCreatedAt());

        List<ContractFundingItem> fundingItemModels = contractEntity.getContractFundingItems().stream()
            .map(item -> {
                ContractFundingItem itemModel = new ContractFundingItem();
                
                FundingItem fundingItemModel = new FundingItem();
                fundingItemModel.setId(item.getFundingItem().getId());
                fundingItemModel.setName(item.getFundingItem().getName());
                itemModel.setFundingItem(fundingItemModel);
                
                itemModel.setValue(item.getValue());
                return itemModel;
            })
            .collect(Collectors.toList());
        model.setFundingItems(fundingItemModels);

        return model;
    }
}
