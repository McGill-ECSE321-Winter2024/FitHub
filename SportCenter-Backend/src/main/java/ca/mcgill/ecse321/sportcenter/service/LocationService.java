package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

/*
* <p>Service class in charge of managing locations. It implements following use cases: </p>
* <p>Create, update, delete a location.</p>
* @author Tayba
*/
@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SportCenterRepository sportCenterRepository;

    @Autowired
    private SportCenterManagementService sportCenterManagementService;

    //--------------------------// Create Location //--------------------------//

    @Transactional
    public Location createLocation(String floor, String room) {
        validLocationInfo(floor, room);
        uniqueFloorAndRoom(floor, room);

        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);
        location.setCenter(toList(sportCenterRepository.findAll()).get(0));
        locationRepository.save(location);
        return location;
    }

    //--------------------------// Update Location //--------------------------//
    
    @Transactional
    public Location updateLocation(Integer id, String floor, String room) {
        validLocationInfo(floor, room);

        Location location = findLocationById(id);
        location.setFloor(floor);
        location.setRoom(room);
        location.setCenter(toList(sportCenterRepository.findAll()).get(0));
        locationRepository.save(location);
        return location;
    }

    //--------------------------// Delete Location //--------------------------//
    
    @Transactional
    public boolean deleteLocation(Integer id) {
        Location location = locationRepository.findLocationById(id);
        if (location == null) {
            return false;
        }
        SportCenter sportCenter = sportCenterManagementService.getSportCenter();
        sportCenter.removeLocation(location);
        sportCenterManagementService.updateSportCenter(sportCenter);
        return true;
    }

    @Transactional
    public boolean deleteLocationByFloorAndRoom(String floor, String room){
        Location location = locationRepository.findLocationByFloorAndRoom(floor,room);
        if (location == null) {
            return false;
        }
        SportCenter sportCenter = sportCenterManagementService.getSportCenter();
        sportCenter.removeLocation(location);
        sportCenterManagementService.updateSportCenter(sportCenter);
        return true;
    }

    //--------------------------// Getters //--------------------------//

    @Transactional
    public Location findLocationById(Integer id) {
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

    //--------------------------// Helper functions //--------------------------//

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    //--------------------------// Input validations //--------------------------//

    private boolean isValidInteger(String strNum) {
        if (strNum == null) {
            return false;
        }

        int num;
        try {
            num = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }

        // Check if the number is negative
        if (num < 0) {
            return false;
        }

        return true;
    }

    private void validLocationInfo(String floor, String room) {
        if (!isValidInteger(floor) || !isValidInteger(room)) {
            throw new IllegalArgumentException("The floor or room number is invalid.");
        }
    } 

    private void uniqueFloorAndRoom(String floor, String room) {
        if (locationRepository.findLocationByFloorAndRoom(floor, room) != null) {
            throw new IllegalArgumentException("The location already exists.");
        }
    }
}