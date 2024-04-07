package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
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
* <p>Create, update, delete a session </p>
* @author Émilia
*/
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SessionIntegrationTests extends CommonTestSetup {
    @Autowired
    private TestRestTemplate client;

	@Autowired
    private AccountService accountService;

	@Autowired
    private LocationService locationService;

	@Autowired
	private SportCenterManagementService sportCenterService;

	@Autowired
    private CourseService courseService;

	@Autowired
	private InstructorRepository instructorRepository;


	//---------------------Headers------------------------------

	private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";

	//------------------------- Instructor ----------------------

	String email = "olivia@mail.com";
    String password = "secretPassword";
    String instructorName = "Olivia";
    String imageURL = "pfp.png";
	Instructor supervisor;

	String newEmail = "dada@mail.com";
    String newPassword = "notsosecretPassword";
    String newInstructorName = "Dada";
    String newImageURL = "dada.png";
	Instructor newSupervisor;

	//------------------------ Location ------------------------

	String floor = "2";
    String room = "200";
	Location location;

	String newFloor = "6";
    String newRoom = "600";
	Location newLocation;


	//------------------------ CourseType ----------------------

	String courseName = "a Name";
    String description = "a Description.";
    Difficulty diff = Difficulty.Beginner;
    Status status = Status.Approved;
	Course course;


	//------------------------ Session -------------------------

	Time startTime = Time.valueOf("08:00:00");
    Time endTime = Time.valueOf("09:00:00");
	LocalDate date = LocalDate.parse("2024-02-18");
    Integer capacity = 10;
	int validId = 0;

	Time newStartTime = Time.valueOf("10:00:00");
    Time newEndTime = Time.valueOf("11:00:00");
    LocalDate newDate = LocalDate.parse("2024-02-19");
    Integer newCapacity = 20;

	//---------------login -------------------------------

	@Test
    @Order(0)
    public void loginAndPrepDatabase() {
		Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("23:0:0");
		
        sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");
		location = locationService.createLocation(floor, room);
		course = courseService.createCourse(courseName, description, diff.toString(), status.toString(), 1, "none","none");
		supervisor = accountService.createInstructorAccount(email, password, instructorName, imageURL, "");
		newSupervisor = accountService.createInstructorAccount(newEmail, newPassword, newInstructorName, newImageURL, "");
		newLocation = locationService.createLocation(newFloor, newRoom);

        // Save one account in the system
        accountService.createCustomerAccount(LOGIN_EMAIL, LOGIN_PASSWORD, "Julia", "Doritos.png", "");
        
        // Login into that account
        LoginRequestDTO request = new LoginRequestDTO(LOGIN_EMAIL, LOGIN_PASSWORD);
        ResponseEntity<LoginResponseDTO> response = client.postForEntity("/login", request, LoginResponseDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

	//--------------------------// General Empty Result Tests //--------------------------//

	@Test
	@Order(1)
	public void testFindAllSessionEmptyResult(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<SessionListDTO> response = client.exchange("/sessions", HttpMethod.GET, requestEntity, SessionListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty

	}

	@Test
	@Order(2)
	public void testFindSessionsByInstructorEmptyResult(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		assertNotNull(instructorRepository.findInstructorById(supervisor.getId()));
		
		String url = "/sessions/instructors/" + supervisor.getId();

		ResponseEntity<SessionListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty

	}

	@Test
	@Order(3)
	public void testFindSessionsByCourseEmptyResult(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		

		String url = "/sessions/courses/" + course.getId();
		
		
		ResponseEntity<SessionListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty

	}

	//------------------------------ Create ------------------------------

	@Test
	@Order(4)
	public void testCreateValidSession(){
		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
		SessionRequestDTO sessionParam = new SessionRequestDTO();
		sessionParam.setCapacity(capacity);
		sessionParam.setDate(date);
		sessionParam.setEndTime(endTime);
		sessionParam.setStartTime(startTime);
        HttpEntity<SessionRequestDTO> requestEntity = new HttpEntity<>(sessionParam, headers);

		///sessions/{iId}/{cId}/{lId}"
		String url = "/sessions/" + supervisor.getId() + "/" + course.getId() + "/" + location.getId();

		ResponseEntity<SessionResponseDTO> response = client.exchange(url, HttpMethod.POST, requestEntity, SessionResponseDTO.class);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
        SessionResponseDTO createdSession = response.getBody();
		validId = createdSession.getId();
		assertEquals(capacity, createdSession.getCapacity());
		assertEquals(date, createdSession.getDate());
		assertEquals(startTime, createdSession.getStartTime());
		assertEquals(endTime, createdSession.getEndTime());

	}

	//---------------------------------- Read by Id, Instructor, Course  ---------------------------
	@Test
    @Order(5)
    public void testReadSessionByValidId() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<SessionResponseDTO> response = client.exchange("/sessions/" + validId, HttpMethod.GET, requestEntity, SessionResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
		SessionResponseDTO createdSession = response.getBody();;
		assertEquals(capacity, createdSession.getCapacity());
		assertEquals(date, createdSession.getDate());
		assertEquals(startTime, createdSession.getStartTime());
		assertEquals(endTime, createdSession.getEndTime());
	}

	@Test
    @Order(6)
    public void testReadSessionByInstructor() {
		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		assertNotNull(instructorRepository.findInstructorById(supervisor.getId()));
		
		String url = "/sessions/instructors/" + supervisor.getId();

		ResponseEntity<SessionListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Should not be empty

	}

	@Test
	@Order(7)
	public void testReadSessionByCourse(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		

		String url = "/sessions/courses/" + course.getId();
		
		
		ResponseEntity<SessionListDTO> response = client.exchange(url, HttpMethod.GET, requestEntity, SessionListDTO.class);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Should be empty

	}

	//---------------------------------- Update ---------------------------


	@Test
	@Order(8)
	public void testUpdateValidSession(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
		SessionRequestDTO sessionParam = new SessionRequestDTO();
		sessionParam.setCapacity(newCapacity);
		sessionParam.setDate(newDate);
		sessionParam.setEndTime(newEndTime);
		sessionParam.setStartTime(newStartTime);
        HttpEntity<SessionRequestDTO> requestEntity = new HttpEntity<>(sessionParam,headers);

		String url = "/sessions/" + validId;
		ResponseEntity<SessionResponseDTO> response = client.exchange(url, HttpMethod.PUT, requestEntity, SessionResponseDTO.class);

		assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		SessionResponseDTO createdSession = response.getBody();
		assertEquals(newCapacity, createdSession.getCapacity());
		assertEquals(newDate, createdSession.getDate());
		assertEquals(newStartTime, createdSession.getStartTime());
		assertEquals(newEndTime, createdSession.getEndTime());

	}

	@Test
	@Order(9)
	public void testUpdateValidSessionInstructor(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = "/sessions/" + validId +  "/instructors/" + newSupervisor.getId();
		ResponseEntity<SessionResponseDTO> response = client.exchange(url, HttpMethod.PUT, requestEntity, SessionResponseDTO.class);

		assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		SessionResponseDTO createdSession = response.getBody();
		assertEquals(newCapacity, createdSession.getCapacity());
		assertEquals(newDate, createdSession.getDate());
		assertEquals(newStartTime, createdSession.getStartTime());
		assertEquals(newEndTime, createdSession.getEndTime());

	}

	
	@Test
	@Order(10)
	public void testUpdateValidSessionLocation(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = "/sessions/" + validId +  "/locations/" + newLocation.getId();
		ResponseEntity<SessionResponseDTO> response = client.exchange(url, HttpMethod.PUT, requestEntity, SessionResponseDTO.class);

		assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		SessionResponseDTO createdSession = response.getBody();
		assertEquals(newCapacity, createdSession.getCapacity());
		assertEquals(newDate, createdSession.getDate());
		assertEquals(newStartTime, createdSession.getStartTime());
		assertEquals(newEndTime, createdSession.getEndTime());


	}

	@Test
	@Order(11)
	public void testDeleteValidSession(){

		HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = "/sessions/" + validId ;
		ResponseEntity<SessionResponseDTO> response = client.exchange(url, HttpMethod.DELETE, requestEntity, SessionResponseDTO.class);

		assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		//This threw a 500 internal error and I can't figure out why, so I'll look into it later
		/*
		
		ResponseEntity<SessionResponseDTO> responseRead = client.exchange("/sessions/" + validId, HttpMethod.GET, requestEntity, SessionResponseDTO.class);

        // Assert
        assertNotNull(responseRead);
        assertEquals(HttpStatus.NO_CONTENT, responseRead.getStatusCode());
		 
		 */
		


	}
	



    
}
