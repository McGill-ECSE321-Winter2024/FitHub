package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseListDTO;
import ca.mcgill.ecse321.sportcenter.dto.LocationDTO;
import ca.mcgill.ecse321.sportcenter.dto.SportCenterDTO;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@RestController
public class SportCenterController {
    
    @Autowired
    private SportCenterManagementService sportCenterManagementService;

    // Creating a sport center
    @PostMapping("/sport-center")
    public ResponseEntity<SportCenterDTO> createSportCenter(@RequestBody SportCenterDTO sportCenter){
        // Create empty lists
        List<LocationDTO> locations = new ArrayList<>();

        List<Course> centerCourses = new ArrayList<>();
        List<CourseResponseDTO> courseResponseList = CourseListDTO.courseListToCourseResponseDTOList(centerCourses);
        CourseListDTO courses = new CourseListDTO(courseResponseList);

        List<Account> centerAccounts = new ArrayList<>();
        List<AccountResponseDTO> accountResponseList = AccountListDTO.accountListToAccountResponseDTOList(centerAccounts);
        AccountListDTO accounts = new AccountListDTO(accountResponseList);

        SportCenter createdSportCenter = sportCenterManagementService.createSportCenter(sportCenter.getName(), sportCenter.getOpeningTime(), sportCenter.getClosingTime(), sportCenter.getAddress(), sportCenter.getEmail(), sportCenter.getPhoneNumber());

        return new ResponseEntity<SportCenterDTO>(new SportCenterDTO(createdSportCenter.getName(), createdSportCenter.getOpeningTime(), createdSportCenter.getClosingTime(), createdSportCenter.getAddress(), createdSportCenter.getEmail(), createdSportCenter.getPhoneNumber(), courses, locations, accounts), HttpStatus.CREATED);
    }

    // updating opening/closing hours, address
    @PutMapping("/sport-center")
    public ResponseEntity<SportCenterDTO> updateSportCenter(@RequestBody SportCenterDTO newSportCenter){
        SportCenter updatedSportCenter = sportCenterManagementService.updateSportCenter(newSportCenter.getOpeningTime(), newSportCenter.getClosingTime(), newSportCenter.getAddress());

        // Convert list of Location into list of LocationDTO
        List<LocationDTO> locations = new ArrayList<>();
        LocationDTO locationDTO;
        for(Location location : updatedSportCenter.getLocations()) {
            locationDTO = new LocationDTO(location.getFloor(), location.getRoom());
            locations.add(locationDTO);
        }
        
        // Convert list of Course into list of CourseDTO
        List<CourseResponseDTO> courseResponseList = CourseListDTO.courseListToCourseResponseDTOList(updatedSportCenter.getCourses());
        CourseListDTO courses = new CourseListDTO(courseResponseList);

        // Convert list of Account into AccountListDTO
        List<AccountResponseDTO> accountResponseList = AccountListDTO.accountListToAccountResponseDTOList(updatedSportCenter.getAccounts());
        AccountListDTO accounts = new AccountListDTO(accountResponseList);

        return new ResponseEntity<SportCenterDTO>(new SportCenterDTO(updatedSportCenter.getName(), updatedSportCenter.getOpeningTime(), updatedSportCenter.getClosingTime(), updatedSportCenter.getAddress(), updatedSportCenter.getEmail(), updatedSportCenter.getPhoneNumber(), courses, locations, accounts), HttpStatus.ACCEPTED);
    }

    // Deleting a sport center
    @DeleteMapping("/sport-center")
    public ResponseEntity<Void> deleteSportCenter() {
        sportCenterManagementService.deleteSportCenter();
        return ResponseEntity.noContent().build();
    }

}
