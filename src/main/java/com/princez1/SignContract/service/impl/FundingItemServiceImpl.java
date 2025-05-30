package com.princez1.SignContract.service.impl;

import com.princez1.SignContract.entity.FundingItemEntity;
import com.princez1.SignContract.repository.FundingItemRepository;
import com.princez1.SignContract.service.FundingItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundingItemServiceImpl implements FundingItemService {

    @Autowired
    private FundingItemRepository fundingItemRepository;

    @Override
    public List<FundingItemEntity> getAllFundingItems() {
        return fundingItemRepository.findAll();
    }
} 