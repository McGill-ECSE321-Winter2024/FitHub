package ca.mcgill.ecse321.sportcenter.repository;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;

@SpringBootTest
public class CommonTestSetup {
    protected SportCenter sportCenter;

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
    private SessionPackageRepository sessionPackageRepository;

    @Autowired
	private CourseRepository courseRepository;
    @Autowired
	private LocationRepository locationRepository;
    

    /**
     * Clears the database before each test.
     */
    @AfterEach
    public void clearDatabase() {
        billingAccountRepository.deleteAll();
        registrationRepository.deleteAll();
		sessionRepository.deleteAll();
        sportCenterRepo.deleteAll();
        sessionPackageRepository.deleteAll();
        courseRepository.deleteAll();
        locationRepository.deleteAll();
        customerRepo.deleteAll();
        instructorRepo.deleteAll();
        ownerRepo.deleteAll();
    }

    /**
     * Creates and saves a SportCenter instance before each test.
     */
    @BeforeEach
    public void createAndSaveSportCenter() {
        billingAccountRepository.deleteAll();
        registrationRepository.deleteAll();
		sessionRepository.deleteAll();
        sportCenterRepo.deleteAll();
        courseRepository.deleteAll();
        locationRepository.deleteAll();
        customerRepo.deleteAll();
        instructorRepo.deleteAll();
        ownerRepo.deleteAll();
        
        sportCenterRepo.deleteAll();
        SportCenter sportCenter = new SportCenter();
        sportCenter.setName("FitHub");
        sportCenter.setOpeningTime(Time.valueOf("08:00:00"));
        sportCenter.setClosingTime(Time.valueOf("23:59:00"));
        sportCenter.setEmail("info@fithub.com");
        sportCenter.setPhoneNumber("421-436-4444");
        sportCenter.setAddress("2011, University Street, Montreal");

        // Save sportCenterRepo
        sportCenter = sportCenterRepo.save(sportCenter);
    }
}
