package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;


@ExtendWith(MockitoExtension.class)

public class CourseServiceTests {
    @Mock
    private CourseRepository courseDao;

    @Mock
    private SportCenterRepository sportCenterRepo;

    @InjectMocks
    private CourseService service;
   
    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        courseDao.deleteAll();
    }

    private static final List<Course> COURSES = new ArrayList<>();

    /**
     * Create and save a SportCenter instance before each test.
     */
    @BeforeEach
    public void createAndSaveSportCenter() {

        Course course1 = new Course();
        course1.setName("Course 1");
        course1.setDescription("Description for Course 1");
        course1.setDifficulty(Course.Difficulty.Beginner);
        course1.setStatus(Course.Status.Closed);
        COURSES.add(course1); // Add course1 to COURSES list

        Course course3 = new Course();
        course3.setName("Course 3");
        course3.setDescription("Description for Course 3");
        course3.setDifficulty(Course.Difficulty.Beginner);
        course3.setStatus(Course.Status.Closed);
        COURSES.add(course3); // Add course3 to COURSES list

        Course course2 = new Course();
        course2.setName("Course 2");
        course2.setDescription("Description for Course 2");
        course2.setDifficulty(Course.Difficulty.Intermediate);
        course2.setStatus(Course.Status.Pending);
        COURSES.add(course2); // Add course2 to COURSES list

        
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

    //--------------------------// Create Course Tests //--------------------------//

    @Test
	public void testCreateCourse() {
		assertEquals(0, service.getAllCourses().size());

        String name = "a Name";
        String description = "a Description.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDifficulty(diff);
        course.setStatus(status);
        course.setCenter(sportCenterRepo.findSportCenterById(0));

        when(courseDao.save(any(Course.class))).thenReturn(course);

        Course createdCourse = service.createCourse(name, description, diff, status);

		assertNotNull(createdCourse);
		assertEquals(name, createdCourse.getName());
        assertEquals(description, createdCourse.getDescription());
        assertEquals(diff, createdCourse.getDifficulty());
        assertEquals(status, createdCourse.getStatus());
        verify(courseDao, times(1)).save(any(Course.class));
	}

	@Test
	public void testCreateCourseNameNull() {
		String name = null;
        String description = "a Description.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;
		String error = null;
		Course course = null;
        
		try {
			course = service.createCourse(name, description, diff, status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(course);
		// check error
		assertEquals("Course name cannot be empty!", error);
	}

    @Test
	public void testCreateCourseDescriptionNull() {
		String name = "a Name";
        String description = null;
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;
		String error = null;
		Course course = null;
        
		try {
			course = service.createCourse(name, description, diff, status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(course);
		// check error
		assertEquals("Course description cannot be empty!", error);
	}

	@Test
	public void testCreateCourseNameEmpty() {
		String name = "";
        String description = "a Description.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;
		String error = null;
		Course course = null;
        
		try {
			course = service.createCourse(name, description, diff, status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(course);
		// check error
		assertEquals("Course name cannot be empty!", error);
	}

    @Test
	public void testCreateCourseDescriptionEmpty() {
		String name = "a Name";
        String description = "";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;
		String error = null;
		Course course = null;
        
		try {
			course = service.createCourse(name, description, diff, status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(course);
		// check error
		assertEquals("Course description cannot be empty!", error);
	}

	@Test
	public void testCreateCourseSpaces() {
		String name = " ";
        String description = "a Description.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;
		String error = null;
		Course course = null;
        
		try {
			course = service.createCourse(name, description, diff, status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(course);
		// check error
		assertEquals("Course name cannot be empty!", error);
	}

    //--------------------------// Update Course Tests //--------------------------//

    @Test
    public void testUpdateValidCourse() {
        Integer id = 3;
        String name = "a Name";
        String description = "a Description.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDifficulty(diff);
        course.setStatus(status);
        course.setCenter(sportCenterRepo.findSportCenterById(0));

        when(courseDao.findCourseById(id)).thenReturn(course);

        String newName = "new Name";
        String newDescription = "new Description.";
        Difficulty newDiff = Difficulty.Advanced;
        Status newStatus = Status.Closed;

        Course updatedCourse = new Course();
        updatedCourse.setName(newName);
        updatedCourse.setDescription(newDescription);
        updatedCourse.setDifficulty(newDiff);
        updatedCourse.setStatus(newStatus);

        when(courseDao.save(any(Course.class))).thenReturn(updatedCourse);

        Course savedCourse = service.updateCourse(id, newName, newDescription, newDiff, newStatus);
    
        // Assert
        verify(courseDao, times(1)).findCourseById(id);
        verify(courseDao, times(1)).save(any(Course.class));
        assertNotNull(savedCourse);
        assertEquals(newName.toLowerCase(), savedCourse.getName());
        assertEquals(newDescription, savedCourse.getDescription());
        assertEquals(newDiff, savedCourse.getDifficulty());
        assertEquals(newStatus, savedCourse.getStatus());
    }

    @Test
    public void testUpdateInvalidCourse() {
        Integer id = 3;
        String name = "a Name";
        String description = "a Description.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDifficulty(diff);
        course.setStatus(status);
        course.setCenter(sportCenterRepo.findSportCenterById(0));

        String newName = "";
        String newDescription = "new Description.";
        Difficulty newDiff = Difficulty.Advanced;
        Status newStatus = Status.Closed;

        Course updatedCourse = new Course();
        updatedCourse.setName(newName);
        updatedCourse.setDescription(newDescription);
        updatedCourse.setDifficulty(newDiff);
        updatedCourse.setStatus(newStatus);

        String error = "";
        try {
			Course savedCourse = service.updateCourse(id, newName, newDescription, newDiff, newStatus);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Course name cannot be empty!", error);
        
    }


    //--------------------------// Find Course Tests //--------------------------//

    @Test
    public void testCourseOwnerByValidId() {
        // Set up test
        int id = 3;
        String name = "aName";
        String description = "a Description";
        Difficulty diff = Difficulty.Advanced;
        Status status = Status.Closed;
        
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDifficulty(diff);
        course.setStatus(status);
        course.setCenter(sportCenterRepo.findSportCenterById(0));

        when(courseDao.findCourseById(id)).thenReturn(course);

        // Use the CourseService
        Course foundCourse = service.findCourseById(id);
    
        // Assert
        assertNotNull(foundCourse);
		assertEquals(name, foundCourse.getName());
        assertEquals(description, foundCourse.getDescription());
        assertEquals(diff, foundCourse.getDifficulty());
        assertEquals(status, foundCourse.getStatus());
    }

    @Test
    public void testReadCourseByValidName() {
        // Set up test
        String name = "aName";
        String description = "a Description";
        Difficulty diff = Difficulty.Advanced;
        Status status = Status.Closed;
        
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDifficulty(diff);
        course.setStatus(status);
        course.setCenter(sportCenterRepo.findSportCenterById(0));

        when(courseDao.findCourseByName(name.toLowerCase())).thenReturn(course);

        // Use the CourseService
        Course foundCourse = service.findCourseByName(name);
    
        // Assert
        assertNotNull(foundCourse);
		assertEquals(name, foundCourse.getName());
        assertEquals(description, foundCourse.getDescription());
        assertEquals(diff, foundCourse.getDifficulty());
        assertEquals(status, foundCourse.getStatus());
    }

    @Test
    public void testReadCourseByInvalidId() {
        // Set up test
        int id = 64;

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.findCourseById(id));
        assertEquals("There is no course with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testReadCourseByInvalidName() {
        // Set up test
        String name = "x";

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.findCourseByName(name));
        assertEquals("There is no course with name " + name + ".", e.getMessage());
    }


    @Test
    public void testFindBeginnerCoursesByDifficulty() {

        when(courseDao.findAll()).thenReturn(COURSES);
        
        //courseRepository.findAll(), it should return a certain list of courses with some beginner courses and some non-beginner courses

        List<Course> courses = service.findCoursesByDifficulty(Course.Difficulty.Beginner);

        assertNotNull(courses);
        assertTrue(courses.size() > 0);

        for (Course course : courses) {
            assertEquals(Course.Difficulty.Beginner, course.getDifficulty());
        }
}

@Test
public void testFindIntermediateCoursesByDifficulty() {
    // Create a list of courses with intermediate difficulty
    List<Course> intermediateCourses = COURSES.stream()
            .filter(course -> course.getDifficulty() == Course.Difficulty.Intermediate)
            .collect(Collectors.toList());

    // Mock the service method to return the list of intermediate courses
    when(service.findCoursesByDifficulty(Course.Difficulty.Intermediate)).thenReturn(intermediateCourses);

    // Call the service method
    List<Course> intermediate = service.findCoursesByDifficulty(Course.Difficulty.Intermediate);

    // Assert that the returned list contains exactly one intermediate course
    assertTrue(intermediate.size() == 1);
}


}
