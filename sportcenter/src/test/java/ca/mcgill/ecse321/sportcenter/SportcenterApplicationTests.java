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
public class SportcenterApplicationTests {

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

    @Test
	public void testCreateSportsCenter(){
		// TODO finish
		SportCenter sportsCenter = repo.save(SportCenter.getSportCenter());
		int centerId = sportsCenter.getId();

		SportCenter centerFromDb = repo.findSportCenterById(centerId);

	}
}
