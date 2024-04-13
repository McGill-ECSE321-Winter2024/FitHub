package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.SessionPackageListDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionPackageRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionPackageResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.SessionPackage;
import ca.mcgill.ecse321.sportcenter.service.SessionPackageService;


/*
* <p>Controller class in charge of managing sessions. It implements following use cases: </p>
* <p>Create, update, delete a session </p>
* @author Émilia
*/
@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class SessionPackageController {

    @Autowired
    private SessionPackageService sessionPackageService;

    //-----------------------// Getters //----------------------//
    @GetMapping("/session-packages/{id}")
    public ResponseEntity<SessionPackageResponseDTO> findSessionPackageById(@PathVariable int id){
        try{
            return new ResponseEntity<SessionPackageResponseDTO>(new SessionPackageResponseDTO(sessionPackageService.getSessionPackageById(id)), HttpStatus.FOUND);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionPackageResponseDTO>(new SessionPackageResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = { "/session-packages/course/{courseId}", "public/session-packages/course/{courseId}"})
    public ResponseEntity<SessionPackageListDTO> findSessionPackageByCourse(@PathVariable int courseId){
        try{
            List<SessionPackageResponseDTO> packages = new ArrayList<SessionPackageResponseDTO>();
            for (SessionPackage model : sessionPackageService.getSessionPackagesByCourse(courseId)){
                packages.add(new SessionPackageResponseDTO(model));
            }
            if(packages.isEmpty())
                return new ResponseEntity<SessionPackageListDTO>((
                    new SessionPackageListDTO(packages)),HttpStatus.NO_CONTENT);
            else{
                return new ResponseEntity<SessionPackageListDTO>(new SessionPackageListDTO(packages),HttpStatus.OK);
            }
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionPackageListDTO>(new SessionPackageListDTO(), HttpStatus.BAD_REQUEST);
        }

    }

    //----------------------// Create //------------------------//
    @PostMapping("/session-packages/{courseId}")
    public ResponseEntity<SessionPackageResponseDTO> createSessionPackage(@RequestBody SessionPackageRequestDTO sessionPackage, @PathVariable int courseId){
        try{
            return new ResponseEntity<SessionPackageResponseDTO>(new SessionPackageResponseDTO(sessionPackageService.createSessionPackage(sessionPackage.getPriceReduction(), sessionPackage.getDuration(), sessionPackage.getDate(), courseId)),HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionPackageResponseDTO>(new SessionPackageResponseDTO(),HttpStatus.BAD_REQUEST);
            
        }
    }

    //----------------------// Update //------------------------//

    @PutMapping("/session-packages/{id}")
    public ResponseEntity<SessionPackageResponseDTO> updateSessionPackage(@RequestBody SessionPackageRequestDTO aNewPackage, @PathVariable int id){
        try{
            return new ResponseEntity<SessionPackageResponseDTO>(new SessionPackageResponseDTO(sessionPackageService.updateSessionPackageReduction(id, aNewPackage.getPriceReduction())),HttpStatus.ACCEPTED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionPackageResponseDTO>(new SessionPackageResponseDTO(),HttpStatus.BAD_REQUEST);
            
        }
    }
    
    //---------------------// Delete //-------------------------//

    @DeleteMapping("/session-packages/{id}")
    public ResponseEntity<SessionPackageResponseDTO> deleteSessionPackage(@PathVariable int id){
        boolean deletionSuccessful = sessionPackageService.deleteSessionPackage(id);
        if (deletionSuccessful) {
            return ResponseEntity.noContent().build(); // 204 NO_CONTENT
        } else {
            return ResponseEntity.notFound().build(); // 404 NOT_FOUND if the sessionPackage with the specified ID was not found
        }
    }
    
}
