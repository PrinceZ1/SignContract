package com.princez1.SignContract.service.impl;

import com.princez1.SignContract.entity.Contract;
import com.princez1.SignContract.entity.ContractFundingItem;
import com.princez1.SignContract.entity.FundingItem;
import com.princez1.SignContract.entity.Sponsor;
import com.princez1.SignContract.enums.ContractStatus;
import com.princez1.SignContract.model.ContractModel;
import com.princez1.SignContract.model.ContractFundingItemModel;
import com.princez1.SignContract.model.FundingItemModel;
import com.princez1.SignContract.model.SponsorModel;
import com.princez1.SignContract.repository.ContractRepository;
import com.princez1.SignContract.repository.SponsorRepository;
import com.princez1.SignContract.repository.FundingItemRepository;
import com.princez1.SignContract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public ContractModel createContract(ContractModel contractModel) {
        contractModel.calculateTotalAmount();

        Contract contractEntity = new Contract();
        contractEntity.setType(contractModel.getType());
        contractEntity.setStartDate(contractModel.getStartDate());
        contractEntity.setEndDate(contractModel.getEndDate());
        contractEntity.setTerms(contractModel.getTerms());
        contractEntity.setAmount(contractModel.getTotalAmount());
        
        contractEntity.setStatus(ContractStatus.SIGNED);
        contractEntity.setCreatedAt(LocalDateTime.now());
        
        Sponsor sponsor = new Sponsor();
        sponsor.setName(contractModel.getSponsor().getName());
        sponsor.setContact(contractModel.getSponsor().getContact());
        sponsor.setPhone(contractModel.getSponsor().getPhone());
        sponsor.setEmail(contractModel.getSponsor().getEmail());
        sponsor.setAddress(contractModel.getSponsor().getAddress());
        sponsor.setActive(true);
        
        Sponsor savedSponsor = sponsorRepository.save(sponsor);
        contractEntity.setSponsor(savedSponsor);
        
        List<ContractFundingItem> fundingItems = contractModel.getFundingItems().stream()
            .map(item -> {
                ContractFundingItem fundingItem = new ContractFundingItem();
                
                FundingItem fi = new FundingItem();
                fi.setName(item.getFundingItem().getName());
                FundingItem savedFundingItem = fundingItemRepository.save(fi);
                
                fundingItem.setFundingItem(savedFundingItem);
                fundingItem.setValue(item.getValue());
                fundingItem.setContract(contractEntity);
                return fundingItem;
            })
            .collect(Collectors.toList());
        contractEntity.setContractFundingItems(fundingItems);

        Contract savedContract = contractRepository.save(contractEntity);
        
        return convertToModel(savedContract);
    }

    @Override
    public List<ContractModel> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        return contracts.stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public ContractModel getContractById(Long id) {
        Contract contract = contractRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contract not found"));
        return convertToModel(contract);
    }

    private ContractModel convertToModel(Contract contractEntity) {
        ContractModel model = new ContractModel();
        model.setId(contractEntity.getId());
        
        SponsorModel sponsorModel = new SponsorModel();
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

        List<ContractFundingItemModel> fundingItemModels = contractEntity.getContractFundingItems().stream()
            .map(item -> {
                ContractFundingItemModel itemModel = new ContractFundingItemModel();
                
                FundingItemModel fundingItemModel = new FundingItemModel();
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
