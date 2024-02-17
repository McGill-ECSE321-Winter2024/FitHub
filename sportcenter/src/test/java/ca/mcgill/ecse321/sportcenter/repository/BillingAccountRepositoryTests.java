package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

@SpringBootTest
public class BillingAccountRepositoryTests {

    @Autowired
    private BillingAccountRepository billingRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase(){
        billingRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadBillingAccount(){

        String aEmail = "bobby@gmail.com";
        String aPassword = "password";
        String aName = "Bobby Bob";
        String aImageURL = "https://upload.wikimedia.org/wikipedia/en/thumb/c/c5/Bob_the_builder.jpg/220px-Bob_the_builder.jpg";
        Integer id = 0;
        
        SportCenter aCenter = new SportCenter(id, "FitHub", new Time(6), new Time(22), "2011 University Street, Montreal", "fithub@gmail.com", "514-873-2648");
        Customer bob = new Customer(aEmail, aPassword, aName, aImageURL, aCenter);

        bob = customerRepo.save(bob);


        int aCardNumber = 0;
        String aCardHolder = "Bobby Bob";
        String aBillingAdress = "2444 Sherbrooke O. Bd, Montreal";
        int aCCV = 374;
        Date expDate = new Date(4);
        boolean isDefault = false;
        BillingAccount billingAccount = new BillingAccount(aCardNumber, aCardHolder , aBillingAdress, aCCV, expDate, isDefault, 0, bob);

        billingAccount = billingRepo.save(billingAccount);
        int billingAccountId = billingAccount.getId();

        BillingAccount billingAccountFromDb = billingRepo.findBillingAccountById(billingAccountId);

        //Assertions
        assertNotNull(billingAccountFromDb);
        assertEquals(aCardNumber, billingAccountFromDb.getCardNumber());
        assertEquals(aCardHolder, billingAccountFromDb.getCardHolder());
        assertEquals(aBillingAdress, billingAccountFromDb.getBillingAddress());
        assertEquals(aCCV, billingAccountFromDb.getCvv());
        assertEquals(expDate, billingAccountFromDb.getExpirationDate());
        assertEquals(isDefault, billingAccountFromDb.getIsDefault());
    }
    
}
