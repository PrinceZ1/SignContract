package com.princez1.SignContract.controller;

import com.princez1.SignContract.entity.ContractEntity;
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
    public ResponseEntity<ContractEntity> createContract(@RequestBody ContractEntity contractEntity) {
        ContractEntity createdContract = contractService.createContract(contractEntity);
        return ResponseEntity.ok(createdContract);
    }

    @GetMapping
    public ResponseEntity<List<ContractEntity>> getAllContracts() {
        List<ContractEntity> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractEntity> getContractById(@PathVariable Long id) {
        ContractEntity contract = contractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }
} 