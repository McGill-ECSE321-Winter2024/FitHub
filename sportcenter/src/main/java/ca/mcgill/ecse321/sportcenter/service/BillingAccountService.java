package ca.mcgill.ecse321.sportcenter.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.repository.BillingAccountRepository;
import jakarta.transaction.Transactional;

@Service
public class BillingAccountService {
    
    @Autowired
    private BillingAccountRepository billingAccountRepo; 

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

        return billingAccount;

    }

    private void validBillingAccountInfo(Integer cardNumber, String cardHolder, String billingAddress, Integer cvv, Date expirationDate, Customer customer){
        if (cardNumber == null || cardHolder.isEmpty() || billingAddress.isEmpty() || cvv == null || expirationDate == null){
            throw new IllegalArgumentException("Empty fields for cardNumber, cardHolder, billingAddress, cvv or name are not valid");
        }
        if (Integer.toString(cardNumber).length() != 16){
            throw new IllegalArgumentException("Invalid cardNumber; has to be exactly 16 digits");
        }
        if (Integer.toString(cvv).length() != 3){
            throw new IllegalArgumentException("Invalid cvv; has to be exactly 3 digits");
        }
        if (customer == null){
            throw new IllegalArgumentException("Customer account does not exist");
        }

    }
}
