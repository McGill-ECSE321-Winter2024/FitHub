package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.BillingAccountRepository;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;

@SpringBootTest
public class BillingAccountServiceTests {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BillingAccountRepository billingAccountRepository;
    @Mock
    private SportCenterRepository sportCenterRepository;

    @InjectMocks
    private BillingAccountService billingAccountService;
    
    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        billingAccountRepository.deleteAll();
        customerRepository.deleteAll();
        sportCenterRepository.deleteAll();
    }

    /**
     * Create and save a SportCenter instance before each test.
     */
    @BeforeEach
    public void createAndSaveSportCenter() {
        SportCenter sportCenter = new SportCenter();
        sportCenter.setName("FitHub");
        sportCenter.setOpeningTime(Time.valueOf("08:00:00"));
        sportCenter.setClosingTime(Time.valueOf("18:00:00"));
        sportCenter.setEmail("info@fithub.com");
        sportCenter.setPhoneNumber("421-436-4444");
        sportCenter.setAddress("2011, University Street, Montreal");

        //Save sportCenterRepo
        sportCenter = sportCenterRepository.save(sportCenter);
    }


    @BeforeEach
    public void createAndSaveCustomer() {
       //create a customer 
       Customer customer = new Customer();
       customer.setEmail("bob@gmail.com");
       customer.setPassword("12345");
       customer.setName("Bob");
       customer.setImageURL("pfp123.com");
       customer.setCenter(sportCenterRepository.findSportCenterById(0));
       // Save into database
       //customer = customerRepository.save(customer);
    }


    //--------------------------// Create Billing Account Tests //--------------------------//

    @Test
    public void testAndCreateValidBillingAccount(){
        //Set up test
        String cardHolder = "Mary Jane";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("1234567891234567");
        Integer cvv = 372;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2026-02-18");

        Customer customer = new Customer();
        when(customerRepository.findCustomerById(0)).thenReturn(customer);
        //Customer customer = customerRepository.findCustomerById(0);
        //if (customer==null){
        //    System.out.println("customer null");
        //}

        BillingAccount account = new BillingAccount();
        account.setCardHolder(cardHolder);
        account.setBillingAddress(billingAddress);
        account.setCardNumber(cardNumber);
        account.setCvv(cvv);
        account.setIsDefault(isDefault);
        account.setExpirationDate(expirationDate);
        //account.setCustomer(customer);

        when(billingAccountRepository.save(any(BillingAccount.class))).thenReturn(account);

        // Act
        BillingAccount createdAccount = billingAccountService.createBillingAccount(cardNumber, cardHolder, billingAddress, cvv, isDefault, expirationDate, customer);
    
        // Assert
        assertNotNull(createdAccount);
        assertEquals(cardHolder.toLowerCase(), createdAccount.getCardHolder().toLowerCase());
        assertEquals(cardNumber,  createdAccount.getCardNumber());
        assertEquals(billingAddress.toLowerCase(), createdAccount.getBillingAddress().toLowerCase());
        assertEquals(cvv, createdAccount.getCvv());
        assertEquals(isDefault, createdAccount.getIsDefault());
        assertEquals(expirationDate, createdAccount.getExpirationDate());
        assertEquals(customer, createdAccount.getCustomer());

        verify(billingAccountRepository, times(1)).save(any(BillingAccount.class));
    }

   // @Test
   // public void testAndCreateInvalidCardNumber(){

    //}

    

    
}
