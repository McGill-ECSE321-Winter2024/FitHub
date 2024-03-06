package ca.mcgill.ecse321.sportcenter.controller;

import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Iterable<SportCenter> getAllSessions(){
        return sportCenterManagementService.getAllSportCenters();
    }

    // Creating a sport center
    @PostMapping("/sportCenters")
    public sportCenter createSportCenter(@RequestBody SportCenter sportCenter){
        return sportCenterManagementService.createSportCenter(sportCenter.getName(), sportCenter.getOpeningTime(), sportCenter.getClosingTime(), sportCenter.getAddress(), sportCenter.getEmail(), sportCenter.getPhoneNumber());
    }

    // updating opening/closing hours? is that how we do it?
    @PutMapping("/sportCenters/{id}")
    public SportCenter updateSportCenter(@RequestBody Time newOpeningTime, @RequestBody Time newClosingTime, @PathVariable int id){
        sportCenterManagementService.updateOpeningTime(id, newOpeningTime);
        return sportCenterManagementService.updateClosingTime(id, newClosingTime);
    }
}
