package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.SessionPackage;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.model.Session;


/**
 * This class provides test cases for the SessionPackageRepository class.
 * It verifies the functionalities related to creating and reading sessionPackages.
 */
@SpringBootTest
public class SessionPackageRepositoryTests {

    @Autowired
    private SessionPackageRepository sessionPackageRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Test
    public void testPersistAndLoadSessionPackages(){

        Course aCourseType = new Course();
        aCourseType.setName("Kung Fu I");
        aCourseType.setDescription("Martial art beginner course");
        aCourseType.setDifficulty(Difficulty.Beginner);
        aCourseType.setStatus(Status.Pending);
        aCourseType = courseRepo.save(aCourseType);

        int aDuration = 6;
        Date aDate = Date.valueOf("2024-02-18");
        int aPriceReduction = 10;

        SessionPackage sessionPackage = new SessionPackage();
        sessionPackage.setDuration(aDuration);
        sessionPackage.setDate(aDate);
        sessionPackage.setPriceReduction(aPriceReduction);

        sessionPackage = sessionPackageRepo.save(sessionPackage);

        SessionPackage sessionPackageFromDb = sessionPackageRepo.findSessionPackageById(sessionPackage.getId());

        assertNotNull(sessionPackageFromDb);
        assertEquals(aDuration, sessionPackageFromDb.getDuration());
        assertEquals(aDate, sessionPackageFromDb.getDate());
        assertEquals(aPriceReduction, sessionPackageFromDb.getPriceReduction());
        assertEquals(sessionPackage.getId(), sessionPackageFromDb.getId());
        

    }
        
    
}
