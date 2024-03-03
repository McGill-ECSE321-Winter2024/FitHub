package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    Location findLocationById(int id);
    Location findByFloorAndRoom(String floor, String room);
}
