package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

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

/**
 * This class provides test cases for the RegistrationRepository class.
 * It verifies the functionalities related to creating and reading registrations.
 */
@SpringBootTest
public class RegistrationRepositoryTests extends CommonTestSetup {

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
    
    /**
     * Test case to verify the creation and reading of a registration.
     */
    @Test
    public void testCreateAndReadRegistration() {
        
        //create a customer 
        Customer customer = new Customer();
        customer.setEmail("bob@gmail.com");
        customer.setPassword("12345");
        customer.setName("Bob");
        customer.setImageURL("pfp123.com");
        // Save into database
        customer = customerRepo.save(customer);
        
        //create a supervisor(instructor)
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Jumijabasali");
        instructor.setImageURL("pfp.com");
        instructor.setCenter(sportCenter);
        // Save into database
        instructor = instructorRepo.save(instructor);
        
        //create a location
        Location location = new Location();
        location.setFloor( "aFloor");
        location.setRoom( "aRoom");
        location.setCenter(sportCenter);
        // Save into database
        location = locationRepo.save(location);

        //create a course
        Course course = new Course();
        course.setName("Cardio");
        course.setDescription("Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope");
        course.setDifficulty(Difficulty.Beginner);
        course.setStatus(Status.Approved);
        // Save into database
        course = courseRepo.save(course);

        //create a session
        Session aSession = new Session();
        aSession.setStartTime(Time.valueOf("08:00:00"));
        aSession.setEndTime(Time.valueOf("09:00:00"));
        aSession.setDate(Date.valueOf("2024-02-18"));
        aSession.setCapacity(50);
        aSession.setSupervisor(instructor);
        aSession.setCourseType(course);
        aSession.setLocation(location);
        // Save into database
        aSession = sessionRepo.save(aSession);

        //create a registration
        Registration.Key key = new Registration.Key(customer, aSession);
        Registration reg = new Registration(key);
        reg = registrationRepo.save(reg);

        // Retrieve registration from the database
        Registration regFromDb = registrationRepo.findRegistrationByKey(key);

        //Assert that session is not null and has correct attributes.
        assertNotNull(regFromDb);
        assertNotNull(regFromDb.getKey());

        //Assert that the information in the session association has been saved. 
        assertNotNull(regFromDb.getKey().getSession()); 
        assertEquals(aSession.getId(), regFromDb.getKey().getSession().getId());

        //Assert that the information in the customer association has been saved.
        assertNotNull(regFromDb.getKey().getCustomer());
        assertEquals(customer.getId(), regFromDb.getKey().getCustomer().getId());
        
    }

    @Test
    public void testFindAllRegistrationsBySession() {
        // Create entities
        Customer customer = new Customer();
        customer.setEmail("bob@gmail.com");
        customer.setPassword("12345");
        customer.setName("Bob");
        customer.setImageURL("pfp123.com");
        customer = customerRepo.save(customer);

        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Jumijabasali");
        instructor.setImageURL("pfp.com");
        instructor.setCenter(sportCenter);
        instructor = instructorRepo.save(instructor);

        Location location = new Location();
        location.setFloor("aFloor");
        location.setRoom("aRoom");
        location.setCenter(sportCenter);
        location = locationRepo.save(location);

        Course course = new Course();
        course.setName("Cardio");
        course.setDescription("Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope");
        course.setDifficulty(Course.Difficulty.Beginner);
        course.setStatus(Course.Status.Approved);
        course = courseRepo.save(course);

        Session session = new Session();
        session.setStartTime(Time.valueOf("08:00:00"));
        session.setEndTime(Time.valueOf("09:00:00"));
        session.setDate(Date.valueOf("2024-02-18"));
        session.setCapacity(50);
        session.setSupervisor(instructor);
        session.setCourseType(course);
        session.setLocation(location);
        session = sessionRepo.save(session);

        Registration.Key key = new Registration.Key(customer, session);
        Registration registration = new Registration(key);
        registrationRepo.save(registration);

        // Find all registrations for the session
        List<Registration> registrations = registrationRepo.findAllByKeySession(session);

        // Assertions
        assertNotNull(registrations);
        assertEquals(1, registrations.size());
        assertEquals(customer.getId(), registrations.get(0).getKey().getCustomer().getId());
        assertEquals(session.getId(), registrations.get(0).getKey().getSession().getId());
    }

    @Test
    public void testFindAllRegistrationsByCustomer() {
        // Create entities
        Customer customer = new Customer();
        customer.setEmail("bob@gmail.com");
        customer.setPassword("12345");
        customer.setName("Bob");
        customer.setImageURL("pfp123.com");
        customer = customerRepo.save(customer);

        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Jumijabasali");
        instructor.setImageURL("pfp.com");
        instructor.setCenter(sportCenter);
        instructor = instructorRepo.save(instructor);

        Location location = new Location();
        location.setFloor("aFloor");
        location.setRoom("aRoom");
        location.setCenter(sportCenter);
        location = locationRepo.save(location);

        Course course = new Course();
        course.setName("Cardio");
        course.setDescription("Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope");
        course.setDifficulty(Course.Difficulty.Beginner);
        course.setStatus(Course.Status.Approved);
        course = courseRepo.save(course);

        Session session = new Session();
        session.setStartTime(Time.valueOf("08:00:00"));
        session.setEndTime(Time.valueOf("09:00:00"));
        session.setDate(Date.valueOf("2024-02-18"));
        session.setCapacity(50);
        session.setSupervisor(instructor);
        session.setCourseType(course);
        session.setLocation(location);
        session = sessionRepo.save(session);

        Registration.Key key = new Registration.Key(customer, session);
        Registration registration = new Registration(key);
        registrationRepo.save(registration);

        // Find all registrations for the customer
        List<Registration> registrations = registrationRepo.findAllByKeyCustomer(customer);

        // Assertions
        assertNotNull(registrations);
        assertEquals(1, registrations.size());
        assertEquals(customer.getId(), registrations.get(0).getKey().getCustomer().getId());
        assertEquals(session.getId(), registrations.get(0).getKey().getSession().getId());
    }
}