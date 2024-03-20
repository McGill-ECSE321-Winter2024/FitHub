package ca.mcgill.ecse321.sportcenter.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationDTO;
import ca.mcgill.ecse321.sportcenter.dto.SportCenterDTO;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@RestController
public class SportCenterManagementController {
    
    @Autowired
    private SportCenterManagementService sportCenterManagementService;

    // Creating a sport center
    @PostMapping("/sportCenter")
    @ResponseStatus(HttpStatus.CREATED)
    public SportCenterDTO createSportCenter(@RequestBody SportCenterDTO sportCenter){
        // Create empty lists
        List<CourseDTO> courses = new ArrayList<>();
        List<LocationDTO> locations = new ArrayList<>();
        List<Account> centerAccounts = new ArrayList<>();
        List<AccountResponseDTO> accountResponseList = AccountListDTO.accountListToAccountResponseDTOList(centerAccounts);
        AccountListDTO accounts = new AccountListDTO(accountResponseList);

        SportCenter createdSportCenter = sportCenterManagementService.createSportCenter(sportCenter.getName(), sportCenter.getOpeningTime(), sportCenter.getClosingTime(), sportCenter.getAddress(), sportCenter.getEmail(), sportCenter.getPhoneNumber());

        return new SportCenterDTO(createdSportCenter.getName(), createdSportCenter.getOpeningTime(), createdSportCenter.getClosingTime(), createdSportCenter.getAddress(), createdSportCenter.getEmail(), createdSportCenter.getPhoneNumber(), courses, locations, accounts);
    }

    // updating opening/closing hours? is that how we do it?
    @PutMapping("/sportCenter")
    public SportCenterDTO updateSportCenter(@RequestBody SportCenterDTO newSportCenter){
        SportCenter updatedSportCenter = sportCenterManagementService.updateSportCenter(newSportCenter.getOpeningTime(), newSportCenter.getClosingTime(), newSportCenter.getAddress());
        
        // Convert list of Course into list of CourseDTO
        List<CourseDTO> courses = new ArrayList<>();
        CourseDTO courseDTO;
        for(Course course : updatedSportCenter.getCourses()) {
            courseDTO = new CourseDTO(course);
            courses.add(courseDTO);
        }

        // Convert list of Location into list of LocationDTO
        List<LocationDTO> locations = new ArrayList<>();
        LocationDTO locationDTO;
        for(Location location : updatedSportCenter.getLocations()) {
            locationDTO = new LocationDTO(location.getFloor(), location.getRoom());
            locations.add(locationDTO);
        }

        // Convert list of Account into AccountListDTO
        List<AccountResponseDTO> accountResponseList = AccountListDTO.accountListToAccountResponseDTOList(updatedSportCenter.getAccounts());
        AccountListDTO accounts = new AccountListDTO(accountResponseList);

        return new SportCenterDTO(updatedSportCenter.getName(), updatedSportCenter.getOpeningTime(), updatedSportCenter.getClosingTime(), updatedSportCenter.getAddress(), updatedSportCenter.getEmail(), updatedSportCenter.getPhoneNumber(), courses, locations, accounts);
    }

    // Deleting a sport center
    @DeleteMapping("/sportCenter")
    public void deleteSportCenter() {
        sportCenterManagementService.deleteSportCenter();
    }

}
