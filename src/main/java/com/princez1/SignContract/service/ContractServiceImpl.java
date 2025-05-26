package com.princez1.SignContract.service;

import com.princez1.SignContract.entity.Contract;
import com.princez1.SignContract.entity.ContractFundingItem;
import com.princez1.SignContract.entity.FundingItem;
import com.princez1.SignContract.entity.Sponsor;
import com.princez1.SignContract.enums.ContractStatus;
import com.princez1.SignContract.model.CreateContractRequest;
import com.princez1.SignContract.repository.ContractRepository;
import com.princez1.SignContract.repository.FundingItemRepository;
import com.princez1.SignContract.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public Contract createContract(CreateContractRequest request) {
        // Tạo contract mới
        Contract contract = new Contract();
        
        // Xử lý sponsor
        if (request.getSponsorId() != null) {
            // Tìm sponsor theo ID
            Sponsor sponsor = sponsorRepository.findById(request.getSponsorId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhà tài trợ với ID: " + request.getSponsorId()));
            contract.setSponsor(sponsor);
        } else if (request.getSponsor() != null) {
            // Tạo mới sponsor
            request.getSponsor().setActive(true);
            Sponsor sponsor = sponsorRepository.save(request.getSponsor());
            contract.setSponsor(sponsor);
        } else {
            throw new IllegalArgumentException("Phải cung cấp ID nhà tài trợ hoặc thông tin nhà tài trợ mới");
        }
        
        // Set các thông tin cơ bản
        contract.setType(request.getType());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setTerms(request.getTerms());
        
        // Xử lý các hạng mục tài trợ
        List<ContractFundingItem> items = request.getItems().stream()
                .map(item -> {
                    // Tìm funding item
                    FundingItem fundingItem = fundingItemRepository.findById(item.getFundingItem().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Hạng mục tài trợ không tồn tại"));
                    
                    // Tạo contract funding item
                    ContractFundingItem contractItem = new ContractFundingItem();
                    contractItem.setFundingItem(fundingItem);
                    contractItem.setValue(item.getValue());
                    contractItem.setContract(contract);
                    return contractItem;
                })
                .collect(Collectors.toList());
        contract.setContractFundingItems(items);
        
        // Validate và tính toán
        validateContract(contract);
        calculateTotalAmount(contract);
        
        // Set trạng thái và thời gian
        contract.setStatus(ContractStatus.SIGNED);
        contract.setCreatedAt(LocalDateTime.now());
        
        // Lưu vào database
        return contractRepository.save(contract);
    }

    private void validateContract(Contract contract) {
        if (!contract.getSponsor().isActive()) {
            throw new IllegalArgumentException("Nhà tài trợ không ở trạng thái ACTIVE");
        }

        if (contract.getType() == null) {
            throw new IllegalArgumentException("Loại hợp đồng không được rỗng");
        }

        if (contract.getStartDate() == null || contract.getEndDate() == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và kết thúc không được rỗng");
        }
        if (contract.getStartDate().isAfter(contract.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu phải trước ngày kết thúc");
        }

        if (contract.getTerms() == null || contract.getTerms().trim().isEmpty()) {
            throw new IllegalArgumentException("Điều khoản không được rỗng");
        }

        List<ContractFundingItem> items = contract.getContractFundingItems();
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Danh sách hạng mục tài trợ không được rỗng");
        }

        for (ContractFundingItem item : items) {
            if (item.getValue() == null || item.getValue().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Giá trị hạng mục tài trợ phải lớn hơn 0");
            }
        }
    }

    private void calculateTotalAmount(Contract contract) {
        BigDecimal totalAmount = contract.getContractFundingItems().stream()
                .map(ContractFundingItem::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        contract.setAmount(totalAmount);
    }
}
