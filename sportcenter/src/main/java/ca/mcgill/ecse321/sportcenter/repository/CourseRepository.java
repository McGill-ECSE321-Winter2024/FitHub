package ca.mcgill.ecse321.sportcenter.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Course;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    Course findCourseById(int id);
    List<Course>  findCoursesByDifficulty(Course.Difficulty difficulty);
    List<Course>  findCoursesByStatus(Course.Status status);
    Course findCourseByName(String name);
    
}


