package ca.mcgill.ecse321.sportcenter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
import ca.mcgill.ecse321.sportcenter.model.Customer;

public interface BillingAccountRepository extends CrudRepository<BillingAccount, Integer> {
    BillingAccount findBillingAccountById(int id);
    List<BillingAccount>  findBillingAccountByCustomer(Customer customer);
}

