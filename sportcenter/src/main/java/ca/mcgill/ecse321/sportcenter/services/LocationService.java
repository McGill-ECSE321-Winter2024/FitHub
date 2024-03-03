package ca.mcgill.ecse321.sportcenter.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.model.Location;;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Transactional
    public Location createLocation(String floor, String room) {
        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);
        locationRepository.save(location);
        return location;
    }

    @Transactional
    public Location getLocation(String floor, String room) {
        Location location = locationRepository.findByFloorAndRoom(floor, room);
        return location;
    }

    @Transactional
    public List<Location> getAllLocations() {
        return toList(locationRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
