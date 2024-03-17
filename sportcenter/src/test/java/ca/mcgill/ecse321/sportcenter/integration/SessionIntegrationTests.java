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

import org.springframework.test.context.event.annotation.BeforeTestClass;

import ca.mcgill.ecse321.sportcenter.dto.AccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.CustomerResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.InstructorResponseDTO;

import ca.mcgill.ecse321.sportcenter.dto.OwnerResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.service.SessionService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SessionIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private SessionService SessionService;

    @Autowired
	private SessionRepository sessionRepository;

    @BeforeEach
	@AfterEach
	public void clearDatabase() {
		sessionRepository.deleteAll();
	}

	@Test
	public void testCreateAndGetSession() {
		
	}

    
}
