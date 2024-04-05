package ca.mcgill.ecse321.sportcenter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.SessionPackage;

public interface SessionPackageRepository extends CrudRepository<SessionPackage, Integer>{
    SessionPackage findSessionPackageById(int id);
    List<SessionPackage> findSessionPackageByCourse(Course course);
    
}
