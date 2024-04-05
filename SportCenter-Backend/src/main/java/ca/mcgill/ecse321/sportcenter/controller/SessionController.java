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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.SessionListDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.service.CourseService;
import ca.mcgill.ecse321.sportcenter.service.SessionService;

/*
* <p>Controller class in charge of managing sessions. It implements following use cases: </p>
* <p>Create, update, delete a session </p>
* @author Émilia
*/
@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private CourseService courseService;

    //--------------------------// Getters //--------------------------//
    @GetMapping("/sessions/{sid}")
    public ResponseEntity<SessionResponseDTO> findSessionById(@PathVariable int sid){
        try{
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(sessionService.findSessionById(sid)), HttpStatus.FOUND);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = { "/sessions", "/sessions/" })
    public ResponseEntity<SessionListDTO> findAllSessions(){
        List<SessionResponseDTO> sessions = new ArrayList<SessionResponseDTO>();
        try{
            for (Session model : sessionService.findAllSessions()){
                sessions.add(new SessionResponseDTO(model));
            }
            if(sessions.isEmpty())
                return new ResponseEntity<SessionListDTO>(new SessionListDTO(sessions),HttpStatus.NO_CONTENT);
            else{
                return new ResponseEntity<SessionListDTO>(new SessionListDTO(sessions),HttpStatus.OK);
            }
    
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionListDTO>(new SessionListDTO(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/sessions/instructors/{iId}")
    public ResponseEntity<SessionListDTO> findSessionsByInstructor(@PathVariable int iId){
        try{
            Instructor instructor = sessionService.getInstructorById(iId);
            SessionListDTO sessions = new SessionListDTO();
            List<SessionResponseDTO> responseDTOs = new ArrayList<SessionResponseDTO>();

            List<Session> list = sessionService.findSessionsByInstructor(instructor);
            
            if(list.isEmpty()){
                return new ResponseEntity<SessionListDTO>(sessions,HttpStatus.NO_CONTENT);
            }

            for(Session s : list){
                responseDTOs.add(new SessionResponseDTO(s));
            }
    
            sessions.setSessions(responseDTOs);
            return new ResponseEntity<SessionListDTO>(sessions,HttpStatus.OK);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionListDTO>(new SessionListDTO(),HttpStatus.BAD_REQUEST);
        }

        
    }

    @GetMapping("/sessions/courses/{cId}")
    public ResponseEntity<SessionListDTO> findSessionsByCourse(@PathVariable int cId){
        try{
            Course course = courseService.findCourseById(cId);
            List<SessionResponseDTO> sessions = new ArrayList<SessionResponseDTO>();
            for (Session model : sessionService.findSessionsByCourse(course)){
                sessions.add(new SessionResponseDTO(model));
            }
            if(sessions.isEmpty())
                return new ResponseEntity<SessionListDTO>(new SessionListDTO(sessions),HttpStatus.NO_CONTENT);
            else{
                return new ResponseEntity<SessionListDTO>(new SessionListDTO(sessions),HttpStatus.OK);
            }
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionListDTO>(new SessionListDTO(),HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Create Session //--------------------------//
    
    @PostMapping("/sessions/{iId}/{cId}/{lId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SessionResponseDTO> proposeSuperviseSession(@RequestBody SessionRequestDTO session, @PathVariable int iId, @PathVariable int cId, @PathVariable int lId){
        try{
            return new ResponseEntity<SessionResponseDTO>( new SessionResponseDTO(sessionService.proposeSuperviseSession(session.getStartTime(), session.getEndTime(), session.getDate(), session.getCapacity(), iId, cId, lId)),HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sessions/{iId}/{cId}/{lId}/{recurrenceDuration}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SessionListDTO> createRecurrentSessions(@RequestBody SessionRequestDTO session, @PathVariable int iId, @PathVariable int cId, @PathVariable int lId, @PathVariable int recurrenceDuration){
        List<SessionResponseDTO> sessions = new ArrayList<SessionResponseDTO>();
        try{
            for (Session model : sessionService.createRecurrentSessions(session.getStartTime(), session.getEndTime(), session.getDate(), session.getCapacity(), iId, cId, lId, recurrenceDuration)){
                sessions.add(new SessionResponseDTO(model));
            }
            if(sessions.isEmpty())
                return new ResponseEntity<SessionListDTO>(new SessionListDTO(sessions),HttpStatus.NO_CONTENT);
            else{
                return new ResponseEntity<SessionListDTO>(new SessionListDTO(sessions),HttpStatus.OK);
            }
    
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionListDTO>(new SessionListDTO(),HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Update Session //--------------------------//
    
    @PutMapping("/sessions/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(@RequestBody SessionRequestDTO newSession, @PathVariable int id){
        try{
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(sessionService.updateSession(id, newSession.getStartTime(), newSession.getEndTime(), newSession.getDate(), newSession.getCapacity())),HttpStatus.ACCEPTED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/sessions/{sId}/locations/{lId}")
    public ResponseEntity<SessionResponseDTO> updateSessionLocation(@PathVariable int sId, @PathVariable int lId){
        try{
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(sessionService.updateSessionLocation(sId, lId)),HttpStatus.ACCEPTED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(), HttpStatus.BAD_REQUEST);
        } 
    }

    @PutMapping("/sessions/{sId}/instructors/{iId}")
    public ResponseEntity<SessionResponseDTO> updateSessionSupervisor(@PathVariable int sId, @PathVariable int iId){
        try{
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(sessionService.updateSessionSupervisor(sId, iId)),HttpStatus.ACCEPTED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<SessionResponseDTO>(new SessionResponseDTO(), HttpStatus.BAD_REQUEST);
        }    
    }

    //--------------------------// Delete Session //--------------------------//
    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<Void> cancelSession(@PathVariable int id){
        boolean deletionSuccessful = sessionService.cancelSession(id);
        if (deletionSuccessful) {
            return ResponseEntity.noContent().build(); // 204 NO_CONTENT
        } else {
            return ResponseEntity.notFound().build(); // 404 NOT_FOUND if the customer with the specified ID was not found
        }
    }
    
}
