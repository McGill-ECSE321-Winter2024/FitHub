package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Course;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    Course findCourseById(int id);
}

