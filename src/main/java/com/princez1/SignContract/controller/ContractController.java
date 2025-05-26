package com.princez1.SignContract.controller;

import com.princez1.SignContract.model.ContractModel;
import com.princez1.SignContract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping
    public ResponseEntity<ContractModel> createContract(@RequestBody ContractModel contract) {
        ContractModel createdContract = contractService.createContract(contract);
        return ResponseEntity.ok(createdContract);
    }

    @GetMapping
    public ResponseEntity<List<ContractModel>> getAllContracts() {
        List<ContractModel> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractModel> getContractById(@PathVariable Long id) {
        ContractModel contract = contractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }
} 