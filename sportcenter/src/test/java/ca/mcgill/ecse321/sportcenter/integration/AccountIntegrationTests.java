package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import ca.mcgill.ecse321.sportcenter.dto.AccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.CustomerResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.InstructorResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.OwnerResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    
    @Autowired
    AccountService accountService;

    @Autowired
    SportCenterManagementService sportCenterService;
    @Autowired
    private SportCenterRepository sportCenterRepository;
    @Autowired
    private CustomerRepository customerRepository;
    
    private String VALID_EMAIL = "alice@mail.mcgill.ca";
    private String VALID_PASSWORD = "password123";
    private String VALID_NAME = "Alice";
    private String VALID_IMAGEURL = "pfp.com";
    private int validId;

    @Test
    @Order(1)
    public void login() {
        sportCenterRepository.deleteAll();
        customerRepository.deleteAll();
        // Save one account in the system
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");
        String email = "julia@mail.com";
        String password = "secret1456165";
        accountService.createCustomerAccount(email, password, "Julia", "Doritos.png");
        
        // Login into that account
        LoginRequestDTO request = new LoginRequestDTO(email, password);
        ResponseEntity<LoginResponseDTO> response = client.postForEntity("/login", request, LoginResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }
    
    @Test
    @Order(2)
    public void testCreateValidCustomer() {

        // Set up
        AccountRequestDTO request = new AccountRequestDTO(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_IMAGEURL);
        
        // Act
        ResponseEntity<CustomerResponseDTO> response = client.postForEntity("/customers", request, CustomerResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerResponseDTO createdCustomer = response.getBody();
        assertNotNull(createdCustomer);
        assertEquals(VALID_EMAIL, createdCustomer.getEmail());
        assertEquals(VALID_PASSWORD, createdCustomer.getPassword());
        assertEquals(VALID_NAME, createdCustomer.getName());
        assertEquals(VALID_IMAGEURL, createdCustomer.getImageURL());
        assertNotNull(createdCustomer.getId());
        assertTrue(createdCustomer.getId() > 0, "Response should have a positive ID.");

        this.validId = createdCustomer.getId();
    }
    /*
    @Test
    @Order(2)
    public void testReadCustomerByValidId() {
        // Set up
        String url = "/customers/" + this.validId;

        // Act
        ResponseEntity<CustomerResponseDTO> response = client.getForEntity(url, CustomerResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomerResponseDTO customer = response.getBody();
        assertNotNull(customer);
        assertEquals(this.validId, customer.getId());
        assertEquals(VALID_EMAIL, customer.getEmail());
        assertEquals(VALID_PASSWORD, customer.getPassword());
        assertEquals(VALID_NAME, customer.getName());
        assertEquals(VALID_IMAGEURL, customer.getImageURL());
    }

    @Test
    @Order(3)
    public void testCreateCustomerWithInvalidPassword() {
        // Set up
        AccountRequestDTO request = new AccountRequestDTO(VALID_EMAIL, "a", VALID_NAME, VALID_IMAGEURL); // Not enough characters

        // Act
        ResponseEntity<String> response = client.postForEntity("/customers", request, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    @Order(4)
    public void testCreateValidInstructor() {
        // Set up
        String VALID_EMAIL = "JobTheInstructor@fithub.com";
        String VALID_PASSWORD = "password123";
        String VALID_NAME = "Joe";
        String VALID_IMAGEURL = "pfp.com";
        AccountRequestDTO request = new AccountRequestDTO(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_IMAGEURL);
        
        // Act
        ResponseEntity<InstructorResponseDTO> response = client.postForEntity("/instructors", request, InstructorResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        InstructorResponseDTO createdInstructor = response.getBody();
        assertNotNull(createdInstructor);
        assertEquals(VALID_EMAIL, createdInstructor.getEmail());
        assertEquals(VALID_PASSWORD, createdInstructor.getPassword());
        assertEquals(VALID_NAME, createdInstructor.getName());
        assertEquals(VALID_IMAGEURL, createdInstructor.getImageURL());
        assertNotNull(createdInstructor.getId());
        assertTrue(createdInstructor.getId() > 0, "Response should have a positive ID.");

        this.validId = createdInstructor.getId();
    }

    
    @Test
    @Order(3)
    public void testCreateValidOwner() {
        // Set up
        String VALID_EMAIL = "AnnaTheOwner@fithub.com";
        String VALID_PASSWORD = "password123";
        String VALID_NAME = "Anna";
        String VALID_IMAGEURL = "pfp.com";
        AccountRequestDTO request = new AccountRequestDTO(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_IMAGEURL);
        
        // Act
        ResponseEntity<OwnerResponseDTO> response = client.postForEntity("/owners", request, OwnerResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        OwnerResponseDTO createdOwner = response.getBody();
        assertNotNull(createdOwner);
        assertEquals(VALID_EMAIL, createdOwner.getEmail());
        assertEquals(VALID_PASSWORD, createdOwner.getPassword());
        assertEquals(VALID_NAME, createdOwner.getName());
        assertEquals(VALID_IMAGEURL, createdOwner.getImageURL());
        assertNotNull(createdOwner.getId());
        assertTrue(createdOwner.getId() > 0, "Response should have a positive ID.");

        this.validId = createdOwner.getId();
    }
     */
}
