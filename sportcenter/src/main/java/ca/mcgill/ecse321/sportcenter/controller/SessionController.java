package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    //--------------------------// Getters //--------------------------//
    @GetMapping("/sessions/{sid}")
    public Session findSessionById(@PathVariable int sid){
        return sessionService.findSessionById(sid);
    }

    @GetMapping("/sessions")
    public Iterable<Session> findAllSessions(){
        return sessionService.findAllSessions();
    }

    //INCOMPLETE
    @GetMapping("/sessions/")
    public Iterable<Session> findSessionsByInstructor(){
        Instructor instructor = null;
        return sessionService.findSessionsByInstructor(instructor);
    }

    //INCOMPLETE
    @GetMapping("/sessions/")
    public Iterable<Session> findSessionsByCourse(){
        Course course = null;
        return sessionService.findSessionsByCourse(course);
    }

    //--------------------------// Create Session //--------------------------//
    @PostMapping("/sessions")
    public Session proposeSuperviseSession(@RequestBody Session session){
        return sessionService.proposeSuperviseSession(session.getStartTime(), session.getEndTime(), session.getDate(), session.getCapacity(), session.getSupervisor(), session.getCourseType(), session.getLocation());
    }
    //--------------------------// Update Session //--------------------------//
    @PutMapping("/sessions/{id}")
    public Session updatSession(@RequestBody Session newSession, @PathVariable int id){
        return sessionService.updateSession(id, newSession.getStartTime(), newSession.getEndTime(), newSession.getDate(), newSession.getCapacity(), newSession.getSupervisor(), newSession.getCourseType(), newSession.getLocation());
    }
    //--------------------------// Delete Session //--------------------------//
    @DeleteMapping("/sessions/{id}")
    public void cancelSession(@PathVariable int id){
        sessionService.cancelSession(id);
    }
    
}
