package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Time;

import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.AfterTestClass;

import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.CustomerResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.InstructorResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.OwnerResponseDTO;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountIntegrationTests extends CommonTestSetup {
    @Autowired
    private TestRestTemplate client;
    
    @Autowired
    AccountService accountService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SportCenterManagementService sportCenterService;
    @Autowired
    private SportCenterRepository sportCenterRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private OwnerRepository ownerRepository;


    private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";

    
    private String valid_email = "alice@mail.mcgill.ca";
    private String valid_password = "password123";
    private String valid_name = "Alice";
    private String valid_imageURL = "pfp.com";
    private int validId = 0;
    private String valid_newEmail = "new@mail.com";
    private String valid_newPassword = "newPassword";
    private String valid_newName = "Lory";
    private String valid_newImageURL = "pfp.jpeg";
    
    //--------------------------// Login Test //--------------------------//

    @Test
    @Order(0)
    public void login() {
        // Save one account in the system
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("23:59:0");
        sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");
        
        accountService.createCustomerAccount(LOGIN_EMAIL, LOGIN_PASSWORD, "Julia", "Doritos.png");
        
        // Login into that account
        LoginRequestDTO request = new LoginRequestDTO(LOGIN_EMAIL, LOGIN_PASSWORD);
        ResponseEntity<LoginResponseDTO> response = client.postForEntity("/login", request, LoginResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    //--------------------------// General Empty Result Tests //--------------------------//

     // Test to cover branch when no customer are in the system yet (before creating the initial customer to log in)

    @Test
    @Order(1)
    public void testFindAllIntructorAccountsEmptyResult() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/instructors", HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }
    
    @Test
    @Order(1)
    public void testFindAllOwnerAccountsEmptyResult() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/owners", HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }
    
    //--------------------------// Customer Tests //--------------------------//
    
    @Test
    @Order(2)
    public void testCreateValidCustomer() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<AccountRequestDTO> requestEntity = new HttpEntity<>(new AccountRequestDTO(valid_email, valid_password, valid_name, valid_imageURL), headers);
        
        // Act
        ResponseEntity<CustomerResponseDTO> response = client.exchange("/customers", HttpMethod.POST, requestEntity, CustomerResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CustomerResponseDTO createdCustomer = response.getBody();
        assertNotNull(createdCustomer);
        assertEquals(valid_email, createdCustomer.getEmail());
        assertTrue(passwordEncoder.matches(valid_password, createdCustomer.getPassword())); // Check if the encoded password matches
        assertEquals(valid_name, createdCustomer.getName());
        assertEquals(valid_imageURL, createdCustomer.getImageURL());
        assertNotNull(createdCustomer.getId());
        assertTrue(createdCustomer.getId() > 0, "Response should have a positive ID.");

        validId = createdCustomer.getId(); // Keep the id for future test
    }
    
    @Test
    @Order(3)
    public void testReadCustomerByValidId() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<CustomerResponseDTO> response = client.exchange("/customers/" + validId, HttpMethod.GET, requestEntity, CustomerResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        CustomerResponseDTO customer = response.getBody();
        assertNotNull(customer);
        assertEquals(this.validId, customer.getId());
        assertEquals(valid_email, customer.getEmail());
        assertTrue(passwordEncoder.matches(valid_password, customer.getPassword()));
        assertEquals(valid_name, customer.getName());
        assertEquals(valid_imageURL, customer.getImageURL());
    }

    @Test
    @Order(4)
    public void testUpdateValidCustomerAccount() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<AccountRequestDTO> requestEntity = new HttpEntity<>(new AccountRequestDTO(valid_newEmail, valid_newPassword, valid_newName, valid_newImageURL), headers);
        
        // Act
        ResponseEntity<CustomerResponseDTO> response = client.exchange("/customers/" + validId, HttpMethod.PUT, requestEntity, CustomerResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        CustomerResponseDTO updatedCustomer = response.getBody();
        assertNotNull(updatedCustomer);
        assertEquals(valid_newEmail, updatedCustomer.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, updatedCustomer.getPassword())); // Check if the encoded password matches
        assertEquals(valid_newName, updatedCustomer.getName());
        assertEquals(valid_newImageURL, updatedCustomer.getImageURL());
        assertNotNull(updatedCustomer.getId());
        assertTrue(updatedCustomer.getId() > 0, "Response should have a positive ID.");
    }

    @Test
    @Order(5)
    public void testFindAllCustomerAccounts() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/customers", HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountListDTO accountListDTO = response.getBody();
        assertNotNull(accountListDTO);
        // Currently there's the customer account I used to login and the one created and updated in test #1
        assertEquals(2, accountListDTO.getAccounts().size()); 
        // The login customer account was created before the customer account created in test #1 so it should be validId-1
        assertEquals(this.validId-1, accountListDTO.getAccounts().get(0).getId());

        // Get the customer created in test #1 and assert
        AccountResponseDTO customer = accountListDTO.getAccounts().get(1); 
        assertEquals(this.validId, customer.getId());
        assertEquals(valid_newEmail, customer.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, customer.getPassword()));
        assertEquals(valid_newName, customer.getName());
        assertEquals(valid_newImageURL, customer.getImageURL());
    }

    @Test
    @Order(6)
    public void testFindCustomerWithEmail() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/customers?email=" + valid_newEmail, HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountListDTO accountListDTO = response.getBody();
        assertNotNull(accountListDTO);
        // Suppose to only contain the customer created and updated in test #1 and #2
        assertEquals(1, accountListDTO.getAccounts().size()); 
        // The login customer account was created before the customer account created in test #1 so it should be validId-1

        // Get the customer created in test #1 and assert
        AccountResponseDTO customer = accountListDTO.getAccounts().get(0); 
        assertEquals(this.validId, customer.getId());
        assertEquals(valid_newEmail, customer.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, customer.getPassword()));
        assertEquals(valid_newName, customer.getName());
        assertEquals(valid_newImageURL, customer.getImageURL());
    }

    @Test
    @Order(7)
    public void testDeleteValidCustomer() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<String> response = client.exchange("/customers/" + validId, HttpMethod.DELETE, requestEntity, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    //--------------------------// Instructor Tests //--------------------------//
    
    @Test
    @Order(8)
    public void testCreateValidInstructor() {
        // Setting up valid variables for upcoming Instructor Tests (cannot reuse the same email)
        valid_email = "instructor@mail.mcgill.ca";
        valid_password = "instructorPassword";
        valid_name = "Loryane";
        valid_imageURL = "instructorFace.com";
        valid_newEmail = "instructor@other.email.com";
        valid_newPassword = "newSecretInstructorPassword";
        valid_newName = "NewLoryane";
        valid_newImageURL = "instructorNose.com";

        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<AccountRequestDTO> requestEntity = new HttpEntity<>(new AccountRequestDTO(valid_email, valid_password, valid_name, valid_imageURL), headers);
        
        // Act
        ResponseEntity<InstructorResponseDTO> response = client.exchange("/instructors", HttpMethod.POST, requestEntity, InstructorResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        InstructorResponseDTO createdInstructor = response.getBody();
        assertNotNull(createdInstructor);
        assertEquals(valid_email, createdInstructor.getEmail());
        assertTrue(passwordEncoder.matches(valid_password, createdInstructor.getPassword())); // Check if the encoded password matches
        assertEquals(valid_name, createdInstructor.getName());
        assertEquals(valid_imageURL, createdInstructor.getImageURL());
        assertNotNull(createdInstructor.getId());
        assertTrue(createdInstructor.getId() > 0, "Response should have a positive ID.");

        validId = createdInstructor.getId(); // Keep the id for future test
    }
    
    @Test
    @Order(9)
    public void testReadInstructorByValidId() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<InstructorResponseDTO> response = client.exchange("/instructors/" + validId, HttpMethod.GET, requestEntity, InstructorResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        InstructorResponseDTO instructor = response.getBody();
        assertNotNull(instructor);
        assertEquals(this.validId, instructor.getId());
        assertEquals(valid_email, instructor.getEmail());
        assertTrue(passwordEncoder.matches(valid_password, instructor.getPassword()));
        assertEquals(valid_name, instructor.getName());
        assertEquals(valid_imageURL, instructor.getImageURL());
    }

    @Test
    @Order(10)
    public void testUpdateValidInstructorAccount() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<AccountRequestDTO> requestEntity = new HttpEntity<>(new AccountRequestDTO(valid_newEmail, valid_newPassword, valid_newName, valid_newImageURL), headers);
        
        // Act
        ResponseEntity<InstructorResponseDTO> response = client.exchange("/instructors/" + validId, HttpMethod.PUT, requestEntity, InstructorResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        InstructorResponseDTO updatedInstructor = response.getBody();
        assertNotNull(updatedInstructor);
        assertEquals(valid_newEmail, updatedInstructor.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, updatedInstructor.getPassword())); // Check if the encoded password matches
        assertEquals(valid_newName, updatedInstructor.getName());
        assertEquals(valid_newImageURL, updatedInstructor.getImageURL());
        assertNotNull(updatedInstructor.getId());
        assertTrue(updatedInstructor.getId() > 0, "Response should have a positive ID.");
    }

    @Test
    @Order(11)
    public void testFindAllInstructorAccounts() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/instructors", HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountListDTO accountListDTO = response.getBody();
        assertNotNull(accountListDTO);
        // Currently there's only one instructor account
        assertEquals(1, accountListDTO.getAccounts().size()); 
        // Get the instructor created and assert
        AccountResponseDTO instructor = accountListDTO.getAccounts().get(0); 
        assertEquals(this.validId, instructor.getId());
        assertEquals(valid_newEmail, instructor.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, instructor.getPassword()));
        assertEquals(valid_newName, instructor.getName());
        assertEquals(valid_newImageURL, instructor.getImageURL());
    }

    @Test
    @Order(12)
    public void testFindInstructorWithEmail() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/instructors?email=" + valid_newEmail, HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountListDTO accountListDTO = response.getBody();
        assertNotNull(accountListDTO);
        // Suppose to only contain the instructor created and updated in test #1 and #2
        assertEquals(1, accountListDTO.getAccounts().size()); 
        // The login instructor account was created before the instructor account created in test #1 so it should be validId-1

        // Get the instructor created in test #1 and assert
        AccountResponseDTO instructor = accountListDTO.getAccounts().get(0); 
        assertEquals(this.validId, instructor.getId());
        assertEquals(valid_newEmail, instructor.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, instructor.getPassword()));
        assertEquals(valid_newName, instructor.getName());
        assertEquals(valid_newImageURL, instructor.getImageURL());
    }

    @Test
    @Order(13)
    public void testDeleteValidInstructor() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<String> response = client.exchange("/instructors/" + validId, HttpMethod.DELETE, requestEntity, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    
    //--------------------------// Owner Tests //--------------------------//
    
    @Test
    @Order(14)
    public void testCreateValidOwner() {
        // Setting up valid variables for upcoming Owner Tests (cannot reuse the same email)
        valid_email = "owner@mail.mcgill.ca";
        valid_password = "ownerPassword";
        valid_name = "Peppa Cochon";
        valid_imageURL = "ownerFace.com";
        valid_newEmail = "owner@other.email.com";
        valid_newPassword = "newSecretOwnerPassword";
        valid_newName = "Mommy Pig";
        valid_newImageURL = "ownerNose.com";

        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<AccountRequestDTO> requestEntity = new HttpEntity<>(new AccountRequestDTO(valid_email, valid_password, valid_name, valid_imageURL), headers);
        
        // Act
        ResponseEntity<OwnerResponseDTO> response = client.exchange("/owners", HttpMethod.POST, requestEntity, OwnerResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        OwnerResponseDTO createdOwner = response.getBody();
        assertNotNull(createdOwner);
        assertEquals(valid_email, createdOwner.getEmail());
        assertTrue(passwordEncoder.matches(valid_password, createdOwner.getPassword())); // Check if the encoded password matches
        assertEquals(valid_name, createdOwner.getName());
        assertEquals(valid_imageURL, createdOwner.getImageURL());
        assertNotNull(createdOwner.getId());
        assertTrue(createdOwner.getId() > 0, "Response should have a positive ID.");

        validId = createdOwner.getId(); // Keep the id for future test
    }
    
    @Test
    @Order(15)
    public void testReadOwnerByValidId() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<OwnerResponseDTO> response = client.exchange("/owners/" + validId, HttpMethod.GET, requestEntity, OwnerResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        OwnerResponseDTO owner = response.getBody();
        assertNotNull(owner);
        assertEquals(this.validId, owner.getId());
        assertEquals(valid_email, owner.getEmail());
        assertTrue(passwordEncoder.matches(valid_password, owner.getPassword()));
        assertEquals(valid_name, owner.getName());
        assertEquals(valid_imageURL, owner.getImageURL());
    }

    @Test
    @Order(16)
    public void testUpdateValidOwnerAccount() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<AccountRequestDTO> requestEntity = new HttpEntity<>(new AccountRequestDTO(valid_newEmail, valid_newPassword, valid_newName, valid_newImageURL), headers);
        
        // Act
        ResponseEntity<OwnerResponseDTO> response = client.exchange("/owners/" + validId, HttpMethod.PUT, requestEntity, OwnerResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        OwnerResponseDTO updatedOwner = response.getBody();
        assertNotNull(updatedOwner);
        assertEquals(valid_newEmail, updatedOwner.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, updatedOwner.getPassword())); // Check if the encoded password matches
        assertEquals(valid_newName, updatedOwner.getName());
        assertEquals(valid_newImageURL, updatedOwner.getImageURL());
        assertNotNull(updatedOwner.getId());
        assertTrue(updatedOwner.getId() > 0, "Response should have a positive ID.");
    }

    @Test
    @Order(17)
    public void testFindAllOwnerAccounts() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/owners", HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountListDTO accountListDTO = response.getBody();
        assertNotNull(accountListDTO);
        // Currently there's only one owner account
        assertEquals(1, accountListDTO.getAccounts().size()); 
        // Get the owner created and assert
        AccountResponseDTO owner = accountListDTO.getAccounts().get(0); 
        assertEquals(this.validId, owner.getId());
        assertEquals(valid_newEmail, owner.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, owner.getPassword()));
        assertEquals(valid_newName, owner.getName());
        assertEquals(valid_newImageURL, owner.getImageURL());
    }

    @Test
    @Order(18)
    public void testFindOwnerWithEmail() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/owners?email=" + valid_newEmail, HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountListDTO accountListDTO = response.getBody();
        assertNotNull(accountListDTO);
        // Suppose to only contain the owner created and updated in test #1 and #2
        assertEquals(1, accountListDTO.getAccounts().size()); 
        // The login owner account was created before the owner account created in test #1 so it should be validId-1

        // Get the owner created in test #1 and assert
        AccountResponseDTO owner = accountListDTO.getAccounts().get(0); 
        assertEquals(this.validId, owner.getId());
        assertEquals(valid_newEmail, owner.getEmail());
        assertTrue(passwordEncoder.matches(valid_newPassword, owner.getPassword()));
        assertEquals(valid_newName, owner.getName());
        assertEquals(valid_newImageURL, owner.getImageURL());
    }

    @Test
    @Order(19)
    public void testDeleteValidOwner() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<String> response = client.exchange("/owners/" + validId, HttpMethod.DELETE, requestEntity, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    //--------------------------// General Tests //--------------------------//
    @Test
    @Order(20)
    public void testFindAllAccounts() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/accounts", HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountListDTO accountListDTO = response.getBody();
        assertNotNull(accountListDTO);
        // Currently there are supposed to be 2 customer accounts, 1 instructor account and 1 owner account
        assertEquals(4, accountListDTO.getAccounts().size()); 
    }

    //--------------------------// Invalid Tests //--------------------------//
    @Test
    @Order(21)
    public void testCreateInvalidInstructor() { // Invalid password
        // Setting up valid variables for upcoming Instructor Tests (cannot reuse the same email)
        valid_email = "instructor@mail.mcgill.ca";
        String invalid_password = "0"; // Not long enough
        valid_name = "Loryane";
        valid_imageURL = "instructorFace.com";
        valid_newEmail = "instructor@other.email.com";
        valid_newPassword = "newSecretInstructorPassword";
        valid_newName = "NewLoryane";
        valid_newImageURL = "instructorNose.com";

        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<AccountRequestDTO> requestEntity = new HttpEntity<>(new AccountRequestDTO(valid_email, invalid_password, valid_name, valid_imageURL), headers);
        
        // Act
        ResponseEntity<InstructorResponseDTO> response = client.exchange("/instructors", HttpMethod.POST, requestEntity, InstructorResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("The password needs to have 8 characters or more", response.getBody().getError());
    }

    @Test
    @Order(22)
    public void testUpdateInvalidOwnerAccount() { // Invalid Email
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<AccountRequestDTO> requestEntity = new HttpEntity<>(new AccountRequestDTO("", "dfljadkjflaksdfj", "Madona", "Madona.face"), headers);
        
        // Act
        ResponseEntity<OwnerResponseDTO> response = client.exchange("/owners/" + validId, HttpMethod.PUT, requestEntity, OwnerResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Empty fields for email, password or name are not valid", response.getBody().getError());
    }

    
    @Test
    @Order(23)
    public void testDeleteInvalidCustomer() { // Fake id
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        int id = 66666;
        // Act
        ResponseEntity<String> response = client.exchange("/customers/" + id, HttpMethod.DELETE, requestEntity, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("There is no customer with ID " + id + ".", response.getBody());
    }

    @Test
    @Order(23)
    public void testFindAllInstructorAccountsWithInvalidEmailArgument() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        String email = "fkjakdjflajf";
        // Act
        ResponseEntity<AccountListDTO> response = client.exchange("/instructors?email=" + email, HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("There is no instructor with email " + email + ".", response.getBody().getError());
    }
}
