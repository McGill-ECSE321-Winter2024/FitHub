package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;



@SpringBootTest
public class SessionServiceTests {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private InstructorRepository supervisorRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private SportCenterRepository sportCenterRepo;

    @InjectMocks
    private SessionService sessionService;
    
    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        sessionRepository.deleteAll();
        supervisorRepository.deleteAll();
        locationRepository.deleteAll();
        courseRepository.deleteAll();
        sportCenterRepo.deleteAll();
    }

    /**
     * Create and save a SportCenter instance before each test.
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

    
    //--------------------------// Create Account Tests //--------------------------//

    @Test
    public void testCreateValidSession() {
        int id = 50;
        // Set up test
        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

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
        when(sessionRepository.save(any(Session.class))).thenReturn(aSession);

        // Act
        Session createdSession = sessionService.proposeSuperviseSession(startTime, endTime, date, capacity, id, id, id);
    
        // Assert
        assertNotNull(createdSession);
        
        assertNotNull(createdSession);
        assertEquals(startTime.toString(), createdSession.getStartTime().toString());
        assertEquals(endTime.toString(), createdSession.getEndTime().toString());
        assertEquals(capacity, createdSession.getCapacity());

        //making sure the other objects match.
        assertNotNull(createdSession.getSupervisor());
        assertEquals(instructor.getId(), createdSession.getSupervisor().getId());

        //Assert that the information in the course association match. 
        assertNotNull(createdSession.getCourseType());
        assertEquals(aCourseType.getId(), createdSession.getCourseType().getId());

        //Assert that the information in the location association match.
        assertNotNull(createdSession.getLocation());
        assertEquals(location.getId(), createdSession.getLocation().getId()); 

        verify(sessionRepository, times(1)).save(any(Session.class));
    }
    
    //---------------
    @Test
    public void testCreateInvalidSession1() { // Capacity of zero
        int id=50;
        // Set up test
        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);


        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("09:00:00");
        Date date = Date.valueOf("2024-02-18");
        Integer capacity = 0;

        // Use Account Service and Assert
        assertThrows(IllegalArgumentException.class, () -> sessionService.proposeSuperviseSession(startTime, endTime, date, capacity, instructor.getId(), aCourseType.getId(), location.getId()));
    }

    @Test
    public void testCreateInvalidSession2() { // No start time
        int id = 50;
        // Set up test
        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

        Time startTime = null;
        Time endTime = Time.valueOf("09:00:00");
        Date date = Date.valueOf("2024-02-18");
        Integer capacity = 10;


        // Use Account Service and Assert
        assertThrows(IllegalArgumentException.class, () -> sessionService.proposeSuperviseSession(startTime, endTime, date, capacity, instructor.getId(), aCourseType.getId(), location.getId()));;
    }

    @Test
    public void testCreateInvalidSession3() { // No date
        int id = 50;
        // Set up test
        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

        Time startTime =  Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("09:00:00");
        Date date = null;
        Integer capacity = 10;

        // Use Account Service and Assert
        assertThrows(IllegalArgumentException.class, () -> sessionService.proposeSuperviseSession(startTime, endTime, date, capacity, instructor.getId(), aCourseType.getId(), location.getId()));;
    }

    @Test
    public void testCreateInvalidSession4() { // No supervisor
        int id = 50;
        // Set up test
        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);
        
        int nonExistentSupervisorId = 4;
        when(supervisorRepository.findInstructorById(nonExistentSupervisorId)).thenReturn(null);
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

        Time startTime =  Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("09:00:00");
        Date date = Date.valueOf("2024-02-18");
        Integer capacity = 10;

        // Use Session Service and Assert
        assertThrows(IllegalArgumentException.class, () -> sessionService.proposeSuperviseSession(startTime, endTime, date, capacity, nonExistentSupervisorId, aCourseType.getId(), location.getId()));
    }

    @Test
    public void testCreateInvalidSession5() { // No courseType
        int id = 50;
        // Set up test
        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);
        
        int nonExistentCourseId = 4;
        when(courseRepository.findById(nonExistentCourseId)).thenReturn(null);

        //Create and save the supervisor
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);

        Time startTime =  Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("09:00:00");
        Date date = Date.valueOf("2024-02-18");
        Integer capacity = 10;

        // Use Session Service and Assert
        assertThrows(IllegalArgumentException.class, () -> sessionService.proposeSuperviseSession(startTime, endTime, date, capacity, instructor.getId(), nonExistentCourseId, location.getId()));
    }
    
    //To implement
    @Test
    public void testCreateInvalidOverlappedLocationSession() { // Session location has conflicting time with another session on the same location, same day.
        int id = 50;
        // Set up test
        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

        Time startTime1 = Time.valueOf("08:00:00");
        Time endTime1 = Time.valueOf("09:00:00");
        Date date = Date.valueOf("2024-02-18");
        Integer capacity = 10;

               
        Time startTime2 = Time.valueOf("08:30:00");
        Time endTime2 = Time.valueOf("09:30:00");

        Session session1 = new Session();
        session1.setStartTime(startTime1);
        session1.setEndTime(endTime1);
        session1.setDate(date);
        session1.setCapacity(capacity);
        session1.setSupervisor(instructor);
        session1.setCourseType(aCourseType);
        session1.setLocation(location);

        Session session2 = new Session();
        session2.setStartTime(startTime2);
        session2.setEndTime(endTime2);
        session2.setDate(date);
        session2.setCapacity(capacity);
        session2.setSupervisor(instructor);
        session2.setCourseType(aCourseType);
        session2.setLocation(location);

        List<Session> sessions = new ArrayList<Session>();
        sessions.add(session1);
        sessions.add(session2);

        when(sessionRepository.findByLocationAndDate(location,date)).thenReturn(sessions);

        // Use Account Service and Assert 

        assertThrows(IllegalArgumentException.class, () -> sessionService.proposeSuperviseSession(startTime2, endTime2, date, capacity, instructor.getId(), aCourseType.getId(), location.getId()));
    }

     //To implement
     @Test
     public void testCreateInvalidOverlappedSupervisorSession() { // Supervisor already supervise a session that overlap with that time 
         // Set up test
        int id = 50;
        Location location1 = new Location();
        location1.setFloor("501D");
        location1.setRoom("50");
        location1.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location1);

        Location location2 = new Location();
        location2.setFloor("501D");
        location2.setRoom("50");
        location2.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location2);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

        Time startTime1 = Time.valueOf("08:00:00");
        Time endTime1 = Time.valueOf("09:00:00");
        Date date = Date.valueOf("2024-02-18");
        Integer capacity = 10;

        Time startTime2 = Time.valueOf("08:30:00");
        Time endTime2 = Time.valueOf("09:30:00");


        Session session1 = new Session();
        session1.setStartTime(startTime1);
        session1.setEndTime(endTime1);
        session1.setDate(date);
        session1.setCapacity(capacity);
        session1.setSupervisor(instructor);
        session1.setCourseType(aCourseType);
        session1.setLocation(location1);

        Session session2 = new Session();
        session2.setStartTime(startTime2);
        session2.setEndTime(endTime2);
        session2.setDate(date);
        session2.setCapacity(capacity);
        session2.setSupervisor(instructor);
        session2.setCourseType(aCourseType);
        session2.setLocation(location2);

        List<Session> sessions = new ArrayList<Session>();
        sessions.add(session1);
        sessions.add(session2);

        when(sessionRepository.findBySupervisorAndDate(instructor, date)).thenReturn(sessions);

        // Use Account Service and Assert
        //sessionService.proposeSuperviseSession(startTime1, endTime1, date, capacity, instructor.getId(), aCourseType.getId(), location1.getId());
        
        
        

        assertThrows(IllegalArgumentException.class, () -> sessionService.proposeSuperviseSession(startTime2, endTime2, date, capacity, instructor.getId(), aCourseType.getId(), location2.getId()));
     }

    //--------------------------// Update Account Tests //--------------------------//

    @Test
    public void testUpdateValidSession() {
        
        // Set up test
        int id = 64;

        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

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
        aSession.setId(64);

        when(sessionRepository.findById(id)).thenReturn(aSession);

        // Use the SessionService
        Location newLocation = new Location();
        newLocation.setFloor("501D");
        newLocation.setRoom("50");
        newLocation.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        // Create and save the instructor 
        Instructor newInstructor = new Instructor();
        newInstructor.setEmail("Jumijabasali@fithub.com");
        newInstructor.setPassword("sportcenter");
        newInstructor.setName("Sahar");
        newInstructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course newCourseType = new Course();
        newCourseType.setName("Kung Fu I");
        newCourseType.setDescription("Martial art beginner course");
        newCourseType.setDifficulty(Difficulty.Beginner);
        newCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

        Time newStartTime = Time.valueOf("08:00:00");
        Time newEndTime = Time.valueOf("09:00:00");
        Date newDate = Date.valueOf("2024-02-18");
        Integer newCapacity = 10;


        Session updatedSession = new Session();
        updatedSession.setStartTime(startTime);
        updatedSession.setEndTime(endTime);
        updatedSession.setDate(date);
        updatedSession.setCapacity(capacity);
        updatedSession.setSupervisor(instructor);
        updatedSession.setCourseType(aCourseType);
        updatedSession.setLocation(location);
        updatedSession.setId(64);

        when(sessionRepository.findById(id)).thenReturn(updatedSession);

        Session savedSession = sessionService.updateSession(id, newStartTime, newEndTime, newDate, newCapacity, newInstructor.getId(), newCourseType.getId(), newLocation.getId());
    
        // Assert
        verify(sessionRepository, times(1)).findById(id);
        verify(sessionRepository, times(1)).save(any(Session.class));
        
        // Assert
        assertNotNull(savedSession);
        
        assertNotNull(savedSession);
        assertEquals(startTime.toString(), savedSession.getStartTime().toString());
        assertEquals(endTime.toString(), savedSession.getEndTime().toString());
        assertEquals(capacity, savedSession.getCapacity());

        //making sure the other objects match.
        assertNotNull(savedSession.getSupervisor());
        assertEquals(instructor.getId(), savedSession.getSupervisor().getId());

        //Assert that the information in the course association match. 
        assertNotNull(savedSession.getCourseType());
        assertEquals(aCourseType.getId(), savedSession.getCourseType().getId());

        //Assert that the information in the location association match.
        assertNotNull(savedSession.getLocation());
        assertEquals(location.getId(), savedSession.getLocation().getId()); 
    }

    //--------------------------// Find Session Tests //--------------------------//

    @Test
    public void testReadSessionByValidId() {
        // Set up test
        int id = 64;

        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

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
        aSession.setId(64);

        when(sessionRepository.findById(id)).thenReturn(aSession);

        // Use the SessionService
        Session foundSession = sessionService.findSessionById(id);
    
        // Assert
        assertNotNull(foundSession);

        assertEquals(startTime.toString(), foundSession.getStartTime().toString());
        assertEquals(endTime.toString(), foundSession.getEndTime().toString());
        assertEquals(capacity, foundSession.getCapacity());

        //making sure the other objects match.
        assertNotNull(foundSession.getSupervisor());
        assertEquals(instructor.getId(), foundSession.getSupervisor().getId());

        //Assert that the information in the course association match. 
        assertNotNull(foundSession.getCourseType());
        assertEquals(aCourseType.getId(), foundSession.getCourseType().getId());

        //Assert that the information in the location association match.
        assertNotNull(foundSession.getLocation());
        assertEquals(location.getId(), foundSession.getLocation().getId()); 
    }

    
    @Test
    public void testReadSessionByInvalidId() {
        // Set up test
        int id = 64;

        when(sessionRepository.findById(id)).thenReturn(null);

        // Use the SessionService and Assert
        assertThrows(IllegalArgumentException.class, () -> sessionService.findSessionById(id));
       
    }
    
    @Test
    public void testReadSessionByValidInstructor() {
        // Set up test
        int id = 64;

        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);
        
        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

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
        aSession.setId(64);

        List<Session> sessionList = new ArrayList<Session>();
        sessionList.add(aSession);

        when(sessionRepository.findBySupervisor(instructor)).thenReturn(sessionList);

        // Use the SessionService
        List<Session> foundSessionList = sessionService.findSessionsByInstructor(instructor);
        Session foundSession = foundSessionList.get(0);

        // Assert
        assertNotNull(foundSession);

        assertEquals(startTime.toString(), foundSession.getStartTime().toString());
        assertEquals(endTime.toString(), foundSession.getEndTime().toString());
        assertEquals(capacity, foundSession.getCapacity());

        //making sure the other objects match.
        assertNotNull(foundSession.getSupervisor());
        assertEquals(instructor.getId(), foundSession.getSupervisor().getId());

        //Assert that the information in the course association match. 
        assertNotNull(foundSession.getCourseType());
        assertEquals(aCourseType.getId(), foundSession.getCourseType().getId());

        //Assert that the information in the location association match.
        assertNotNull(foundSession.getLocation());
        assertEquals(location.getId(), foundSession.getLocation().getId()); 
    }
    

    
    @Test
    public void testReadSessionByInvalidInstructor() {
        int id = 50;
        // Set up test
        Instructor instructor = null;

        //when(sessionRepository.findBySupervisor(instructor)).thenReturn(null);

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sessionService.findSessionsByInstructor(instructor));
        
    }

    @Test
    public void testReadSessionByValidCourse() {
        // Set up test
        int id = 64;

        Location location = new Location();
        location.setFloor("501D");
        location.setRoom("50");
        location.setCenter(sportCenterRepo.findSportCenterById(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);
    
        // Create and save the instructor 
        Instructor instructor = new Instructor();
        instructor.setEmail("Jumijabasali@fithub.com");
        instructor.setPassword("sportcenter");
        instructor.setName("Sahar");
        instructor.setImageURL("pfp.com");
        when(supervisorRepository.findInstructorById(id)).thenReturn(instructor);

        // Create and save the course
        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        when(courseRepository.findCourseById(id)).thenReturn(aCourseType);

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
        aSession.setId(64);

        List<Session> sessionList = new ArrayList<Session>();
        sessionList.add(aSession);

        when(sessionRepository.findByCourseType(aCourseType)).thenReturn(sessionList);

        // Use the SessionService
        List<Session> foundSessionList = sessionService.findSessionsByCourse(aCourseType);
        Session foundSession = foundSessionList.get(0);
        
        // Assert
        assertNotNull(foundSession);

        assertEquals(startTime.toString(), foundSession.getStartTime().toString());
        assertEquals(endTime.toString(), foundSession.getEndTime().toString());
        assertEquals(capacity, foundSession.getCapacity());

        //making sure the other objects match.
        assertNotNull(foundSession.getSupervisor());
        assertEquals(instructor.getId(), foundSession.getSupervisor().getId());

        //Assert that the information in the course association match. 
        assertNotNull(foundSession.getCourseType());
        assertEquals(aCourseType.getId(), foundSession.getCourseType().getId());

        //Assert that the information in the location association match.
        assertNotNull(foundSession.getLocation());
        assertEquals(location.getId(), foundSession.getLocation().getId()); 
    }
    
    @Test
    public void testReadSessionByInvalidCourse(){

        int id = 50;
        Course aCourseType = null;
       

        when(sessionRepository.findByCourseType(aCourseType)).thenReturn(null);

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sessionService.findSessionsByCourse(aCourseType));
        
    }
    

}
  

