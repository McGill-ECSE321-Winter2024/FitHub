package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;

/**
 * This class provides test cases for the CourseRepository class.
 * It verifies the functionalities related to creating and reading courses.
 */
@SpringBootTest
public class CourseRepositoryTests extends CommonTestSetup {
    @Autowired
    private CourseRepository courseRepo;
    /**
     * Test case to verify the creation and reading of a course.
     */
    @Test
    public void testCreateAndReadCourse() {

        //create a course
        String name = "Cardio";
        String description = "Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope, squat jumps, lunges cycling and more.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDifficulty(diff);
        course.setStatus(status);
        course.setCenter(sportCenter);
        
        //save into database
        course = courseRepo.save(course);
        Integer courseId = course.getId();
        
        //read from database
        Course courseFromDb = courseRepo.findCourseById(course.getId());

        //Assertions to check if course exists and has the appropriate attributes
        assertNotNull(courseFromDb);
        assertEquals(courseId, courseFromDb.getId());
        assertEquals(name, courseFromDb.getName());
        assertEquals(description, courseFromDb.getDescription());
        assertEquals(diff, courseFromDb.getDifficulty());
        assertEquals(status, courseFromDb.getStatus());
    }

}