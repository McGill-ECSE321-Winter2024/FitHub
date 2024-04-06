package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigInteger;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
import ca.mcgill.ecse321.sportcenter.model.Customer;

/**
 * This class provides test cases for the BillingAccountRepository class.
 * It verifies the functionalities related to creating and reading billing accounts.
 */
@SpringBootTest
public class BillingAccountRepositoryTests extends CommonTestSetup {

    @Autowired
    private BillingAccountRepository billingRepo;

    @Autowired
    private CustomerRepository accountRepo;

    /**
     * Test case to verify the creation and reading of a billing account.
     */
    @Test
    public void testCreateAndReadBillingAccount(){
        // Create the customer 
        String email = "Jumijabasali@fithub.com";
        String password = "sportcenter";
        String name = "Jumijabasali";
        String imageURL = "pfp.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(sportCenter);
        
        // Save into database
        customer = accountRepo.save(customer);

        // Then create the billing account
        String aCardNumber = "1234567891234567";
        String aCardHolder = "Bobby Bob";
        String aBillingAdress = "2444 Sherbrooke O. Bd, Montreal";
        int aCCV = 374;
        LocalDate expDate = LocalDate.parse("2028-10-30");
        boolean isDefault = false;

        BillingAccount billingAccount = new BillingAccount();
        billingAccount.setCardNumber(aCardNumber);
        billingAccount.setCardHolder(aCardHolder);
        billingAccount.setBillingAddress(aBillingAdress);
        billingAccount.setCvv(aCCV);
        billingAccount.setExpirationDate(expDate);
        billingAccount.setIsDefault(isDefault);
        billingAccount.setCustomer(customer);

        billingAccount = billingRepo.save(billingAccount);
       
        int billingAccountId = billingAccount.getId();

        BillingAccount billingAccountFromDb = billingRepo.findBillingAccountById(billingAccountId);

        // Testing
        assertNotNull(billingAccountFromDb);
        assertEquals(aCardNumber, billingAccountFromDb.getCardNumber());
        assertEquals(aCardHolder, billingAccountFromDb.getCardHolder());
        assertEquals(aBillingAdress, billingAccountFromDb.getBillingAddress());
        assertEquals(aCCV, billingAccountFromDb.getCvv());
        assertEquals(expDate.toString(), billingAccountFromDb.getExpirationDate().toString());
        assertEquals(isDefault, billingAccountFromDb.getIsDefault());
        assertNotNull(billingAccountFromDb.getId());
        assertNotNull(billingAccountFromDb.getCustomer().getId());
        assertEquals(billingAccount.getCustomer().getId(),billingAccountFromDb.getCustomer().getId());
        
    }

    @Test
    public void testCreateAndReadBillingAccountByCustomer(){
        // Create the customer 
        String email = "Jumijabasali@fithub.com";
        String password = "sportcenter";
        String name = "Jumijabasali";
        String imageURL = "pfp.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(sportCenter);
        
        // Save into database
        customer = accountRepo.save(customer);

         // Then create the billing account
         String aCardNumber = "1234567891234567";
         String aCardHolder = "Bobby Bob";
         String aBillingAdress = "2444 Sherbrooke O. Bd, Montreal";
         int aCCV = 374;
         LocalDate expDate = LocalDate.parse("2028-10-30");
         boolean isDefault = false;
 
         BillingAccount billingAccount = new BillingAccount();
         billingAccount.setCardNumber(aCardNumber);
         billingAccount.setCardHolder(aCardHolder);
         billingAccount.setBillingAddress(aBillingAdress);
         billingAccount.setCvv(aCCV);
         billingAccount.setExpirationDate(expDate);
         billingAccount.setIsDefault(isDefault);
         billingAccount.setCustomer(customer);
 
        billingAccount = billingRepo.save(billingAccount);

        // Retrieve session from the database
        BillingAccount billingAccountFromDb = billingRepo.findBillingAccountByCustomer(billingAccount.getCustomer()).get(0);
 
         // Testing
         assertNotNull(billingAccountFromDb);
         assertEquals(aCardNumber, billingAccountFromDb.getCardNumber());
         assertEquals(aCardHolder, billingAccountFromDb.getCardHolder());
         assertEquals(aBillingAdress, billingAccountFromDb.getBillingAddress());
         assertEquals(aCCV, billingAccountFromDb.getCvv());
         assertEquals(expDate.toString(), billingAccountFromDb.getExpirationDate().toString());
         assertEquals(isDefault, billingAccountFromDb.getIsDefault());
         assertNotNull(billingAccountFromDb.getId());
         assertNotNull(billingAccountFromDb.getCustomer().getId());
         assertEquals(billingAccount.getCustomer().getId(),billingAccountFromDb.getCustomer().getId());
         
    }
    
}