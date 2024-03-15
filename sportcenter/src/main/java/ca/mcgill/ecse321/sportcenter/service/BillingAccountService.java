package ca.mcgill.ecse321.sportcenter.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.repository.BillingAccountRepository;

import jakarta.transaction.Transactional;

/*
* <p> Service class in charge of managing billing accounts. It implements following use cases: </p>
* <p> Create, update, delete a billing account </p>
* @author Anjali
*/
@Service
public class BillingAccountService {
    
    @Autowired
    private BillingAccountRepository billingAccountRepo; 

    //--------------------------// Create Billing Account //--------------------------//

    @Transactional
    public BillingAccount createBillingAccount(Integer cardNumber, String cardHolder, String billingAddress, Integer cvv, boolean isDefault, Date expirationDate, Customer customer){
        
        //Input validation check
        validBillingAccountInfo(cardNumber, cardHolder, billingAddress, cvv, expirationDate, customer);

        BillingAccount billingAccount = new BillingAccount();
        billingAccount.setBillingAddress(billingAddress);
        billingAccount.setCardNumber(cardNumber);
        billingAccount.setCardHolder(cardHolder);
        billingAccount.setCvv(cvv);
        billingAccount.setExpirationDate(expirationDate);
        billingAccount.setIsDefault(isDefault);
        billingAccount.setCustomer(customer);

        return billingAccountRepo.save(billingAccount);

    }

    //--------------------------// Update Account //--------------------------//

    @Transactional
    public BillingAccount updateBillingAccount(Integer id, Integer cardNumber, String cardHolder, String billingAddress, Integer cvv, boolean isDefault, Date expirationDate, Customer customer){
        
        validBillingAccountInfo(cardNumber, cardHolder, billingAddress, cvv, expirationDate, customer);

        BillingAccount account = findBillingAccountById(id);

        account.setCardNumber(cardNumber);
        account.setCardHolder(cardHolder);
        account.setBillingAddress(billingAddress);
        account.setCvv(cvv);
        account.setExpirationDate(expirationDate);
        account.setCustomer(customer);
        account.setIsDefault(isDefault);

        return billingAccountRepo.save(account);

    }

    //--------------------------// Delete Account //--------------------------//

    @Transactional
    public void deleteBillingAccount(Integer id){
        billingAccountRepo.delete(findBillingAccountById(id));
    }

    //--------------------------// Getters //--------------------------//

    @Transactional
    public BillingAccount findBillingAccountById(Integer id){
        BillingAccount account = billingAccountRepo.findBillingAccountById(id);
        if (account == null){
            throw new IllegalArgumentException("There is no billing account with ID " + id + ".");
        }
        return account;
    }

    @Transactional
    public List<BillingAccount> findBillingAccountByCustomer(Customer customer){
        List<BillingAccount> account = billingAccountRepo.findBillingAccountByCustomer(customer);
        if (account == null){
            throw new IllegalArgumentException("There is no billing account with for this customer.");
        }
        return account;
    }

    @Transactional
    public Iterable<BillingAccount> findAllBillingAccounts() {
        return billingAccountRepo.findAll();
    }

    //--------------------------// Input validations //--------------------------//

    private void validBillingAccountInfo(Integer cardNumber, String cardHolder, String billingAddress, Integer cvv, Date expirationDate, Customer customer){
        if (cardNumber == null || cardHolder.isEmpty() || cardHolder.trim().length() == 0 || billingAddress.isEmpty() || billingAddress.trim().length() == 0 || cvv == null || expirationDate == null){
            throw new IllegalArgumentException("Empty fields for cardNumber, cardHolder, billingAddress, cvv or expirationDate are not valid");
        }
        if (Integer.toString(cardNumber).length() != 16){
            throw new IllegalArgumentException("Invalid cardNumber; needs to be exactly 16 digits");
        }
        if (Integer.toString(cvv).length() != 3){
            throw new IllegalArgumentException("Invalid cvv; needs to be exactly 3 digits");
        }
        if (expirationDate.before(java.sql.Date.valueOf(LocalDate.now()))){
            throw new IllegalArgumentException("Invalid expirationDate");
        }
        if (customer == null){
            throw new IllegalArgumentException("Customer account does not exist");
        }

    }
}
