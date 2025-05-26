package com.princez1.SignContract.service;

import com.princez1.SignContract.model.ContractModel;
import java.util.List;

public interface ContractService {
    ContractModel createContract(ContractModel contract);
    List<ContractModel> getAllContracts();
    ContractModel getContractById(Long id);
}
