package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.service.LocationService;
import ca.mcgill.ecse321.sportcenter.dto.LocationRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationListDTO;

@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    //--------------------------// Create Location //--------------------------//

     @PostMapping(value = {"/locations", "/locations/"})
     public ResponseEntity<LocationResponseDTO> createLocation(@RequestBody LocationRequestDTO location) {
        try { 
            Location createdLocation = locationService.createLocation(location.getFloor(), location.getRoom());
            return new ResponseEntity<LocationResponseDTO>(new LocationResponseDTO(createdLocation), HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<LocationResponseDTO>(new LocationResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Update Location //--------------------------//

    @PutMapping(value = {"/locations/{id}", "/locations/{id}/"})
    public ResponseEntity<LocationResponseDTO> updateLocation(@PathVariable Integer id, @RequestBody LocationRequestDTO location) {
        try {
            Location updatedLocation = locationService.updateLocation(id, location.getFloor(), location.getRoom());
            return new ResponseEntity<LocationResponseDTO>(new LocationResponseDTO(updatedLocation), HttpStatus.ACCEPTED);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<LocationResponseDTO>(new LocationResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Read Location //--------------------------//

    @GetMapping(value = {"/locations/{id}", "/locations/{id}/"})
    public ResponseEntity<LocationResponseDTO> getLocation(@PathVariable Integer id) {
        try{
            Location foundLocation = locationService.findLocationById(id);
            return new ResponseEntity<LocationResponseDTO>(new LocationResponseDTO(foundLocation), HttpStatus.FOUND);
        }
        catch(IllegalArgumentException e) {
            return new ResponseEntity<LocationResponseDTO>(new LocationResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = {"/public/locations", "/public/locations/"})
    public ResponseEntity<LocationListDTO> getAllLocations() {
        List<LocationResponseDTO> locations = new ArrayList<>();
        for (Location location: locationService.getAllLocations()) {
            locations.add(new LocationResponseDTO(location));
        }

        LocationListDTO locationList = new LocationListDTO(locations);
        if (locationList.getLocations().size() > 0) {
            return new ResponseEntity<LocationListDTO>(locationList, HttpStatus.OK);
        } else {
            return new ResponseEntity<LocationListDTO>(locationList, HttpStatus.NO_CONTENT);
        }
    }

    //--------------------------// Delete Location //--------------------------//

    @DeleteMapping(value = {"/locations/{id}", "/locations/{id}/"})
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
        try{
            boolean deletionSuccessful = locationService.deleteLocation(id);
            if (deletionSuccessful) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = {"/locations", "/locations/"})
    public ResponseEntity<Void> deleteLocationByFloorAndRoom(@RequestBody LocationRequestDTO location) {
        try{
            boolean deletionSuccessful = locationService.deleteLocationByFloorAndRoom(location.getFloor(),location.getRoom());
            if (deletionSuccessful) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}