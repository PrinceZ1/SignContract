package com.princez1.SignContract.controller;

import com.princez1.SignContract.entity.FundingItemEntity;
import com.princez1.SignContract.service.FundingItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/funding-items")
public class FundingItemController {

    @Autowired
    private FundingItemService fundingItemService;

    @GetMapping
    public ResponseEntity<List<FundingItemEntity>> getFundingItems() {
        List<FundingItemEntity> items = fundingItemService.getAllFundingItems();
        return ResponseEntity.ok(items);
    }
} 