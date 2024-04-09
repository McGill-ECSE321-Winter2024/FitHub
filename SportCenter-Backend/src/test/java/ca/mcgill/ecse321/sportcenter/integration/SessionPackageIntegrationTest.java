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
import ca.mcgill.ecse321.sportcenter.dto.LoginRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionListDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionPackageListDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionPackageRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionPackageResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionPackageRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.CourseService;
import ca.mcgill.ecse321.sportcenter.service.LocationService;
import ca.mcgill.ecse321.sportcenter.service.SessionPackageService;
import ca.mcgill.ecse321.sportcenter.service.SessionService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

/*
* <p>Service class in charge of managing sessions. It implements following use cases: </p>
* <p>Create, update, delete a sessionPackage </p>
* @author Émilia
*/
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SessionPackageIntegrationTest extends CommonTestSetup {

    @Autowired
    private TestRestTemplate client;

	@Autowired
    private AccountService accountService;

	@Autowired
	private SportCenterManagementService sportCenterService;

	@Autowired
    private CourseService courseService;

    @Autowired
    private SessionService sessionService;

	@Autowired
    private LocationService locationService;




	//-------------------------// Headers //------------------------------//

	private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";

    //------------------------// CourseType //----------------------//

	String courseName = "a Name";
    String description = "a Description.";
    Difficulty diff = Difficulty.Beginner;
    Status status = Status.Approved;
	Course course;

    //------------------------- Instructor ----------------------

	String email = "olivia@mail.com";
    String password = "secretPassword";
    String instructorName = "Olivia";
    String imageURL = "pfp.png";
	Instructor supervisor;

    //------------------------ Location ------------------------

	String floor = "2";
    String room = "200";
	Location location;

    //---------------------// SessionPackage //------------------//
    int validId;
    int aDuration = 6;
    LocalDate aDate = LocalDate.parse("2024-02-17");
    int aPriceReduction = 10;

    int aNewPriceReduction = 25;

    //------------------------// Sessions //-------------------------//

	Time startTime1 = Time.valueOf("08:00:00");
    Time endTime1 = Time.valueOf("09:00:00");
	LocalDate date1 = LocalDate.parse("2024-02-18");
    Integer capacity1 = 10;
    Session session1;
	

	Time startTime2 = Time.valueOf("10:00:00");
    Time endTime2 = Time.valueOf("11:00:00");
    LocalDate date2 = LocalDate.parse("2024-02-19");
    Integer capacity2 = 20;
    Session session2;

    //------------------------// Empty Result Tests //-----------------//

    @Test
    @Order(0)
    public void loginAndPrepDatabase() {
		Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("23:0:0");
		
        sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");
		course = courseService.createCourse(courseName, description, diff.toString(), status.toString(), 1, "none","none");
        location = locationService.createLocation(floor, room);
		supervisor = accountService.createInstructorAccount(email, password, instructorName, imageURL, "");
        


        // Save one account in the system
        accountService.createCustomerAccount(LOGIN_EMAIL, LOGIN_PASSWORD, "Julia", "Doritos.png", "");
        
        // Login into that account
        LoginRequestDTO request = new LoginRequestDTO(LOGIN_EMAIL, LOGIN_PASSWORD);
        ResponseEntity<LoginResponseDTO> response = client.postForEntity("/login", request, LoginResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }


    @Test
	@Order(1)
	public void testSessionPackagesByCourseEmptyResult(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/session-packages/course/" + course.getId();
		
		ResponseEntity<SessionPackageListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionPackageListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty

	}


    //-----------------// Create //-------------------------

    @Test
	@Order(2)
    public void testCreateSessionPackage(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);

		SessionPackageRequestDTO sessionPackageParam = new SessionPackageRequestDTO();
	    sessionPackageParam.setDate(aDate);
        sessionPackageParam.setDuration(aDuration);
        sessionPackageParam.setPriceReduction(aPriceReduction);
        HttpEntity<SessionPackageRequestDTO> requestEntity = new HttpEntity<>(sessionPackageParam, headers);

		
		String url = "/session-packages/" + course.getId();

		ResponseEntity<SessionPackageResponseDTO> response = client.exchange(url, HttpMethod.POST, requestEntity, SessionPackageResponseDTO.class);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
        SessionPackageResponseDTO createdPackage = response.getBody();
        assertEquals(aDate, createdPackage.getDate());
        assertEquals(aDuration, createdPackage.getDuration());
        assertEquals(aPriceReduction, createdPackage.getPriceReduction());
		validId = createdPackage.getId();

    }

    //-------------------------// Getter //------------------//

    @Test
    @Order(3)
    public void testSessionBySessionPackageEmptyResult(){

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/session-packages/" + validId + "/sessions";
		
		ResponseEntity<SessionListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionListDTO.class);
        // Assert
        assertNotNull(response);
        SessionListDTO sessionListDTO = response.getBody();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
        
    }

    @Test
    @Order(4)
    public void testSessionBySessionPackage(){
        session1 = sessionService.proposeSuperviseSession(startTime1, endTime1, date1, capacity1, supervisor.getId(), course.getId(), location.getId());
        session2 = sessionService.proposeSuperviseSession(startTime2, endTime2, date2, capacity2, supervisor.getId(), course.getId(), location.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = "/session-packages/" + validId + "/sessions";
		
		ResponseEntity<SessionListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Should be empty
        SessionListDTO sessionListDTO = response.getBody();
        assertEquals(2,sessionListDTO.getSessions().size());

    }

    //----------------------// Update //-----------------------//

    
     
    
    @Test
	@Order(5)
    public void testUpdateSessionPackage(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);

		SessionPackageRequestDTO sessionPackageParam = new SessionPackageRequestDTO();
        sessionPackageParam.setPriceReduction(aNewPriceReduction);
        HttpEntity<SessionPackageRequestDTO> requestEntity = new HttpEntity<>(sessionPackageParam, headers);

        String url = "/session-packages/" + validId;
        ResponseEntity<SessionPackageResponseDTO> response = client.exchange(url, HttpMethod.PUT, requestEntity, SessionPackageResponseDTO.class);
		assertNotNull(response);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        SessionPackageResponseDTO updatedPackage = response.getBody();
        assertEquals(aDate, updatedPackage.getDate());
        assertEquals(aDuration, updatedPackage.getDuration());
        assertEquals(aNewPriceReduction, updatedPackage.getPriceReduction());

    }

    

    //----------------------// Delete //-----------------------//
    @Test
	@Order(6)
	public void testDeleteValidSessionPackage(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = "/session-packages/" + validId ;
		ResponseEntity<SessionPackageResponseDTO> response = client.exchange(url, HttpMethod.DELETE, requestEntity, SessionPackageResponseDTO.class);

		assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());


	}
    
}
