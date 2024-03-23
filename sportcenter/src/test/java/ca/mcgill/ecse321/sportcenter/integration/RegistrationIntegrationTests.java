package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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

import ca.mcgill.ecse321.sportcenter.dto.AccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.CustomerResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationListDTO;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.CourseService;
import ca.mcgill.ecse321.sportcenter.service.LocationService;
import ca.mcgill.ecse321.sportcenter.service.RegistrationService;
import ca.mcgill.ecse321.sportcenter.service.SessionService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RegistrationIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    SessionService sessionService;

    @Autowired
    LocationService locationService;

    @Autowired
    CourseService courseService;

    @Autowired
    SportCenterRepository sportCenterRepository;

    @Autowired
    SportCenterManagementService sportCenterService;

    private Registration.Key validKey;
    private Integer validCustomerId;
    private Integer validSessionId;
    private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";


    @BeforeAll
    @AfterTestClass
    public void clearDatabase() {
        sportCenterRepository.deleteAll();
        registrationRepository.deleteAll();
        customerRepository.deleteAll();
        sessionRepository.deleteAll();
    }

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

    //--------------------------// General Empty Result Test //--------------------------//

    @Test
    @Order(1)
    public void testFindAllRegistrations() {
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("23:59:0");
        sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");

        Customer customer = accountService.createCustomerAccount("tayba.jusab@mail.mcgill.ca", "password", "Tayba", "rat.png");
        Instructor instructor = accountService.createInstructorAccount("instructor@mail.com", "instructor", "Jim", "gym.png");
        Course course = courseService.createCourse("Goat Yoga", "yoga with goats", Difficulty.Advanced, Status.Approved);
        Location location = locationService.createLocation("5", "502");
        Session session = sessionService.proposeSuperviseSession(openingTime, closingTime, Date.valueOf("2024-02-18"), 50, instructor.getId(), course.getId(), location.getId());

        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<RegistrationListDTO> response = client.exchange("/registrations", HttpMethod.GET, requestEntity, RegistrationListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty

        validCustomerId = customer.getId();
        validSessionId = session.getId();
    }

    //--------------------------// Create Registration Test //--------------------------//

    @Test
    @Order(2)
    public void testCreateValidRegistration() {
        Customer customer = accountService.findCustomerById(validCustomerId);
        Session session = sessionService.findSessionById(validSessionId);
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        Integer customerId = customer.getId();
        Integer sessionId = session.getId();
        
        // Act
        ResponseEntity<RegistrationResponseDTO> response = client.exchange("/registrations?customerId=" + customerId + "&sessionId=" + sessionId, 
            HttpMethod.POST, requestEntity, RegistrationResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        RegistrationResponseDTO createdRegistration = response.getBody();
        assertNotNull(createdRegistration);
        assertEquals(customer, createdRegistration.getAccount());
        assertEquals(session, createdRegistration.getSession());
        
        validKey = registrationService.findRegistration(customerId, sessionId).getKey();
    }

    //--------------------------// Read Registration Tests //--------------------------//

    @Test
    @Order(3)
    public void testReadRegistrationByValidKey() {
        Customer customer = accountService.findCustomerById(validCustomerId);
        Session session = sessionService.findSessionById(validSessionId);

        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<RegistrationResponseDTO> response = client.exchange("/registrations/" + validKey, HttpMethod.GET, requestEntity, RegistrationResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        RegistrationResponseDTO registration = response.getBody();
        assertNotNull(registration);
        assertEquals(customer, registration.getAccount());
        assertEquals(session, registration.getSession());
    }



}
