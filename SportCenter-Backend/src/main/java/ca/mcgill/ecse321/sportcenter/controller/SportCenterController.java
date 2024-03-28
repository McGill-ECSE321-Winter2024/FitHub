package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.SportCenterDTO;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

/**
 * <p>Controller class in charge of managing the sport center. It implements following use cases: </p>
 * <p>Create, update, read and delete the sport center </p>
 * @author James
*/
@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class SportCenterController {
    
    @Autowired
    private SportCenterManagementService sportCenterManagementService;

    @GetMapping("/sport-center")
    public ResponseEntity<SportCenterDTO> getSportCenter() {
        return new ResponseEntity<SportCenterDTO>(new SportCenterDTO(sportCenterManagementService.getSportCenter()), HttpStatus.ACCEPTED);
    }

    // Since there is only a unique sportCenter, there is no POSTMapping (We do not want to risk the creation of more than one SportCenter)
    // Create
    @PostMapping("/sport-center")
    public ResponseEntity<SportCenterDTO> createSportCenter(@RequestBody SportCenterDTO newSportCenterDTO){
        try {
            SportCenter sportCenter = sportCenterManagementService.createSportCenter(
                newSportCenterDTO.getName(), newSportCenterDTO.getOpeningTime(), newSportCenterDTO.getClosingTime(), 
                newSportCenterDTO.getAddress(), newSportCenterDTO.getEmail(), newSportCenterDTO.getPhoneNumber());
            return new ResponseEntity<SportCenterDTO>(new SportCenterDTO(sportCenter), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<SportCenterDTO>(new SportCenterDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // updating sportCenter
    @PutMapping("/sport-center")
    public ResponseEntity<SportCenterDTO> updateSportCenter(@RequestBody SportCenterDTO newSportCenterDTO){
        try {
            SportCenter sportCenter = new SportCenter(newSportCenterDTO.getName(), newSportCenterDTO.getOpeningTime(), 
                newSportCenterDTO.getClosingTime(), newSportCenterDTO.getAddress(), 
                newSportCenterDTO.getEmail(), newSportCenterDTO.getPhoneNumber());

            sportCenter = sportCenterManagementService.updateSportCenter(sportCenter);
            return new ResponseEntity<SportCenterDTO>(new SportCenterDTO(sportCenter), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<SportCenterDTO>(new SportCenterDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Deleting a sport center
    @DeleteMapping("/sport-center")
    public ResponseEntity<Void> deleteSportCenter() {
        try {
            sportCenterManagementService.deleteSportCenter();
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}