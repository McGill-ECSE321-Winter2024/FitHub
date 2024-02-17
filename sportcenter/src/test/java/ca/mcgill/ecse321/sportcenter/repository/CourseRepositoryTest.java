package ca.mcgill.ecse321.sportcenter.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;

@SpringBootTest
public class CourseRepositoryTest {
    
    @Autowired
    private CourseRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadCourse() {
        String name = "Cardio";
        String description = "Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope, squat jumps, lunges cycling and more.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;

        Course course = new Course(name, diff, status, description, SportCenter.getSportCenter());

        course = repo.save(course);
        int courseId = course.getId();

        Course courseFromDb = repo.findCourseById(courseId);

        //Assertions
        assertNotNull(courseFromDb);
        assertEquals(courseId, courseFromDb.getId());
        assertEquals(name, courseFromDb.getName());
        assertEquals(description, courseFromDb.getDescription());
        assertEquals(diff, courseFromDb.getDifficulty());
        assertEquals(status, courseFromDb.getStatus());

    }

}
