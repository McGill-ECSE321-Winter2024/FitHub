/**
 * This class provides test cases for the SessionRepository class.
 * It verifies the functionalities related to creating and reading sessions.
 */
package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.Location;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class SessionRepositoryTests {
	@Autowired
	private SessionRepository sessionRepository;

    @Autowired
	private InstructorRepository instructorRepository;

    @Autowired
	private CourseRepository courseRepository;
    
    @Autowired
	private LocationRepository locationRepository;
    
    @Autowired
    private SportCenterRepository sportCenterRepo;

    private SportCenter sportCenter;

	/**
     * Method to clear the database before and after each test.
     */
	@AfterEach
    @BeforeEach
	public void clearDatabase() {
		sessionRepository.deleteAll();
        instructorRepository.deleteAll();
        courseRepository.deleteAll();
        locationRepository.deleteAll();
        sportCenterRepo.deleteAll();
	}

    /**
     * Method to create and save a sport center before each test.
     */
    @BeforeEach
    public void createAndSaveSportCenter() {
            SportCenter sportCenter = new SportCenter();
            sportCenter.setName("FitHub");
            sportCenter.setOpeningTime(Time.valueOf("08:00:00"));
            sportCenter.setClosingTime(Time.valueOf("18:00:00"));
            sportCenter.setEmail("info@fithub.com");
            sportCenter.setPhoneNumber("421-436-4444");
            sportCenter.setAddress("2011, University Street, Montreal");

            // Save sportCenterRepo
            sportCenter = sportCenterRepo.save(sportCenter);
    }

	/**
     * Test case to verify the creation and reading of a session.
     */
	@Test
	public void testPersistAndLoadSession() {
        // Create and save the location
        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenter);
        location = locationRepository.save(location);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        instructor = instructorRepository.save(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        aCourseType = courseRepository.save(aCourseType);

        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("09:00:00");
        Date date = Date.valueOf("2024-02-18");
        Integer capacity = 10;

        Session aSession = new Session();
        aSession.setStartTime(startTime);
        aSession.setEndTime(endTime);
        aSession.setDate(date);
        aSession.setCapacity(capacity);
        aSession.setSupervisor(instructor);
        aSession.setCourseType(aCourseType);
        aSession.setLocation(location);

        aSession = sessionRepository.save(aSession);

        // Retrieve session from the database
        Session sessionFromDb = sessionRepository.findSessionById(aSession.getId());

		// Assert that session is not null and has correct attributes.
		assertNotNull(sessionFromDb);
        assertEquals(startTime.toString(), sessionFromDb.getStartTime().toString());
        assertEquals(endTime.toString(), sessionFromDb.getEndTime().toString());
        assertEquals(capacity, sessionFromDb.getCapacity());

        //making sure the other objects were also saved
        assertNotNull(sessionFromDb.getSupervisor());
        assertEquals(instructor.getId(), sessionFromDb.getSupervisor().getId());

        //Assert that the information in the course association has been saved. 
        assertNotNull(sessionFromDb.getCourseType());
        assertEquals(aCourseType.getId(), sessionFromDb.getCourseType().getId());

        //Assert that the information in the location association has been saved.
        assertNotNull(sessionFromDb.getLocation());
        assertEquals(location.getId(), sessionFromDb.getLocation().getId()); 
	}
}
