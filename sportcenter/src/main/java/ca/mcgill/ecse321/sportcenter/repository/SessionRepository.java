package ca.mcgill.ecse321.sportcenter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Session;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    Session findById(int id);
    List<Session> findBySupervisor(Instructor supervisor);
    List<Session> findByCourseType(Course course);
}
