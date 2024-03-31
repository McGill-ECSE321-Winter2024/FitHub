package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    Instructor findInstructorById(int id);
    Instructor findInstructorByEmail(String email);
}
