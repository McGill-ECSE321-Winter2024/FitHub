package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.sql.Time;

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
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.CourseService;
import ca.mcgill.ecse321.sportcenter.service.LocationService;
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


	//-------------------------// Headers //------------------------------//

	private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";

    //------------------------// CourseType //----------------------//

	String courseName = "a Name";
    String description = "a Description.";
    Difficulty diff = Difficulty.Beginner;
    Status status = Status.Approved;
	Course course;

    //---------------------// SessionPackage //------------------//
    int validId;
    int aDuration = 6;
    Date aDate = Date.valueOf("2024-02-18");
    int aPriceReduction = 10;

    int aNewPriceReduction = 25;

    //------------------------// Empty Result Tests //-----------------//

    @Test
    @Order(0)
    public void loginAndPrepDatabase() {
		Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("23:0:0");
		
        sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");
		course = courseService.createCourse(courseName, description, diff.toString(), status.toString());

        // Save one account in the system
        accountService.createCustomerAccount(LOGIN_EMAIL, LOGIN_PASSWORD, "Julia", "Doritos.png");
        
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

        String url = "/sessionPackages/course/" + course.getId();
		
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

		
		String url = "/sessionPackages/" + course.getId();

		ResponseEntity<SessionPackageResponseDTO> response = client.exchange(url, HttpMethod.POST, requestEntity, SessionPackageResponseDTO.class);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
        SessionPackageResponseDTO createdPackage = response.getBody();
        assertEquals(aDate, createdPackage.getDate());
        assertEquals(aDuration, createdPackage.getDuration());
        assertEquals(aPriceReduction, createdPackage.getPriceReduction());
		validId = createdPackage.getId();

    }

    //----------------------// Update //-----------------------//

    @Test
	@Order(3)
    public void testUpdateSessionPackage(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);

		SessionPackageRequestDTO sessionPackageParam = new SessionPackageRequestDTO();
        sessionPackageParam.setPriceReduction(aNewPriceReduction);
        HttpEntity<SessionPackageRequestDTO> requestEntity = new HttpEntity<>(sessionPackageParam, headers);

        String url = "/sessionPackages/" + validId;
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
	@Order(4)
	public void testDeleteValidSessionPackage(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = "/sessionPackages/" + validId ;
		ResponseEntity<SessionPackageResponseDTO> response = client.exchange(url, HttpMethod.DELETE, requestEntity, SessionPackageResponseDTO.class);

		assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());


	}
    
}
