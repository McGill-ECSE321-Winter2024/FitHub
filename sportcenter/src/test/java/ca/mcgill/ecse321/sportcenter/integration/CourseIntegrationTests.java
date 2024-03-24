package ca.mcgill.ecse321.sportcenter.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.description;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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

import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseListDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.CustomerResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.InstructorResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LoginResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.OwnerResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.CourseService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CourseIntegrationTests extends CommonTestSetup{
    @Autowired
    private TestRestTemplate client;

    @Autowired
    AccountService accountService;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    SportCenterManagementService sportCenterService;

    private String LOGIN_EMAIL = "julia@mail.com";
    private String LOGIN_PASSWORD = "secret1456165";

    private static final List<Course> COURSES = new ArrayList<>();

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

    //Testing the non-valid requests where the elements don't exist

    @Test
    @Order(1)
    public void testFindAllCoursesEmpty() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<CourseListDTO> response = client.exchange("/courses", HttpMethod.GET, requestEntity, CourseListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }

    @Test
    @Order(1)
    public void testFindAllCoursesByDifficultyEmpty() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<CourseListDTO> response = client.exchange("/courses?difficulty=Intermediate", HttpMethod.GET, requestEntity, CourseListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }

    @Test
    @Order(1)
    public void testFindAllCoursesByStatusEmpty() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<CourseListDTO> response = client.exchange("/courses?status=Approved", HttpMethod.GET, requestEntity, CourseListDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }

    @Test
    @Order(1)
    public void testFindAllCoursesByNameEmpty() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<CourseResponseDTO> response = client.exchange("/courses?name=baba", HttpMethod.GET, requestEntity, CourseResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }

    @Test
    @Order(1)
    public void testFindAllCoursesByIdEmpty() {
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        // Act
        ResponseEntity<CourseResponseDTO> response = client.exchange("/courses/4", HttpMethod.GET, requestEntity, CourseResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Should be empty
    }

    //Testing the valid tests which should be returning content


    @Test
    @Order(1)
    public void testCreateCourse() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);

        CourseRequestDTO courseRequest = new CourseRequestDTO();
        courseRequest.setName("Test Course");
        courseRequest.setDescription("Test Description");
        courseRequest.setDifficulty(CourseRequestDTO.Difficulty.Beginner);
        courseRequest.setStatus(CourseRequestDTO.Status.Closed);

        HttpEntity<CourseRequestDTO> requestEntity = new HttpEntity<>(courseRequest, headers);

        // When
        ResponseEntity<CourseResponseDTO> response = client.exchange("/courses", HttpMethod.POST, requestEntity, CourseResponseDTO.class);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CourseResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Test Course", responseBody.getName());
        assertEquals("Test Description", responseBody.getDescription());
        // Add assertions for other fields as needed
    }

    @Test
    @Order(1)
    public void testUpdateValidCourse() {
        COURSES.clear();
        // First, create a course in the database
        CourseRequestDTO courseRequest = new CourseRequestDTO();
        courseRequest.setName("Original Course Name");
        courseRequest.setDescription("Original Course Description");
        courseRequest.setDifficulty(CourseRequestDTO.Difficulty.Beginner);
        courseRequest.setStatus(CourseRequestDTO.Status.Closed);
    
        // Set up authentication for this test
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(LOGIN_EMAIL, LOGIN_PASSWORD);
        HttpEntity<CourseRequestDTO> createRequestEntity = new HttpEntity<>(courseRequest, headers);
    
        // Perform the request to create the course
        String createUrl = "/courses/";
        ResponseEntity<CourseResponseDTO> createResponse = client.exchange(createUrl, HttpMethod.PUT, createRequestEntity, CourseResponseDTO.class, 1);
    
        // Verify that the course was created successfully
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        CourseResponseDTO createdCourse = createResponse.getBody();
        assertNotNull(createdCourse);
        assertEquals("Original Course Name", createdCourse.getName());
        // Add assertions for other fields as needed
    
        // Now, update the created course
        CourseRequestDTO updatedCourseRequest = new CourseRequestDTO();
        updatedCourseRequest.setName("Updated Course Name");
        updatedCourseRequest.setDescription("Updated Course Description");
        updatedCourseRequest.setDifficulty(CourseRequestDTO.Difficulty.Advanced);
        updatedCourseRequest.setStatus(CourseRequestDTO.Status.Approved);
    
        // Set up authentication for the update request
        HttpEntity<CourseRequestDTO> updateRequestEntity = new HttpEntity<>(updatedCourseRequest, headers);
    
        // Perform the update request
        String updateUrl = "/courses/{id}";
        ResponseEntity<CourseResponseDTO> updateResponse = client.exchange(updateUrl, HttpMethod.PUT, updateRequestEntity, CourseResponseDTO.class, createdCourse.getId());
    
        // Verify response
        assertEquals(HttpStatus.ACCEPTED, updateResponse.getStatusCode());
        CourseResponseDTO updatedCourse = updateResponse.getBody();
        assertNotNull(updatedCourse);
        assertEquals("Updated Course Name", updatedCourse.getName());
        // Add assertions for other fields as needed
    }
    



}
