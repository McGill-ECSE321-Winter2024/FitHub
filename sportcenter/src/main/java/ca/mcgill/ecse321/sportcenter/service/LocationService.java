package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.model.Location;
//import ca.mcgill.ecse321.sportcenter.model.SportCenter;;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    //@Autowired
    //private SportCenter sportCenter;

    @Transactional
    public Location createLocation(String floor, String room) {
        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);
        //location.setCenter(sportCenter);
        locationRepository.save(location);
        return location;
    }

    @Transactional
    public Location findLocationById(int id) {
        Location location = locationRepository.findLocationById(id);

        if (location == null) {
            throw new IllegalArgumentException("There is no location with ID " + id + ".");
        }

        return location;
    }

    @Transactional
    public Location findLocationByFloorAndRoom(String floor, String room) {
        Location location = locationRepository.findLocationByFloorAndRoom(floor, room);

        if (location == null) {
            throw new IllegalArgumentException("There is no location with floor " + floor + 
                " or room " + room + ".");
        }

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
