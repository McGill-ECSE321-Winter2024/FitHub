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
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Customer;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;

public class LocationRepositoryTest {
    @Autowired
    private LocationRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadLocation() {
        
        // Create Location
        String floor = "1";
        String room = "1";
        Location location = new Location(floor, room, SportCenter.getSportCenter());

        // Save in the database
        location = repo.save(location);
        int locationId = location.getId();

        // Read back from the database
        Location locationFromDb = repo.findLocationById(locationId);

        // Assertions
        assertNotNull(locationFromDb);
        assertEquals(locationId, locationFromDb.getId());
        assertEquals(floor, locationFromDb.getFloor());
        assertEquals(room, locationFromDb.getRoom());
    }
}

