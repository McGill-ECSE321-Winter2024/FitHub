package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.model.Location;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class provides test cases for the LocationRepository class.
 * It verifies the functionalities related to persisting and loading locations.
 */
@SpringBootTest
public class LocationRepositoryTests {
	@Autowired
	private LocationRepository locationRepository;
        @Autowired
        private SportCenterRepository sportCenterRepo;

        private SportCenter sportCenter;

	/**
     * Method to clear the database after each test.
     */
	@AfterEach
	public void clearDatabase() {
		locationRepository.deleteAll();
                sportCenterRepo.deleteAll();
        }

        /**
         * Method to create and save a sport center before each test.
         */
        @BeforeEach
        public void createAndSaveSportCenter() {
                SportCenter sportCenter = new SportCenter();
                sportCenter.setName("FitHub");
                sportCenter.setOpeningTime(Time.valueOf("08:00:00"));
                sportCenter.setClosingTime(Time.valueOf("18:00:00"));
                sportCenter.setEmail("info@fithub.com");
                sportCenter.setPhoneNumber("421-436-4444");
                sportCenter.setAddress("2011, University Street, Montreal");

                // Save sportCenterRepo
                sportCenter = sportCenterRepo.save(sportCenter);
        }

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

        @Test
        public void testPersistAndLoadLocation2() {
                String floor = "aFloor";
                String room = "aRoom";

                Location location = new Location();
                location.setFloor(floor);
                location.setRoom(room);
                location.setCenter(sportCenter);

                Location savedLocation = locationRepository.save(location);

                Location locationFromDb = locationRepository.findLocationByFloorAndRoom(savedLocation.getFloor(), savedLocation.getRoom());

                assertNotNull(locationFromDb);
                assertEquals(room, locationFromDb.getRoom());
                assertEquals(floor, locationFromDb.getFloor());
        }
}
