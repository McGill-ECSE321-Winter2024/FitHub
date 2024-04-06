package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
import java.sql.Time;

import org.junit.jupiter.api.BeforeAll;
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

import ca.mcgill.ecse321.sportcenter.dto.LoginRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationListDTO;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionListDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
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

    //---------------------Headers------------------------------

	private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";

    //--------------------- Customer ------------------------------
    private Customer customer1;
    private Customer customer2;
    private Session session1;
    private Session session2;
    private Instructor instructor1;
    private Instructor instructor2;
    private Course course1;
    private Course course2;
    private Location location;

    @BeforeAll
	public void intializeDatabase() {
		sportCenterRepository.deleteAll();
        registrationRepository.deleteAll();
        customerRepository.deleteAll();
        sessionRepository.deleteAll();

		Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("23:0:0");
		
        sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");
        customer1 = accountService.createCustomerAccount("tayba.jusab@mail.mcgill.ca", "password", "Tayba", "rat.png", "");
        customer2 = accountService.createCustomerAccount("personB@gmail.com", "notMyPassword", "Person B", "tree.png", "");
        instructor1 = accountService.createInstructorAccount("instructor@mail.com", "instructor", "Jim", "gym.png", "");
        instructor2 = accountService.createInstructorAccount("pam@mail.com", "pammylmaooda", "Pam", "office.png", "");
        course1 = courseService.createCourse("Goat Yoga", "yoga with goats", Difficulty.Advanced.toString(), Status.Approved.toString(), 1, "none","none");
        course2 = courseService.createCourse("Goat Yoga 2", "beginner yoga with goats", Difficulty.Beginner.toString(), Status.Approved.toString(), 1,  "none","none");
        location = locationService.createLocation("5", "502");
        session1 = sessionService.proposeSuperviseSession(openingTime, closingTime, LocalDate.parse("2024-02-18"), 50, instructor1.getId(), course1.getId(), location.getId());
        session2 = sessionService.proposeSuperviseSession(openingTime, closingTime, LocalDate.parse("2024-12-06"), 100, instructor1.getId(), course2.getId(), location.getId());
	}

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
        accountService.createCustomerAccount(LOGIN_EMAIL, LOGIN_PASSWORD, "Julia", "Doritos.png", "");
        // Login into that account
        LoginRequestDTO request = new LoginRequestDTO(LOGIN_EMAIL, LOGIN_PASSWORD);
        ResponseEntity<LoginResponseDTO> response = client.postForEntity("/login", request, LoginResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    //--------------------------// General Empty Result Test //--------------------------//

    @Test
    @Order(1)
    public void testFindAllRegistrationsEmptyResult() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<RegistrationListDTO> response = client.exchange("/registrations", HttpMethod.GET, requestEntity, RegistrationListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }

    @Test
	@Order(2)
	public void testFindSessionsByCustomerEmptyResult() {
		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		assertNotNull(customerRepository.findCustomerById(customer1.getId()));
		
		String url = "/customers/" + customer1.getId() + "/sessions";

		ResponseEntity<SessionListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
	}

    @Test
	@Order(3)
	public void testFindCustomersBySessionEmptyResult(){
		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		assertNotNull(sessionRepository.findById(session1.getId()));
		
		String url = "/sessions/" + session1.getId() + "/customers";

		ResponseEntity<AccountListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, AccountListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
	}

    //--------------------------// Create Registration Test //--------------------------//

    @Test
    @Order(4)
    public void testCreateValidRegistration() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/registrations?customerId=" + customer1.getId() + "&sessionId=" + session1.getId();
        
        // Act
        ResponseEntity<RegistrationResponseDTO> response = client.exchange(url, HttpMethod.POST, requestEntity, RegistrationResponseDTO.class);

        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        RegistrationResponseDTO createdRegistration = response.getBody();
        assertNotNull(createdRegistration);
        assertEquals(customer1.getId(), createdRegistration.getAccount().getId());
        assertEquals(session1.getId(), createdRegistration.getSession().getId());
    }

    @Test
    @Order(5)
    public void testCreateValidRegistration2() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Register customer2 to sessions 1 and 2 such that
        // Session 1: customer 1, and 2
        // Session 2: customer 2
        String url1 = "/registrations?customerId=" + customer2.getId() + "&sessionId=" + session1.getId();
        String url2 = "/registrations?customerId=" + customer2.getId() + "&sessionId=" + session2.getId();
        
        // Act
        ResponseEntity<RegistrationResponseDTO> response1 = client.exchange(url1, HttpMethod.POST, requestEntity, RegistrationResponseDTO.class);
        ResponseEntity<RegistrationResponseDTO> response2 = client.exchange(url2, HttpMethod.POST, requestEntity, RegistrationResponseDTO.class);

        // Asserts
        assertNotNull(response1);
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        RegistrationResponseDTO createdRegistration1 = response1.getBody();
        assertNotNull(createdRegistration1);
        assertEquals(customer2.getId(), createdRegistration1.getAccount().getId());
        assertEquals(session1.getId(), createdRegistration1.getSession().getId());

        assertNotNull(response1);
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());
        RegistrationResponseDTO createdRegistration2 = response2.getBody();
        assertNotNull(createdRegistration2);
        assertEquals(customer2.getId(), createdRegistration2.getAccount().getId());
        assertEquals(session2.getId(), createdRegistration2.getSession().getId());
    }

    //--------------------------// Read Registration Tests //--------------------------//

    @Test
    @Order(6)
    public void testReadRegistration() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/registrations/" + customer1.getId() + "/" + session1.getId();
        
        // Act
        ResponseEntity<RegistrationResponseDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, RegistrationResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        RegistrationResponseDTO registration = response.getBody();
        assertNotNull(registration);
    }

    @Test
    @Order(7)
    public void testReadAllRegistrations() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<RegistrationListDTO> response = client.exchange("/registrations/", HttpMethod.GET, requestEntity, RegistrationListDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        RegistrationListDTO registrationList = response.getBody();
        assertNotNull(registrationList);

        // Check total registrations size
        assertEquals(3, registrationList.getRegistrations().size());

        // Check if customer1 is registered to session1
        assertEquals(customer1.getId(), registrationList.getRegistrations().get(0).getAccount().getId());
        assertEquals(session1.getId(), registrationList.getRegistrations().get(0).getSession().getId());

        // Check if customer2 is registered to session1 and 2
        assertEquals(customer2.getId(), registrationList.getRegistrations().get(1).getAccount().getId());
        assertEquals(session1.getId(), registrationList.getRegistrations().get(1).getSession().getId());
        assertEquals(customer2.getId(), registrationList.getRegistrations().get(2).getAccount().getId());
        assertEquals(session2.getId(), registrationList.getRegistrations().get(2).getSession().getId());
    }

    @Test
    @Order(8)
    public void testReadCustomersBySpecificSession() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/sessions/" + session1.getId() + "/customers";

        ResponseEntity<AccountListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, AccountListDTO.class);

        // Check all customers registered to session 1
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AccountListDTO accountList = response.getBody();
        assertNotNull(accountList);

        assertEquals(2, accountList.getAccounts().size());
        assertEquals(customer1.getId(), accountList.getAccounts().get(0).getId());
        assertEquals(customer2.getId(), accountList.getAccounts().get(1).getId());
    }

    @Test
    @Order(9)
    public void testReadSessionsBySpecificCustomer() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/customers/" + customer2.getId() + "/sessions";

        ResponseEntity<SessionListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionListDTO.class);

        // Check all sessions that customer 2 registered to
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SessionListDTO sessionList = response.getBody();
        assertNotNull(sessionList);

        assertEquals(2, sessionList.getSessions().size());
        assertEquals(session1.getId(), sessionList.getSessions().get(0).getId());
        assertEquals(session2.getId(), sessionList.getSessions().get(1).getId());
    }

    //--------------------------// Update Registration Tests //--------------------------//

    @Test
    @Order(9)
    public void updateRegistration() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Update customer 2's name and email
        accountService.updateCustomerAccount(customer2.getId(), "newemail@gmail.com", customer2.getPassword(), "New Name", customer2.getImageURL(), "");

        // Update sessions 1's instructor
        sessionService.updateSessionSupervisor(session1.getId(), instructor2.getId());

        String url = "/registrations?customerId=" + customer2.getId() + "&sessionId=" + session1.getId();

        ResponseEntity<RegistrationResponseDTO> response = client.exchange(url, HttpMethod.PUT, requestEntity, RegistrationResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        RegistrationResponseDTO registration = response.getBody();
        assertNotNull(registration);

        assertEquals("New Name", registration.getAccount().getName());
        assertEquals("newemail@gmail.com", registration.getAccount().getEmail());
        assertEquals(instructor2.getId(), registration.getSession().getSupervisor().getId());
    }

    @Test
    @Order(10)
    public void cancelRegistration() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/registrations/" + customer1.getId() + "/" + session1.getId();

        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(11)
    public void cancelAlreadyCancelledRegistration() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/registrations/" + customer1.getId() + "/" + session1.getId();

        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
