package com.princez1.SignContract.service;

import com.princez1.SignContract.entity.Contract;
import com.princez1.SignContract.model.CreateContractRequest;

public interface ContractService {
    Contract createContract(CreateContractRequest request);
}
