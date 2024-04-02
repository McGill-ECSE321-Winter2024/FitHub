package ca.mcgill.ecse321.sportcenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Date;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.SessionPackage;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionPackageRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import jakarta.transaction.Transactional;

/*
* <p>Service class in charge of managing sessionPackages. It implements following use cases: </p>
* <p>Create, update, delete a sessionPackage </p>
* @author Émilia
*/

public class SessionPackageService {

    @Autowired
    private SessionPackageRepository sessionPackageRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private SportCenterRepository sportCenterRepository;

    //--------------------------// Create SessionPackage //--------------------------//

    @Transactional
    public SessionPackage createSessionPackage(int aPriceReduction, int aDuration, Date aDate, Course aCourse){

        //Input validation
        SessionPackage sessionPackage = new SessionPackage(aPriceReduction, aDuration, aDate, aCourse);
        return sessionPackageRepo.save(sessionPackage);
    }

    //--------------------------// Update Package //--------------------------//

    @Transactional
    public SessionPackage updateSessionPackageReduction(int id, int aNewPriceReduction){
        //Input valid
        SessionPackage sessionPackageToUpdate = sessionPackageRepo.findSessionPackageById(id);
        sessionPackageToUpdate.setPriceReduction(aNewPriceReduction);
        return sessionPackageRepo.save(sessionPackageToUpdate);
    }

    //--------------------------// Delete Package //--------------------------//

    @Transactional
    public boolean deleteSessionPackage(int id){
        //Input valid
        SessionPackage sessionPackageToDelete = sessionPackageRepo.findSessionPackageById(id);
        sessionPackageRepo.delete(sessionPackageToDelete);
        return true;
    }


}
