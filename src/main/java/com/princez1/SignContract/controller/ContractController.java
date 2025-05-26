package com.princez1.SignContract.controller;

import com.princez1.SignContract.entity.Contract;
import com.princez1.SignContract.model.CreateContractRequest;
import com.princez1.SignContract.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping
    public ResponseEntity<Contract> createContract(@Valid @RequestBody CreateContractRequest request) {
        Contract contract = contractService.createContract(request);
        return ResponseEntity.ok(contract);
    }
} 