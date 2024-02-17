package ca.mcgill.ecse321.sportcenter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;

import java.sql.Time;

@SpringBootTest
class SportcenterApplicationTests {

	@Test
	void contextLoads() {
	}

	 @Autowired
    private SportCenterRepository repo;

    @BeforeAll
	@AfterAll
    public void clearDatabase() {
        repo.deleteAll();
    }

	@BeforeAll
	public void createSportsCenter(){
		String name = "FitHub";
		Time openTime = Time.valueOf("08:00:00");
        Time closeTime = Time.valueOf("18:00:00");
		String email = "info@fithub.com";
		String phone = "421-436-4444";
		String address = "2011, University Street, Montreal";
		
		SportCenter sportsCenter = new SportCenter(0, name, openTime, closeTime, address, email, phone);

		sportsCenter = repo.save(sportsCenter);
		int centerId = sportsCenter.getId();

		SportCenter centerFromDb = repo.findSportCenterById(centerId);

	}
}
