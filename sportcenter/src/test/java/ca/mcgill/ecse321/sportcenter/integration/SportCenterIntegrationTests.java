package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;

import ca.mcgill.ecse321.sportcenter.dto.SportCenterDTO;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseListDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginResponseDTO;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SportCenterIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    
    @Autowired
    AccountService accountService;
    @Autowired
    SportCenterManagementService sportCenterService;
    @Autowired
    private SportCenterRepository sportCenterRepository;

    private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";

    private String valid_name = "James";
    private Time valid_opening_time = Time.valueOf("06:00:00");
    private Time valid_closing_time = Time.valueOf("23:00:00");
    private String valid_address = "somewhere";
    private String valid_email = "james@mail.mcgill.ca";
    private String valid_phone_number = "1234567890";

    // Locations
    List<LocationDTO> valid_locations = new ArrayList<>();

    // Courses
    List<Course> centerCourses = new ArrayList<>();
    List<CourseResponseDTO> courseResponseList = CourseListDTO.courseListToCourseResponseDTOList(centerCourses);
    CourseListDTO valid_courses = new CourseListDTO(courseResponseList);

    // Accounts
    List<Account> centerAccounts = new ArrayList<>();
    List<AccountResponseDTO> accountResponseList = AccountListDTO.accountListToAccountResponseDTOList(centerAccounts);
    AccountListDTO valid_accounts = new AccountListDTO(accountResponseList);
    
    private Time new_valid_opening_time = Time.valueOf("10:00:00");
    private Time new_valid_closing_time = Time.valueOf("20:00:00");
    private String new_valid_address = "somewhere else";

    @BeforeAll
    @AfterTestClass
    public void clearDatabase() {
        sportCenterRepository.deleteAll();
    }

	//--------------------------// LOGIN //--------------------------//

	@Test
    @Order(0)
    public void loginAndPrepDatabase() {
		Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("23:0:0");
		
        sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");

        // Save one account in the system
        accountService.createCustomerAccount(LOGIN_EMAIL, LOGIN_PASSWORD, "Julia", "Doritos.png");
        
        // Login into that account
        LoginRequestDTO request = new LoginRequestDTO(LOGIN_EMAIL, LOGIN_PASSWORD);
        ResponseEntity<LoginResponseDTO> response = client.postForEntity("/login", request, LoginResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    //--------------------------// Create test //--------------------------//
    
    @Test
    @Order(1)
    public void testCreateInvalidSportCenter() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<SportCenterDTO> requestEntity = new HttpEntity<SportCenterDTO>(new SportCenterDTO(valid_name, valid_opening_time, valid_closing_time, valid_address, valid_email, valid_phone_number, valid_courses, valid_locations, valid_accounts), headers);
        
        // Act
        ResponseEntity<SportCenterDTO> response = client.exchange("/sport-center", HttpMethod.POST, requestEntity, SportCenterDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sport center already exists.", response.getBody().getError());
    }

    //--------------------------// Update test //--------------------------//

    @Test
    @Order(2)
    public void testUpdateValidSportCenter() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<SportCenterDTO> requestEntity = new HttpEntity<SportCenterDTO>(new SportCenterDTO(valid_name, new_valid_opening_time, new_valid_closing_time, new_valid_address, valid_email, valid_phone_number, valid_courses, valid_locations, valid_accounts), headers);
        
        // Act
        ResponseEntity<SportCenterDTO> response = client.exchange("/sport-center", HttpMethod.PUT, requestEntity, SportCenterDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        SportCenterDTO updatedSportCenter = response.getBody();
        assertNotNull(updatedSportCenter);
        assertEquals(new_valid_opening_time, updatedSportCenter.getOpeningTime());
        assertEquals(new_valid_closing_time, updatedSportCenter.getClosingTime());
        assertEquals(new_valid_address, updatedSportCenter.getAddress());
    }

    //--------------------------// Delete test //--------------------------//

    @Test
    @Order(3)
    public void testDeleteValidCustomer() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<SportCenterDTO> response = client.exchange("/sport-center", HttpMethod.DELETE, requestEntity, SportCenterDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    
    @Test
    @Order(4)
    public void testAuthenticationFailedWhenCreatingValidSportCenter() {
        // Set up authentication for this test, however if the sportcenter was well deleted, then there is no account to log into
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<SportCenterDTO> requestEntity = new HttpEntity<SportCenterDTO>(new SportCenterDTO(valid_name, valid_opening_time, valid_closing_time, valid_address, valid_email, valid_phone_number, valid_courses, valid_locations, valid_accounts), headers);
        
        // Act
        ResponseEntity<SportCenterDTO> response = client.exchange("/sport-center", HttpMethod.POST, requestEntity, SportCenterDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()); // Since no sport center in the system and no account
    }
}