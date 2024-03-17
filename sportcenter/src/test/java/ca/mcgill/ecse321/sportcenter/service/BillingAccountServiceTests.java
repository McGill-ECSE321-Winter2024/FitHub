package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
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
    
    private Customer customer;

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

    @BeforeEach
    public void createAndSaveCustomer() {
       //create a customer 
       customer = new Customer();
       customer.setEmail("bob@gmail.com");
       customer.setPassword("12345");
       customer.setName("Bob");
       customer.setImageURL("pfp123.com");
    }


    //--------------------------// Create Billing Account Tests //--------------------------//

    @Test
    public void testAndCreateValidBillingAccount(){
        // Set up test
        String cardHolder = "Mary Jane";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("1234567891234567");
        Integer cvv = 372;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2026-02-18");

        when(customerRepository.findCustomerById(0)).thenReturn(customer);

        BillingAccount account = new BillingAccount();
        account.setCardHolder(cardHolder);
        account.setBillingAddress(billingAddress);
        account.setCardNumber(cardNumber);
        account.setCvv(cvv);
        account.setIsDefault(isDefault);
        account.setExpirationDate(expirationDate);
        account.setCustomer(customer);

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


    /*
    @Test
    public void testAndCreateDuplicateBillingAccount(){
        String cardHolder = "Mary Jane";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("1234567891234567");
        Integer cvv = 372;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2026-02-18");

        when(customerRepository.findCustomerById(0)).thenReturn(customer);

        BillingAccount account = new BillingAccount();
        account.setCardHolder(cardHolder);
        account.setBillingAddress(billingAddress);
        account.setCardNumber(cardNumber);
        account.setCvv(cvv);
        account.setIsDefault(isDefault);
        account.setExpirationDate(expirationDate);
        account.setCustomer(customer);

        //when(billingAccountRepository.save(any(BillingAccount.class))).thenReturn(account);
        when(billingAccountRepository.save(any(BillingAccount.class))).thenReturn(account);

        // Act
       BillingAccount createdAccount = billingAccountService.createBillingAccount(cardNumber, cardHolder, billingAddress, cvv, isDefault, expirationDate, customer);
    
        //when(billingAccountRepository.findBillingAccountById(any())).thenReturn(account);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> billingAccountService.createBillingAccount(new BigInteger("1234567891234567"), "Bob Smith","1234, Sherbrooke Street, Montreal", 372, true,  Date.valueOf("2026-02-18"), customer));
        assertEquals("This card already exists", e.getMessage());
    }

    @Test
    public void testAndCreateBillingAccountWithInvalidCardNumber(){

        String cardHolder = "Bob";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("12345");
        Integer cvv = 123;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2026-07-23");

        when(customerRepository.findCustomerById(0)).thenReturn(customer);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> billingAccountService.createBillingAccount(cardNumber, cardHolder, billingAddress, cvv, isDefault, expirationDate, customer));
        assertEquals("Invalid cardNumber; needs to be exactly 16 digits", e.getMessage());

    }
    */

    @Test
    public void testAndCreateBillingAccountWithInvalidCvv(){

        String cardHolder = "Bob";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("1234567891234567");
        Integer cvv = 1;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2026-07-23");

        when(customerRepository.findCustomerById(0)).thenReturn(customer);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> billingAccountService.createBillingAccount(cardNumber, cardHolder, billingAddress, cvv, isDefault, expirationDate, customer));
        assertEquals("Invalid cvv; needs to be exactly 3 digits", e.getMessage());

    }

    @Test
    public void testAndCreateBillingAccountWithInvalidExpirationDate(){

        String cardHolder = "Bob";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("1234567891234567");
        Integer cvv = 123;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2020-07-23");

        when(customerRepository.findCustomerById(0)).thenReturn(customer);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> billingAccountService.createBillingAccount(cardNumber, cardHolder, billingAddress, cvv, isDefault, expirationDate, customer));
        assertEquals("Invalid expirationDate", e.getMessage());

    }

    @Test
    public void testAndCreateBillingAccountWithInvalidCustomer(){

        String cardHolder = "Bob";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("1234567891234567");
        Integer cvv = 123;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2027-07-23");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> billingAccountService.createBillingAccount(cardNumber, cardHolder, billingAddress, cvv, isDefault, expirationDate, null));
        assertEquals("Customer account does not exist", e.getMessage());

    }


    //--------------------------// Update Billing Account Tests //--------------------------//

    @Test
    public void testAndUpdateValidBillingAccount(){
        // Set up test
        int id = 55;
        String cardHolder = "Mary Jane";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("1234567891234567");
        Integer cvv = 372;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2026-02-18");

        when(customerRepository.findCustomerById(0)).thenReturn(customer);

        BillingAccount account = new BillingAccount();
        account.setCardHolder(cardHolder);
        account.setBillingAddress(billingAddress);
        account.setCardNumber(cardNumber);
        account.setCvv(cvv);
        account.setIsDefault(isDefault);
        account.setExpirationDate(expirationDate);
        account.setCustomer(customer);

        when(billingAccountRepository.findBillingAccountById(id)).thenReturn(account);

        String newCardHolder = "Bob Smith";
        String newBillingAddress = "9, Wellington Street, Montreal";
        BigInteger newCardNumber =  new BigInteger("2000007891234000");
        Integer newCvv = 407;
        boolean newIsDefault = false;
        Date newExpirationDate = Date.valueOf("2028-11-01");
        BillingAccount newAccount = new BillingAccount();
        newAccount.setCardHolder(newCardHolder);
        newAccount.setBillingAddress(newBillingAddress);
        newAccount.setCardNumber(newCardNumber);
        newAccount.setCvv(newCvv);
        newAccount.setIsDefault(newIsDefault);
        newAccount.setExpirationDate(newExpirationDate);
        newAccount.setCustomer(customer);

        when(billingAccountRepository.save(any(BillingAccount.class))).thenReturn(newAccount);

        // Act
        BillingAccount updatedAccount = billingAccountService.updateBillingAccount(id, newCardNumber, newCardHolder, newBillingAddress, newCvv, newIsDefault, newExpirationDate);
    
        // Assert
        verify(billingAccountRepository, times(1)).findBillingAccountById(id);
        verify(billingAccountRepository, times(1)).save(any(BillingAccount.class));

        assertNotNull(updatedAccount);
        assertEquals(newCardHolder.toLowerCase(), updatedAccount.getCardHolder().toLowerCase());
        assertEquals(newCardNumber,  updatedAccount.getCardNumber());
        assertEquals(newBillingAddress.toLowerCase(), updatedAccount.getBillingAddress().toLowerCase());
        assertEquals(newCvv, updatedAccount.getCvv());
        assertEquals(newIsDefault, updatedAccount.getIsDefault());
        assertEquals(newExpirationDate, updatedAccount.getExpirationDate());
        assertEquals(customer, updatedAccount.getCustomer());
    }


    //--------------------------// Find Billing Account Tests //--------------------------//

    @Test
    public void testReadBillingaccountByValidId(){
        // Set up test
        int id = 55;
        String cardHolder = "Mary Jane";
        String billingAddress = "1234, Sherbrooke Street, Montreal";
        BigInteger cardNumber =  new BigInteger("1234567891234567");
        Integer cvv = 372;
        boolean isDefault = true;
        Date expirationDate = Date.valueOf("2026-02-18");

        when(customerRepository.findCustomerById(0)).thenReturn(customer);

        BillingAccount account = new BillingAccount();
        account.setCardHolder(cardHolder);
        account.setBillingAddress(billingAddress);
        account.setCardNumber(cardNumber);
        account.setCvv(cvv);
        account.setIsDefault(isDefault);
        account.setExpirationDate(expirationDate);
        account.setCustomer(customer);

        when(billingAccountRepository.findBillingAccountById(id)).thenReturn(account);

        // Act
        BillingAccount foundBillingAccount = billingAccountService.findBillingAccountById(id);
    
        // Assert
        assertNotNull(foundBillingAccount);
        assertEquals(cardHolder.toLowerCase(), foundBillingAccount.getCardHolder().toLowerCase());
        assertEquals(cardNumber,  foundBillingAccount.getCardNumber());
        assertEquals(billingAddress.toLowerCase(), foundBillingAccount.getBillingAddress().toLowerCase());
        assertEquals(cvv, foundBillingAccount.getCvv());
        assertEquals(isDefault, foundBillingAccount.getIsDefault());
        assertEquals(expirationDate, foundBillingAccount.getExpirationDate());
        assertEquals(customer, foundBillingAccount.getCustomer());

    }

    @Test
    public void testReadBillingAccountByInvalidId(){
        // Set up test
        int id = 55;
        when(billingAccountRepository.findBillingAccountById(id)).thenReturn(null);
         // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> billingAccountService.findBillingAccountById(id));
        assertEquals("There is no billing account with ID " + id + ".", e.getMessage());
    }


    

    
}
