package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;

public interface BillingAccountRepository extends CrudRepository<BillingAccount, Integer> {
    BillingAccount findBillingAccountById(int id);
}

