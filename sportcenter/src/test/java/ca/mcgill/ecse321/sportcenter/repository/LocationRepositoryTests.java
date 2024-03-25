package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.sportcenter.model.Location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class provides test cases for the LocationRepository class.
 * It verifies the functionalities related to persisting and loading locations.
 */
@SpringBootTest
public class LocationRepositoryTests extends CommonTestSetup{
	@Autowired
	private LocationRepository locationRepository;

	/**
     * Test case to verify the persistence and loading of a location.
     */
	@Test
	public void testPersistAndLoadLocation() {
        
                String floor = "aFloor";
                String room = "aRoom";

                Location location = new Location();
                location.setFloor(floor);
                location.setRoom(room);
                location.setCenter(sportCenter);

                Location savedLocation = locationRepository.save(location);

                // Retrieve location from the database
                Location locationFromDb = locationRepository.findLocationById(savedLocation.getId());

                //Assert that the information in the location association has been saved. 
                assertNotNull(locationFromDb);
                assertEquals(room, locationFromDb.getRoom());
                assertEquals(floor, locationFromDb.getFloor());

	}
}