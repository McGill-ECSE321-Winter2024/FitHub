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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.AfterTestClass;

import ca.mcgill.ecse321.sportcenter.dto.LocationListDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.BillingAccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.BillingAccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.CustomerResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.InstructorResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.OwnerResponseDTO;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.LocationService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class LocationIntegrationTests extends CommonTestSetup {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SportCenterManagementService sportCenterService;

    private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";

    private String VALID_FLOOR = "3";
    private String VALID_ROOM = "302";

    private String NEW_FLOOR = "6";
    private String NEW_ROOM = "601";

    private Integer validId = 0;

    
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

    @Test
    @Order(1)
    public void testFindAllLocationsEmptyResult() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<LocationListDTO> response = client.exchange("/locations", HttpMethod.GET, requestEntity, LocationListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }
    
    //--------------------------// Create Tests //--------------------------//
    
    @Test
    @Order(2)
    public void testCreateValidLocation() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/locations?floor=" + VALID_FLOOR + "&room=" + VALID_ROOM;
        
        // Act
        ResponseEntity<LocationResponseDTO> response = client.exchange(url, HttpMethod.POST, requestEntity, LocationResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        LocationResponseDTO createdLocation = response.getBody();
        assertNotNull(createdLocation);
        assertEquals(VALID_FLOOR, createdLocation.getFloor());
        assertEquals(VALID_ROOM, createdLocation.getRoom());

        this.validId = createdLocation.getId(); // Keep the id for future test
    }

    //--------------------------// Read Tests //--------------------------//
    
    @Test
    @Order(3)
    public void testReadLocationByValidId() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<LocationResponseDTO> response = client.exchange("/locations/" + this.validId, HttpMethod.GET, requestEntity, LocationResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        LocationResponseDTO location = response.getBody();
        assertNotNull(location);
        assertEquals(this.validId, location.getId());
        assertEquals(VALID_FLOOR, location.getFloor());
        assertEquals(VALID_ROOM, location.getRoom());
    }

     //--------------------------// Delete Tests //--------------------------//

    @Test
    @Order(5)
    public void testDeleteValidLocation() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<LocationResponseDTO> response = client.exchange("/locations/" + this.validId, HttpMethod.DELETE, requestEntity, LocationResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
