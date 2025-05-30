package com.princez1.SignContract.service;

import com.princez1.SignContract.entity.ContractEntity;
import java.util.List;

public interface ContractService {
    ContractEntity createContract(ContractEntity contractEntity);
    List<ContractEntity> getAllContracts();
    ContractEntity getContractById(Long id);
}
