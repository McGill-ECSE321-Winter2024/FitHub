package ca.mcgill.ecse321.sportcenter.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import jakarta.transaction.Transactional;

/*
* <p>Service class in charge of managing sessions. It implements following use cases: </p>
* <p>Create, update, delete a session </p>
* @author Émilia
*/
@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepo;

    @Autowired
    private InstructorRepository instructRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private LocationRepository locationRepo;

    //--------------------------// Create Session //--------------------------//

    @Transactional
    public Session proposeSuperviseSession(Time aStartTime, Time aEndTime, Date aDate, int aCapacity, int iId, int cId, int lId){
        //Input Validation
        //Things left to implement: make sure aEndTime is after aStartTime and that it is inside the opnening hours
        if(aCapacity<=0 || aDate == null || aStartTime == null || aEndTime == null){
            throw new IllegalArgumentException();
        }
        Instructor aSupervisor = instructRepo.findInstructorById(iId);
        Location aLocation = locationRepo.findLocationById(lId);
        Course aCourseType = courseRepo.findCourseById(cId);
        if(aSupervisor == null || aLocation == null || aCourseType == null){
            throw new IllegalArgumentException();
        }
        Session sessionToCreate = new Session(aStartTime, aEndTime, aDate, aCapacity, aSupervisor, aCourseType, aLocation);
        return sessionRepo.save(sessionToCreate);
    }

    

    //--------------------------// Update Session //--------------------------//

    @Transactional
    public Session updateSession(int sid, Time aStartTime, Time aEndTime, Date aDate, int aCapacity, int iId, int cId, int lId){
        //Things left to implement: make sure aEndTime is after aStartTime and that it is inside the opnening hours
        Session sessionToUpdate = findSessionById(sid);
        Instructor aSupervisor = instructRepo.findInstructorById(iId);
        Location aLocation = locationRepo.findLocationById(lId);
        Course aCourseType = courseRepo.findCourseById(cId);

        sessionToUpdate.setStartTime(aStartTime);
        sessionToUpdate.setEndTime(aEndTime);
        sessionToUpdate.setDate(aDate);
        sessionToUpdate.setCapacity(aCapacity);
        sessionToUpdate.setSupervisor(aSupervisor);
        sessionToUpdate.setCourseType(aCourseType);
        sessionToUpdate.setLocation(aLocation);
        return sessionRepo.save(sessionToUpdate);
    }

    //--------------------------// Delete Session //--------------------------//

    @Transactional
    //An instructor or an owner cancel a session
    public void cancelSession(int sid){
        Session sessionToCancel = sessionRepo.findById(sid);
        sessionRepo.delete(sessionToCancel);
        //I am unsure if this line is necessary
        sessionToCancel.delete();

    }

    //--------------------------// Getters //--------------------------//

    @Transactional
    public List<Session> findAllSessions() {
        return toList(sessionRepo.findAll());
    }

    @Transactional
    public Session findSessionById(int sid) {
        Session session =  sessionRepo.findById(sid);
        if(session == null){
            throw new IllegalArgumentException();
        }
        return session;
    }

    @Transactional
    public List<Session> findSessionsByInstructor(Instructor supervisor){
        if(supervisor == null){
            throw new IllegalArgumentException();
        }
        return sessionRepo.findBySupervisor(supervisor);
    }

    @Transactional
    public List<Session> findSessionsByCourse(Course course){
        if(course == null){
            throw new IllegalArgumentException();
        }
        return sessionRepo.findByCourseType(course);
    }

    //--------------------------// Helper functions //--------------------------//
    
    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    public Course getCourseById(int id){
        return courseRepo.findCourseById(id);
    }

    public Instructor getInstructorById(int id){
        return instructRepo.findInstructorById(id);
    }
}
