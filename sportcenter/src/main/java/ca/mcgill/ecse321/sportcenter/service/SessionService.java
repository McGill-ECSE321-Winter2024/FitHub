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
import ca.mcgill.ecse321.sportcenter.service.CourseService;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
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

    @Autowired
    private SportCenterRepository sportCenterRepository;

    @Autowired
    private CourseService courseService;

    //--------------------------// Create Session //--------------------------//

    @Transactional
    public Session proposeSuperviseSession(Time aStartTime, Time aEndTime, Date aDate, int aCapacity, int iId, int cId, int lId){
        //Input Validation
        //Things left to implement: make sure aEndTime is after aStartTime and that it is inside the opnening hours
        if(aCapacity<=0 || aDate == null || aStartTime == null || aEndTime == null){
            throw new IllegalArgumentException();
        }
        if(aStartTime.after(aEndTime)){
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if(isTimeOutsideOpeningHours(aStartTime, aEndTime)){
            throw new IllegalArgumentException("Proposed time is outside opening hours");
        }
        
        Instructor aSupervisor = instructRepo.findInstructorById(iId);
        Location aLocation = locationRepo.findLocationById(lId);
        Course aCourseType = getCourseById(cId);
        if(aSupervisor == null || aLocation == null || aCourseType == null){
            throw new IllegalArgumentException();
        }
        if(!isSupervisorAvailable(aDate, aStartTime, aEndTime, aSupervisor)){
            throw new IllegalArgumentException("Supervisor is already supervising a session on day " + aDate.toString() + "between time " + aStartTime.toString() + "and time " + aEndTime.toString() + ".");

        }
        if(!isLocationAvailable(aLocation, aDate, aStartTime, aEndTime)){
            throw new IllegalArgumentException("The location is already reserved for another session on day " + aDate.toString() + "between time " + aStartTime.toString() + "and time " + aEndTime.toString() + ".");
        }
        
        Session sessionToCreate = new Session(aStartTime, aEndTime, aDate, aCapacity, aSupervisor, aCourseType, aLocation);
        return sessionRepo.save(sessionToCreate);
    }

    

    //--------------------------// Update Session //--------------------------//

    @Transactional
    public Session updateSession(int sid, Time aStartTime, Time aEndTime, Date aDate, int aCapacity){
        if(aCapacity<=0 || aDate == null || aStartTime == null || aEndTime == null){
            throw new IllegalArgumentException();
        }
        if(aStartTime.after(aEndTime)){
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if(isTimeOutsideOpeningHours(aStartTime, aEndTime)){
            throw new IllegalArgumentException("Proposed time is outside opening hours");
        }

        Session sessionToUpdate = findSessionById(sid);
        sessionToUpdate.setStartTime(aStartTime);
        sessionToUpdate.setEndTime(aEndTime);
        sessionToUpdate.setDate(aDate);
        sessionToUpdate.setCapacity(aCapacity);
        return sessionRepo.save(sessionToUpdate);
    }

    @Transactional 
    public Session updateSessionSupervisor(int sid, int iId){
        Session sessionToUpdate = findSessionById(sid);
        Instructor newSupervisor = instructRepo.findInstructorById(iId);
        if(newSupervisor == null){
            throw new IllegalArgumentException();
        }
        if(!isSupervisorAvailable(sessionToUpdate.getDate(), sessionToUpdate.getStartTime(), sessionToUpdate.getEndTime(), newSupervisor)){
            throw new IllegalArgumentException("Supervisor is already supervising a session on day " + sessionToUpdate.getDate().toString() + "between time " + sessionToUpdate.getStartTime().toString() + "and time " + sessionToUpdate.getEndTime().toString() + ".");

        }
        sessionToUpdate.setSupervisor(newSupervisor);
        return sessionRepo.save(sessionToUpdate);
    }

    @Transactional 
    public Session updateSessionLocation(int sid, int lId){
        Location newLocation = locationRepo.findLocationById(lId);
        if(newLocation == null){
            throw new IllegalArgumentException("Location does not exist");
        }
        Session sessionToUpdate = findSessionById(sid);
        if(!isLocationAvailable(newLocation, sessionToUpdate.getDate(), sessionToUpdate.getStartTime(), sessionToUpdate.getEndTime())){
            throw new IllegalArgumentException("The location is already reserved for another session on day " + sessionToUpdate.getDate().toString() + "between time " + sessionToUpdate.getStartTime().toString() + "and time " + sessionToUpdate.getEndTime().toString() + ".");
        }
        sessionToUpdate.setLocation(newLocation);
        return sessionRepo.save(sessionToUpdate);
    }

    //--------------------------// Delete Session //--------------------------//

    @Transactional
    //An instructor or an owner cancel a session
    public boolean cancelSession(int sid){
        Session sessionToCancel = sessionRepo.findById(sid);
        if(sessionToCancel == null){
            return false;
        }
        sessionRepo.delete(sessionToCancel);
        //I am unsure if this line is necessary
        sessionToCancel.delete();
        return true;

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
            throw new IllegalArgumentException("Instructor does not exist");
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
        return courseService.findCourseById(id);
    }

    public Instructor getInstructorById(int id){
        return instructRepo.findInstructorById(id);
    }

    private boolean isLocationAvailable(Location location, Date date, Time aStartTime, Time aEndTime){

        // Get the sessions associated with given location and date
        //For each session in sessions, check if there is overlap with the given time
        List<Session> sessions = sessionRepo.findByLocationAndDate(location, date);
        return !isTimeOverlap(sessions, aStartTime, aEndTime);

    }

    private boolean isSupervisorAvailable(Date date, Time aStartTime, Time aEndTime, Instructor supervisor){
        //get the sessions associated with the given date and supervisor
        //For each session, check if there is overlap with the given time
        List<Session> session = sessionRepo.findBySupervisorAndDate(supervisor, date);
        return !isTimeOverlap(session, aStartTime, aEndTime);
    }

    private boolean isTimeOverlap(List<Session> sessions, Time startTime1, Time endTime1){
        //Four cases of Overlap:
        // Case 1 diagram: [s1   [s2      e1]      e2] s1 is before s2 and e1 is after s2
        // Case 2 diagram: [s2   [s1      e2]      e1] s2 is before s1 and e2 is after s1
        // Case 3 diagram: [s1   [s2      e2]      e1] s1 is before s2 and e2 is before e1
        // Case 4 diagram: [s2   [s1      e1]      e2] s2 is before s1 and e1 is before e2

       for(Session session:sessions){
            Time startTime2 = session.getStartTime();
            Time endTime2 = session.getEndTime();
            if(startTime1.before(startTime2)){
                if(endTime1.after(startTime2)) //Case 1
                    return true;
                if(endTime2.before(endTime1)) //Case 3
                    return true;
            }
            else{
                if(endTime2.after(startTime1)) 
                    return true;
                if(endTime1.before(endTime2))
                    return true;
            } 
            
        }
       
        return false;

    }

    public boolean isTimeOutsideOpeningHours(Time startTime, Time endTime){

        List<SportCenter> sportCenters = sportCenterRepository.findAll();
        //There will only be one sportCenter in the whole database, which is why I can get 
        //the first element and be sure it will be the corresponding sportCenter to the Session object
        SportCenter sportCenter = sportCenters.get(0);
        return sportCenter.getClosingTime().before(endTime) || sportCenter.getOpeningTime().after(startTime);
        
    }

}
