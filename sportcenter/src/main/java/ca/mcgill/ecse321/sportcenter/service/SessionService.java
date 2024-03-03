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
    public Iterable<Session> findAllSessions() {
        return sessionRepo.findAll();
    }

    @Transactional
    public Session findSessionById(int sid) {
        return sessionRepo.findSessionById(sid);
    }
 
    //An instructor propose to supervise a session
    @Transactional
    public Session proposeSuperviseSession(Time aStartTime, Time aEndTime, Date aDate, int aCapacity, Instructor aSupervisor, Course aCourseType, Location aLocation){
        Session sessionToCreate = new Session(aStartTime, aEndTime, aDate, aCapacity, aSupervisor, aCourseType, aLocation);
        return sessionRepo.save(sessionToCreate);
    }

    @Transactional
    public Session updateSession(int sid, Time aStartTime, Time aEndTime, Date aDate, int aCapacity, Instructor aSupervisor, Course aCourseType, Location aLocation){
        Session sessionToUpdate = findSessionById(sid);
        sessionToUpdate.setStartTime(aStartTime);
        sessionToUpdate.setEndTime(aEndTime);
        sessionToUpdate.setDate(aDate);
        sessionToUpdate.setCapacity(aCapacity);
        sessionToUpdate.setSupervisor(aSupervisor);
        sessionToUpdate.setCourseType(aCourseType);
        sessionToUpdate.setLocation(aLocation);
        return sessionRepo.save(sessionToUpdate);
    }

    @Transactional
    //An instructor or an owner cancel a session
    public void cancelSession(int sid){
        Session sessionToCancel = sessionRepo.findSessionById(sid);
        sessionRepo.delete(sessionToCancel);
        //I am unsure if this line is necessary
        sessionToCancel.delete();

    }

    
}
