package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private Customer customer;
    private Session session;
    private Instructor instructor;
    private Location location;
    private Course course;

    /**
     * Create and save a Customer and Session instance before each test.
     */
    @BeforeEach
    public void createAndSaveCustomerAndSession() {
        //create a customer 
        customer = new Customer();
        customer.setEmail("bob@gmail.com");
        customer.setPassword("12345");
        customer.setName("Bob");
        customer.setImageURL("pfp123.com");

        //create a supervisor(instructor)
        instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Jumijabasali");
        instructor.setImageURL("pfp.com");
        
        //create a location
        location = new Location();
        location.setFloor( "aFloor");
        location.setRoom( "aRoom");

        //create a course
        course = new Course();
        course.setName("Cardio");
        course.setDescription("Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope");
        course.setDifficulty(Difficulty.Beginner);
        course.setStatus(Status.Approved);

        //create a session
        session = new Session();
        session.setStartTime(Time.valueOf("08:00:00"));
        session.setEndTime(Time.valueOf("09:00:00"));
        session.setDate(Date.valueOf("2024-02-18"));
        session.setCapacity(50);
        session.setSupervisor(instructor);
        session.setCourseType(course);
        session.setLocation(location);
    }

    //--------------------------// Create Registration Tests //--------------------------//

    @Test
    public void testCreateValidRegistration() {
        when(customerRepository.findCustomerById(0)).thenReturn(customer);
        when(sessionRepository.findById(0)).thenReturn(session);

        Registration registration = new Registration(new Registration.Key(customer, session));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration createdRegistration = registrationService.createRegistration(customer, session);

        assertNotNull(createdRegistration);
        assertEquals(customer, createdRegistration.getKey().getCustomer());
        assertEquals(session, createdRegistration.getKey().getSession());
        verify(registrationRepository, times(1)).save(createdRegistration);
    }

    @Test
    public void testCreateDuplicateRegistration() {
        when(customerRepository.findCustomerById(0)).thenReturn(customer);
        when(sessionRepository.findById(0)).thenReturn(session);

        Registration registration = new Registration(new Registration.Key(customer, session));
        when(registrationRepository.findRegistrationByKey(any())).thenReturn(registration);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> registrationService.createRegistration(customer, session));
        assertEquals("Customer " + customer.getId() + " has already registered for session " + session.getId(), e.getMessage());
    }

    //--------------------------// Update Registration Tests //--------------------------//

    @Test
    public void updateValidRegistration() {
        when(customerRepository.findCustomerById(0)).thenReturn(customer);
        when(sessionRepository.findById(0)).thenReturn(session);

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

    //--------------------------// Cancel Registration Tests //--------------------------//

    @Test
    public void cancelValidRegistration() {
        when(customerRepository.findCustomerById(0)).thenReturn(customer);
        when(sessionRepository.findById(0)).thenReturn(session);

        Registration.Key key = new Registration.Key(customer, session);
        Registration registration = new Registration(key);
        when(registrationRepository.findRegistrationByKey(key)).thenReturn(registration);

        registrationService.deleteRegistration(registration.getKey());

        verify(registrationRepository, times(1)).delete(any(Registration.class));
    }

    @Test
    public void cancelInvalidRegistration() {
        when(customerRepository.findCustomerById(0)).thenReturn(customer);
        when(sessionRepository.findById(0)).thenReturn(session);

        Registration.Key key = new Registration.Key(customer, session);
        when(registrationRepository.findRegistrationByKey(key)).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> registrationService.deleteRegistration(key));
        assertEquals("There is no registration with key " + key + ".", e.getMessage());
    }

    //--------------------------// Find Registration Tests //--------------------------//

    @Test
    public void testReadRegistrationByValidKey() {
        when(customerRepository.findCustomerById(0)).thenReturn(customer);
        when(sessionRepository.findById(0)).thenReturn(session);

        Registration.Key key = new Registration.Key(customer, session);
        Registration registration = new Registration(key);
        when(registrationRepository.findRegistrationByKey(key)).thenReturn(registration);

        Registration foundRegistration = registrationService.findRegistrationByKey(key);

        assertNotNull(foundRegistration);
        assertEquals(registration.getKey().getCustomer(), foundRegistration.getKey().getCustomer());
        assertEquals(registration.getKey().getSession(), foundRegistration.getKey().getSession());
    }

    @Test
    public void testReadRegistrationByInvalidKey() {
        when(customerRepository.findCustomerById(0)).thenReturn(customer);
        when(sessionRepository.findById(0)).thenReturn(session);

        Registration.Key key = new Registration.Key(customer, session);
        when(registrationRepository.findRegistrationByKey(key)).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> registrationService.findRegistrationByKey(key));
        assertEquals("There is no registration with key " + key + ".", e.getMessage());
    }
}