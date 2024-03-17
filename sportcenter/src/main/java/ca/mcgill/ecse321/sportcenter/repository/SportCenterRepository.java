package ca.mcgill.ecse321.sportcenter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;

public interface SportCenterRepository extends CrudRepository<SportCenter, Integer> {
    SportCenter findSportCenterById(int id);
    List<SportCenter> findAll();
}
