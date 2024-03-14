package ca.mcgill.ecse321.sportcenter.controller;

import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.SportCenterDTO;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@RestController
public class SportCenterManagementController {
    
    @Autowired
    private SportCenterManagementService sportCenterManagementService;

    // Get sport center by id
    @GetMapping("/sportCenters/{id}")
    public SportCenter findSportCenterById(@PathVariable int id){
        return sportCenterManagementService.findSportCenterById(id);
    }

    // Get all sport centers
    @GetMapping("/sportCenters")
    public Iterable<SportCenter> getAllSportCenters(){
        return sportCenterManagementService.getAllSportCenters();
    }

    // Creating a sport center
    @PostMapping("/sportCenters")
    @ResponseStatus(HttpStatus.CREATED)
    public SportCenterDTO createSportCenter(@RequestBody SportCenterDTO sportCenter){
        SportCenter createdSportCenter = sportCenterManagementService.createSportCenter(sportCenter.getName(), sportCenter.getOpeningTime(), sportCenter.getClosingTime(), sportCenter.getAddress(), sportCenter.getEmail(), sportCenter.getPhoneNumber());
        return new SportCenterDTO();
    }

    // updating opening/closing hours? is that how we do it?
    @PutMapping("/sportCenters/{id}")
    public SportCenter updateSportCenter(@RequestBody SportCenterDTO sportCenter, @PathVariable int id){
        sportCenterManagementService.updateOpeningTime(id, sportCenter.getOpeningTime());
        return sportCenterManagementService.updateClosingTime(id, sportCenter.getClosingTime());
    }

    // Deleting a sport center
    @DeleteMapping(value={"/sportCenters/{id}", "/sportCenters/{id}/"})
    public void deleteSportCenter(@PathVariable int id) {
        sportCenterManagementService.deleteSportCenter(id);
    }
}
