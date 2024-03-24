package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.RegistrationResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.service.LocationService;
import ca.mcgill.ecse321.sportcenter.dto.LocationRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationListDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationListDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    //--------------------------// Create Location //--------------------------//

    // this commented method gives me error 500 for some reason (although its valid floor and valid room)

    // @PostMapping(value = {"/locations", "/locations/"})
    // public ResponseEntity<LocationResponseDTO> createLocation(@RequestBody LocationRequestDTO location) {
    //     Location createdLocation = locationService.createLocation(location.getFloor(), location.getRoom());
    //     return new ResponseEntity<>(new LocationResponseDTO(createdLocation), HttpStatus.CREATED);
    // }

        // this one works
    @PostMapping(value = {"/locations", "/locations/"})
    public ResponseEntity<LocationResponseDTO> createLocation(@RequestParam String floor, @RequestParam String room) {
        Location createdLocation = locationService.createLocation(floor, room);
        return new ResponseEntity<>(new LocationResponseDTO(createdLocation), HttpStatus.CREATED);
    }

    //--------------------------// Update Location //--------------------------//

    @PutMapping(value = {"/locations/{id}", "/locations/{id}/"})
    public ResponseEntity<LocationResponseDTO> updateLocation(@RequestBody LocationRequestDTO location, @PathVariable Integer id) {
        Location updatedLocation = locationService.updateLocation(id, location.getFloor(), location.getRoom());
        return new ResponseEntity<>(new LocationResponseDTO(updatedLocation), HttpStatus.ACCEPTED);
    }

    //--------------------------// Read Location //--------------------------//

    @GetMapping(value = {"/locations/{id}", "/locations/{id}/"})
    public ResponseEntity<LocationResponseDTO> getLocation(@PathVariable Integer id) {
        Location foundLocation = locationService.findLocationById(id);
        return new ResponseEntity<>(new LocationResponseDTO(foundLocation), HttpStatus.FOUND);
    }

    @GetMapping(value = {"/locations", "/locations/"})
    public ResponseEntity<LocationListDTO> getAllLocations() {
        List<LocationResponseDTO> locations = new ArrayList<>();
        for (Location location: locationService.getAllLocations()) {
            locations.add(new LocationResponseDTO(location));
        }

        LocationListDTO locationList = new LocationListDTO(locations);
        if (locationList.getLocations().size() > 0) {
            return new ResponseEntity<>(locationList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(locationList, HttpStatus.NO_CONTENT);
        }
    }

    //--------------------------// Delete Location //--------------------------//

    @DeleteMapping(value = {"/locations/{id}", "/locations/{id}/"})
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
        boolean deletionSuccessful = locationService.deleteLocation(id);
        if (deletionSuccessful) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}