package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.model.Location;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LocationRepositoryTest {
	@Autowired
	private LocationRepository locationRepository;

	@AfterEach
	public void clearDatabase() {
		locationRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadLocation() {
        
        String floor = "aFloor";
        String room = "aRoom";

        Location location = new Location(floor, room, SportCenter.getSportCenter());

        Location savedLocation = locationRepository.save(location);

        // Retrieve location from the database
        Location locationFromDb = locationRepository.findLocationById(savedLocation.getId());

        //Assert that the information in the location association has been saved. 
        assertEquals(room, locationFromDb.getRoom());
        assertEquals(floor, locationFromDb.getFloor());

	}
}