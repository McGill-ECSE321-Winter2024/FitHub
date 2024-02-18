package ca.mcgill.ecse321.sportcenter.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;

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
    private CourseRepository courseRepo;
    @Autowired
    private SportCenterRepository sportCenterRepo;

    private SportCenter sportCenter;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        courseRepo.deleteAll();
        sportCenterRepo.deleteAll();
    }

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

    @Test
    public void testCreateAndReadCourse() {
        String name = "Cardio";
        String description = "Your instructor will have your heart rate up while you move through a variety of different exercises like running, jump rope, squat jumps, lunges cycling and more.";
        Difficulty diff = Difficulty.Beginner;
        Status status = Status.Approved;

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDifficulty(diff);
        course.setStatus(status);
        
        course = courseRepo.save(course);

        Integer courseId = course.getId();

        Course courseFromDb = courseRepo.findCourseById(courseId);

        //Assertions
        assertNotNull(courseFromDb);
        assertEquals(courseId, courseFromDb.getId());
        assertEquals(name, courseFromDb.getName());
        assertEquals(description, courseFromDb.getDescription());
        assertEquals(diff, courseFromDb.getDifficulty());
        assertEquals(status, courseFromDb.getStatus());
    }

}
