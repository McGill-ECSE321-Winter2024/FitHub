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
import org.springframework.test.context.TestExecutionListeners;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Customer;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;

@SpringBootTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository repo;
    @Autowired
    private SportCenterRepository sportCenterRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadOwner() {
        // Save sportCenterRepo
        SportCenter sportCenter = sportCenterRepo.save(SportCenter.getSportCenter());

        // Create the owner 
        String email = "Jumijabasali@info.com";
        String password = "sportcenter";
        String name = "Jumijabasali";
        String imageURL = "pfp.com";
        Owner owner = new Owner(email, password, name, imageURL, sportCenter);
        
        // Save into database
        owner = repo.save(owner);
        Integer ownerId = owner.getId();
        
        // Read back from database
        Owner ownerDb = (Owner) repo.findAccountById(owner.getId());

        assertNotNull(ownerDb);
        assertEquals(email, ownerDb.getEmail());
        assertEquals(password, ownerDb.getPassword());
        assertEquals(name, ownerDb.getName());
        assertEquals(imageURL, ownerDb.getImageURL());
    }
}
