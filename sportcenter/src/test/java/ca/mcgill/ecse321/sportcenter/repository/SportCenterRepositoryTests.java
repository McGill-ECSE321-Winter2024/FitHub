package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

@SpringBootTest
public class SportCenterRepositoryTests {

    @Autowired
    private SportCenterRepository sportCenterRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase(){
        sportCenterRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadSportCenter(){
        String name = "FitHub";
		Time openTime = Time.valueOf("08:00:00");
        Time closeTime = Time.valueOf("18:00:00");
		String email = "info@fithub.com";
		String phone = "421-436-4444";
		String address = "2011, University Street, Montreal";
		
		SportCenter sportsCenter = new SportCenter();
        sportsCenter.setName(name);
        sportsCenter.setOpeningTime(openTime);
        sportsCenter.setClosingTime(closeTime);
        sportsCenter.setEmail(email);
        sportsCenter.setPhoneNumber(phone);
        sportsCenter.setAddress(address);

		sportsCenter = sportCenterRepo.save(sportsCenter);
        int centerId = sportsCenter.getId();

        SportCenter sportCenterFromDb = sportCenterRepo.findSportCenterById(centerId);

        assertNotNull(sportCenterFromDb);
        assertEquals(name, sportCenterFromDb.getName());
        assertEquals(openTime.toString(),sportCenterFromDb.getOpeningTime().toString());
        assertEquals(closeTime.toString(), sportCenterFromDb.getClosingTime().toString());
        assertEquals(email, sportCenterFromDb.getEmail());
        assertEquals(phone, sportCenterFromDb.getPhoneNumber());
        assertEquals(address, sportCenterFromDb.getAddress());
    }


    
}
