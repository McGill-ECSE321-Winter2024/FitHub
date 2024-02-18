package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

@SpringBootTest
public class RegistrationRepositoryTest {

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private LocationRepository locationRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private RegistrationRepository registrationRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() { 
        locationRepo.deleteAll();
        courseRepo.deleteAll();
        instructorRepo.deleteAll();
        registrationRepo.deleteAll();
        customerRepo.deleteAll();
        sessionRepo.deleteAll();
    }
    
    @Test
    public void testCreateAndReadRegistration() {
        
        //create a customer 
        String custName = "Bob";
        String custEmail = "bob@gmail.com";
        String custPassword = "123456";
        String custImageURL = "https://pixabay.com/vectors/blank-profile-picture-mystery-man-973460/";
        Customer cust = new Customer(custName, custEmail, custPassword, custImageURL, SportCenter.getSportCenter());
        cust = customerRepo.save(cust);
        
        //create a supervisor(instructor)
        String instrName = "Bob";
        String instrEmail = "bob@gmail.com";
        String instrPassword = "123456";
        String instrImageURL = "https://pixabay.com/vectors/blank-profile-picture-mystery-man-973460/";
        Instructor instr = new Instructor(instrName, instrEmail, instrPassword, instrImageURL, SportCenter.getSportCenter());
        instr = instructorRepo.save(instr);
        
        //create a location
        String floor = "2";
        String room = "M12";
        Location location = new Location(floor, room, SportCenter.getSportCenter());
        location = locationRepo.save(location);

        //create a course
        String name = "Cardio";
        String description = "Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope, squat jumps, lunges cycling and more.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;
        Course course = new Course(name, diff, status, description, SportCenter.getSportCenter());
        course = courseRepo.save(course);

        //create a session
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("11:00:00");
        int cap = 50;
        Date date = Date.valueOf("2024-02-09");
        Session session = new Session(startTime, endTime, date, cap, instr, course, location);
        session = sessionRepo.save(session);

        //create a registration
        Registration.Key key = new Registration.Key(cust, session);
        Registration reg = new Registration(key);
        reg = registrationRepo.save(reg);
        Registration regFromDb = registrationRepo.findRegistrationByKey(key);

        assertNotNull(regFromDb);
        assertNotNull(regFromDb.getKey());
        assertNotNull(regFromDb.getKey().getCustomer());
        assertEquals(cust.getId(), regFromDb.getKey().getCustomer().getId());
        assertNotNull(regFromDb.getKey().getSession());
        assertEquals(session.getId(), regFromDb.getKey().getSession().getId());

    }
}
