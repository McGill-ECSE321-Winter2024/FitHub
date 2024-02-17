package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.startsWith;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.sportcenter.SportcenterApplication;
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

	@AfterEach
	public void clearDatabase() {
		sessionRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadPerson() {

        String name = "FitHub";
        Time openingTime = new Time(1);
        Time closingTime = new Time(2);
        String address = "Somewhere";
        String email ="fithub@gmail.com";
        String number = "613-301-8300";
        
        SportCenter sportcenter = new SportCenter(name, openingTime, closingTime, address, email, number);
        
        Time startTime = new Time(0);
        Time endTime = new Time(0);
        Date date = new Date(0);
        int aCapacity = 50;

        Instructor aSupervisor = new Instructor("aEmail", "aPassword", "aName", "aImageURL", sportcenter);
        Course aCourseType = new Course("aName", Course.Difficulty.Beginner, Course.Status.Approved , "aDescription", sportcenter);
        Location aLocation = new Location("aFloor", "aRoom", sportcenter);

        Session aSession = new Session(startTime, endTime, date, aCapacity, aSupervisor, aCourseType, aLocation);

		// Save person
		aSession = sessionRepository.save(aSession);

        int aSessionId = aSession.getId();

        Session sessionFromDb = sessionRepository.findSessionById(aSessionId);

		// Assert that person is not null and has correct attributes.
		assertNotNull(sessionFromDb);
        

	}
}