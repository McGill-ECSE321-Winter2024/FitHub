package ca.mcgill.ecse321.sportcenter.integration;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

import ca.mcgill.ecse321.sportcenter.repository.BillingAccountRepository;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.repository.RegistrationRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionPackageRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;

@SpringBootTest
public class CommonTestSetup {
    @Autowired
    private BillingAccountRepository billingAccountRepository;
    @Autowired 
    private RegistrationRepository registrationRepository;
	@Autowired
	private SessionRepository sessionRepository;

    @Autowired
    private SportCenterRepository sportCenterRepo;

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private OwnerRepository ownerRepo;

    @Autowired
	private CourseRepository courseRepository;
    @Autowired
	private LocationRepository locationRepository;

    @Autowired
    private SessionPackageRepository sessionPackageRepository;
    

    /**
     * Clears the database before each test.
     */
    @BeforeAll
    @AfterTestClass
    public void clearDatabase() {
        billingAccountRepository.deleteAll();
        registrationRepository.deleteAll();
		sessionRepository.deleteAll();
        sportCenterRepo.deleteAll();
        courseRepository.deleteAll();
        sessionPackageRepository.deleteAll();
        locationRepository.deleteAll();
        customerRepo.deleteAll();
        instructorRepo.deleteAll();
        ownerRepo.deleteAll();
    }
}
