package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
//DELETE UNUSED IMPORT
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.service.SessionService;

/*
* <p>Controller class in charge of managing sessions. It implements following use cases: </p>
* <p>Create, update, delete a session </p>
* @author Émilia
*/
@CrossOrigin(origins = "*")
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    //--------------------------// Getters //--------------------------//
    @GetMapping("/sessions/{sid}")
    public Session findSessionById(@PathVariable int sid){
        return sessionService.findSessionById(sid);
    }

    @GetMapping(value = { "/sessions", "/sessions/" })
    public SessionListDTO findAllSessions(){
        List<SessionResponseDTO> sessions = new ArrayList<SessionResponseDTO>();
        for (Session model : sessionService.findAllSessions()){
            sessions.add(new SessionResponseDTO(model));
        }
        return new SessionListDTO(sessions);
    }
    
    @GetMapping("/sessions/{iId}")
    public SessionListDTO findSessionsByInstructor(@PathVariable int iId){
        Instructor instructor = sessionService.getInstructorById(iId);
        List<SessionResponseDTO> sessions = new ArrayList<SessionResponseDTO>();
        for (Session model : sessionService.findSessionsByInstructor(instructor)){
            sessions.add(new SessionResponseDTO(model));
        }
        return new SessionListDTO(sessions);
    }

    @GetMapping("/sessions/{cId}")
    public SessionListDTO findSessionsByCourse(@PathVariable int cId){
        Course course = sessionService.getCourseById(cId);
        List<SessionResponseDTO> sessions = new ArrayList<SessionResponseDTO>();
        for (Session model : sessionService.findSessionsByCourse(course)){
            sessions.add(new SessionResponseDTO(model));
        }
        return new SessionListDTO(sessions);
    }

    //--------------------------// Create Session //--------------------------//
    //TO CONFIRM WITH TA
    @PostMapping("/sessions/{iId}/{cId}/{lId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponseDTO proposeSuperviseSession(@RequestBody SessionRequestDTO session, @PathVariable int iId, @PathVariable int cId, @PathVariable int lId){
        return new SessionResponseDTO(sessionService.proposeSuperviseSession(session.getStartTime(), session.getEndTime(), session.getDate(), session.getCapacity(), iId, cId, lId));
    }
    //--------------------------// Update Session //--------------------------//
    //TO CONFIRM WITH TA
    @PutMapping("/sessions/{id}/{iId}/{cId}/{lId}")
    public Session updatSession(@RequestBody SessionRequestDTO newSession, @PathVariable int id, @PathVariable int iId, @PathVariable int cId, @PathVariable int lId){
        return sessionService.updateSession(id, newSession.getStartTime(), newSession.getEndTime(), newSession.getDate(), newSession.getCapacity(), iId, cId, lId);
    }
    //--------------------------// Delete Session //--------------------------//
    @DeleteMapping("/sessions/{id}")
    public void cancelSession(@PathVariable int id){
        sessionService.cancelSession(id);
    }
    
}
