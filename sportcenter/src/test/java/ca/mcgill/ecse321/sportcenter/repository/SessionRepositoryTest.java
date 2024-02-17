package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.model.Location;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class SessionRepositoryTest {
	@Autowired
	private SessionRepository sessionRepository;

    @Autowired
	private InstructorRepository instructorRepository;

    @Autowired
	private CourseRepository courseRepository;
    
    @Autowired
	private LocationRepository locationRepository;
 
    @Autowired
	private SportCenterRepository sportCenterRepository;

	@AfterEach
	public void clearDatabase() {
		sessionRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadSession() {

        Time startTime = new Time(0);
        Time endTime = new Time(0);
        Date date = new Date(0);
        int aCapacity = 50;

        String email = "aEmail";
        String password = "aPassword";
        String name1 = "aName1";
        String name2 = "aName2";
        String imageURL = "aImageURL";
        String description = "aDescription";
        String floor = "aFloor";
        String room = "aRoom";

        Instructor aSupervisor = new Instructor(email, password, name1, imageURL, SportCenter.getSportCenter());
        Course aCourseType = new Course(name2, Course.Difficulty.Beginner, Course.Status.Approved , description, SportCenter.getSportCenter());
        Location aLocation = new Location(floor, room, SportCenter.getSportCenter());

        Session aSession = new Session(startTime, endTime, date, aCapacity, aSupervisor, aCourseType, aLocation);

        SportCenter center = sportCenterRepository.save(SportCenter.getSportCenter());
        Instructor savedSupervisor = instructorRepository.save(aSupervisor);
        Course savedCourse = courseRepository.save(aCourseType);
        Location savedLocation = locationRepository.save(aLocation);

        Session savedSession = sessionRepository.save(aSession);

        // Retrieve session from the database
        Session sessionFromDb = sessionRepository.findSessionById(savedSession.getId());

		// Assert that session is not null and has correct attributes.
		assertNotNull(sessionFromDb);
        assertEquals(aCapacity, sessionFromDb.getCapacity());
        assertEquals(startTime.toString(), sessionFromDb.getEndTime().toString());
        assertEquals(endTime.toString(), sessionFromDb.getStartTime().toString());

        //Assert that the information in the instructor association has been saved.
        assertEquals(email, sessionFromDb.getSupervisor().getEmail());
        assertEquals(password, sessionFromDb.getSupervisor().getPassword());
        assertEquals(name1, sessionFromDb.getSupervisor().getName());
        assertEquals(imageURL, sessionFromDb.getSupervisor().getImageURL());

        //Assert that the information in the course association has been saved. 
        assertEquals(name2, sessionFromDb.getCourseType().getName());
        assertEquals(Course.Difficulty.Beginner, sessionFromDb.getCourseType().getDifficulty());
        assertEquals(Course.Status.Approved, sessionFromDb.getCourseType().getStatus());
        assertEquals(description, sessionFromDb.getCourseType().getDescription());

        //Assert that the information in the location association has been saved. 
        assertEquals(room, sessionFromDb.getLocation().getRoom());
        assertEquals(floor, sessionFromDb.getLocation().getFloor());
        
        //making sure the other objects were also saved
        assertNotNull(savedSupervisor);
        assertEquals(savedSupervisor.getId(), sessionFromDb.getSupervisor().getId());
        assertEquals(savedSupervisor.getEmail(), sessionFromDb.getSupervisor().getEmail());
        assertEquals(savedSupervisor.getPassword(), sessionFromDb.getSupervisor().getPassword());
        assertEquals(savedSupervisor.getName(), sessionFromDb.getSupervisor().getName());
        assertEquals(savedSupervisor.getImageURL(), sessionFromDb.getSupervisor().getImageURL());

        //Assert that the information in the course association has been saved. 
        assertNotNull(savedCourse);
        assertEquals(savedCourse.getId(), sessionFromDb.getCourseType().getId());
        assertEquals(savedCourse.getName(), sessionFromDb.getCourseType().getName());
        assertEquals(savedCourse.getDifficulty(), sessionFromDb.getCourseType().getDifficulty());
        assertEquals(savedCourse.getStatus(), sessionFromDb.getCourseType().getStatus());
        assertEquals(savedCourse.getDescription(), sessionFromDb.getCourseType().getDescription());

        //Assert that the information in the location association has been saved.
        assertNotNull(savedLocation);
        assertEquals(savedLocation.getId(), sessionFromDb.getLocation().getId()); 
        assertEquals(savedLocation.getRoom(), sessionFromDb.getLocation().getRoom());
        assertEquals(savedLocation.getFloor(), sessionFromDb.getLocation().getFloor());

	}
}