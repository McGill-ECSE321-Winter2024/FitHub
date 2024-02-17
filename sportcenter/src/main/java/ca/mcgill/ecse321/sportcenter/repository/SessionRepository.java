package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Session;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    Session findSessionById(int id);
}
