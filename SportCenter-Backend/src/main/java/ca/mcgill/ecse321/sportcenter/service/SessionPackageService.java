package ca.mcgill.ecse321.sportcenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.SessionPackage;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionPackageRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import jakarta.transaction.Transactional;

/*
* <p>Service class in charge of managing sessionPackages. It implements following use cases: </p>
* <p>Create, update, delete a sessionPackage </p>
* @author Émilia
*/

@Service
public class SessionPackageService {

    @Autowired
    private SessionPackageRepository sessionPackageRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private SessionRepository sessionRepo;

    //--------------------------// Create SessionPackage //--------------------------//

    @Transactional
    public SessionPackage createSessionPackage(int aPriceReduction, int aDuration, LocalDate aDate, int courseId){
        Course aCourse = courseRepo.findCourseById(courseId);
        if(aPriceReduction > 100 || aPriceReduction<0 || aDuration <0 || aDate == null ){
            throw new IllegalArgumentException("Invalid input");
        }
        if(aCourse == null){
            throw new IllegalArgumentException("Course not found");
        }
        SessionPackage sessionPackage = new SessionPackage(aPriceReduction, aDuration, aDate, aCourse);
        return sessionPackageRepo.save(sessionPackage);
    }

    //--------------------------// Update Package //--------------------------//

    @Transactional
    public SessionPackage updateSessionPackageReduction(int id, int aNewPriceReduction){
        
        SessionPackage sessionPackage = sessionPackageRepo.findSessionPackageById(id);
        if(sessionPackage == null){
            throw new IllegalArgumentException("Session package not found");
        }
        if(aNewPriceReduction<0 || aNewPriceReduction >100){
            throw new IllegalArgumentException("The new price reduction must be between 0 and 100");
        }
        SessionPackage sessionPackageToUpdate = sessionPackageRepo.findSessionPackageById(id);
        sessionPackageToUpdate.setPriceReduction(aNewPriceReduction);
        return sessionPackageRepo.save(sessionPackageToUpdate);
    }

    //--------------------------// Delete Package //--------------------------//

    @Transactional
    public boolean deleteSessionPackage(int id){
        
        SessionPackage sessionPackageToDelete = sessionPackageRepo.findSessionPackageById(id);
        if(sessionPackageToDelete == null){
            throw new IllegalArgumentException("Session package not found");
        }
        sessionPackageRepo.delete(sessionPackageToDelete);
        return true;
    }

    //---------------------------// Getters //-------------------------------//

    @Transactional
    public List<SessionPackage> getSessionPackagesByCourse(int courseId){
        Course course = courseRepo.findCourseById(courseId);
        if(course ==null){
            throw new IllegalArgumentException("Course not found");
        }
        return sessionPackageRepo.findSessionPackageByCourse(course);
    }

    @Transactional
    public SessionPackage getSessionPackageById(int id){
        SessionPackage sessionPackage = sessionPackageRepo.findSessionPackageById(id);
        if(sessionPackage ==null){
            throw new IllegalArgumentException("SessionPackage not found");
        }
        return sessionPackage;
    }

    @Transactional
    public List<Session> findSessionsBySessionPackage(int id){
        SessionPackage sessionPackage = sessionPackageRepo.findSessionPackageById(id);
        if(sessionPackage ==null){
            throw new IllegalArgumentException("SessionPackage not found");
        }
        LocalDate startDate = sessionPackage.getDate();
        LocalDate endDate =  startDate.plusDays(sessionPackage.getDuration() * 7);
        List<Session> sessions = new ArrayList<Session>();
        List<Session> sessionByCourse = sessionRepo.findByCourseType(sessionPackage.getCourse());

        for(Session session : sessionByCourse){
            if(session.getDate().isAfter(startDate) && session.getDate().isBefore(endDate)){
                sessions.add(session);
            }
        }
        return sessions;
    }

}
