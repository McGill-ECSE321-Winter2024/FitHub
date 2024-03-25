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
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.Location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Incomplete: need to include two new tests: findBySupervisorAndDate, findByLocationAndDate

/**
 * This class represents the JUnit test cases for the SessionRepository.
 * It ensures the correctness of CRUD operations related to sessions.
 * Specifically, it tests the creation and retrieval of sessions entities.
 */
@SpringBootTest
public class SessionRepositoryTests extends CommonTestSetup {
	@Autowired
	private SessionRepository sessionRepository;

    @Autowired
	private InstructorRepository instructorRepository;

    @Autowired
	private CourseRepository courseRepository;
    
    @Autowired
	private LocationRepository locationRepository;

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
        Session sessionFromDb = sessionRepository.findById(aSession.getId());

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

    /**
     * Test case to verify the creation and reading of a session by their course.
     */
	@Test
    public void testCreateAndReadSessionByCourse(){
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
        Session sessionFromDb = sessionRepository.findByCourseType(aSession.getCourseType()).get(0);

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

    /**
     * Test case to verify the creation and reading of a session by their supervisor.
     */
	@Test
    public void testCreateAndReadSessionBySupervisor(){
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
        Session sessionFromDb = sessionRepository.findBySupervisor(aSession.getSupervisor()).get(0);

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