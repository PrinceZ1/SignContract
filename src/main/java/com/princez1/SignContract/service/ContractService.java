package com.princez1.SignContract.service;

import com.princez1.SignContract.entity.Contract;
import java.util.List;

public interface ContractService {
    Contract createContract(Contract contract);
    List<Contract> getAllContracts();
    Contract getContractById(Long id);
}
