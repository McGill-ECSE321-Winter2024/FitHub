package ca.mcgill.ecse321.sportcenter.service;

import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import jakarta.transaction.Transactional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepo;

    @Transactional
    public Iterable<Session> findAllSession() {
        return sessionRepo.findAll();
    }

    @Transactional
    public Session findSessionById(int sid) {
        return sessionRepo.findSessionById(sid);
    }
 
    @Transactional
    public Session createSession(Time aStartTime, Time aEndTime, Date aDate, int aCapacity, Instructor aSupervisor, Course aCourseType, Location aLocation){
        //Input Validation
        if(aCapacity<=0){
            throw new IllegalArgumentException("Capacity should be greater than 0");
        }
        Session sessionToCreate = new Session(aStartTime, aEndTime, aDate, aCapacity, aSupervisor, aCourseType, aLocation);
        return sessionRepo.save(sessionToCreate);
    }

    @Transactional
    public Iterable<Session> findSessionsByInstructor(Instructor supervisor){
        return sessionRepo.findSessionsByInstructor(supervisor);
    }

    @Transactional
    public Iterable<Session> findSessionByCourse(Course course){
        return sessionRepo.findSessionsByCourse(course);
    }
    
}
