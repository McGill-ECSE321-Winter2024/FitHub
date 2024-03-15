package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;

@SpringBootTest
public class RegistrationServiceTests {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SportCenterRepository sportCenterRepository;

    @InjectMocks
    private RegistrationService registrationService;

    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        registrationRepository.deleteAll();
        customerRepository.deleteAll();
        sessionRepository.deleteAll();
        instructorRepository.deleteAll();
        locationRepository.deleteAll();
        courseRepository.deleteAll();
        sportCenterRepository.deleteAll();
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
        sportCenter = sportCenterRepository.save(sportCenter);
    }

    /**
     * Create and save a Customer and Session instance before each test.
     */
    @BeforeEach
    public void createAndSaveCustomerAndSession() {
        SportCenter sportCenter = new SportCenter();
        sportCenter.setName("FitHub");
        sportCenter.setOpeningTime(Time.valueOf("08:00:00"));
        sportCenter.setClosingTime(Time.valueOf("18:00:00"));
        sportCenter.setEmail("info@fithub.com");
        sportCenter.setPhoneNumber("421-436-4444");
        sportCenter.setAddress("2011, University Street, Montreal");

        // Save sportCenterRepo
        sportCenter = sportCenterRepository.save(sportCenter);

        //create a customer 
        Customer customer = new Customer();
        customer.setEmail("bob@gmail.com");
        customer.setPassword("12345");
        customer.setName("Bob");
        customer.setImageURL("pfp123.com");
        // Save into database
        customer = customerRepository.save(customer);

        //create a supervisor(instructor)
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Jumijabasali");
        instructor.setImageURL("pfp.com");
        // Save into database
        instructor = instructorRepository.save(instructor);
        
        //create a location
        Location location = new Location();
        location.setFloor( "aFloor");
        location.setRoom( "aRoom");
        location.setCenter(sportCenter);
        // Save into database
        location = locationRepository.save(location);

        //create a course
        Course course = new Course();
        course.setName("Cardio");
        course.setDescription("Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope");
        course.setDifficulty(Difficulty.Beginner);
        course.setStatus(Status.Approved);
        // Save into database
        course = courseRepository.save(course);

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
        aSession = sessionRepository.save(aSession);
    }

    //--------------------------// Create Registration Tests //--------------------------//

    @Test
    public void testCreateValidRegistration() {
        Customer customer = customerRepository.findCustomerById(0);
        Session session = sessionRepository.findSessionById(0);

        Registration registration = new Registration(new Registration.Key(customer, session));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration createdRegistration = registrationService.createRegistration(customer, session);

        assertNotNull(createdRegistration);
        assertEquals(customer, createdRegistration.getKey().getCustomer());
        assertEquals(session, createdRegistration.getKey().getSession());
        verify(registrationRepository, times(1)).save(createdRegistration);
    }

    //--------------------------// Update Registration Tests //--------------------------//

    @Test
    public void updateValidRegistration() {
        Customer customer = customerRepository.findCustomerById(0);
        Session session = sessionRepository.findSessionById(0);

        Registration.Key key = new Registration.Key(customer, session);
        Registration registration = new Registration(key);

        when(registrationRepository.findRegistrationByKey(key)).thenReturn(registration);

        Customer newCustomer = new Customer();
        newCustomer.setEmail("newCustomer@gmail.com");
        Session newSession = new Session();
        newSession.setCapacity(64);

        Registration.Key newKey = key;
        newKey.setCustomer(newCustomer);
        newKey.setSession(newSession);
        Registration newRegistration = new Registration(key);
        
        when(registrationRepository.save(any(Registration.class))).thenReturn(newRegistration);
        Registration savedRegistration = registrationService.updateRegistration(newKey, newCustomer, newSession);

        verify(registrationRepository, times(1)).findRegistrationByKey(newKey);
        verify(registrationRepository, times(1)).save(any(Registration.class));
        assertNotNull(savedRegistration);
        assertEquals(newCustomer, savedRegistration.getKey().getCustomer());
        assertEquals(newSession, savedRegistration.getKey().getSession());
    }

    //--------------------------// Find Registration Tests //--------------------------//

    @Test
    public void testReadRegistrationByValidKey() {
        Customer customer = customerRepository.findCustomerById(0);
        Session session = sessionRepository.findSessionById(0);

        Registration.Key key = new Registration.Key(customer, session);
        Registration registration = new Registration(key);
        when(registrationRepository.findRegistrationByKey(key)).thenReturn(registration);

        Registration foundRegistration = registrationService.findRegistrationByKey(key);

        assertNotNull(foundRegistration);
        assertEquals(registration.getKey().getCustomer(), foundRegistration.getKey().getCustomer());
        assertEquals(registration.getKey().getSession(), foundRegistration.getKey().getSession());
    }
}