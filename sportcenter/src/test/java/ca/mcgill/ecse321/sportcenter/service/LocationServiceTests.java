package ca.mcgill.ecse321.sportcenter.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;

@SpringBootTest
public class LocationServiceTests {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private SportCenterRepository sportCenterRepository;

    @InjectMocks
    private LocationService locationService;

    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        locationRepository.deleteAll();
        sportCenterRepository.deleteAll();
    }

    /**
     * Create and save a SportCenter instance before each test.
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
        List<SportCenter> listSportCenter = new ArrayList<>();
        listSportCenter.add(sportCenter);
        when(sportCenterRepository.findAll()).thenReturn(listSportCenter);
    }

    //--------------------------// Create Location Tests //--------------------------//

    @Test
    public void testCreateValidLocation() {
        String floor = "2";
        String room = "200";
        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);
        location.setCenter(sportCenterRepository.findAll().get(0));

        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location createdLocation = locationService.createLocation(floor, room);

        assertNotNull(createdLocation);
        assertEquals(floor, createdLocation.getFloor());
        assertEquals(room, createdLocation.getRoom());
        verify(locationRepository, times(1)).save(createdLocation);
    }

    @Test
    public void testCreateLocationWithInvalidFloor() {   // Invalid floor
        String floor = "aFloor";
        String room = "100";
    
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> locationService.createLocation(floor, room));
        assertEquals("The floor or room number is invalid.", e.getMessage());
    }

    @Test
    public void testCreateLocationWithInvalidRoom() {   // Invalid room
        String floor = "4";
        String room = "-23";
    
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> locationService.createLocation(floor, room));
        assertEquals("The floor or room number is invalid.", e.getMessage());
    }

    @Test
    public void testCreateDuplicateLocation() {   // location with this floor and room already exists
        String floor = "4";
        String room = "401";

        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);
        location.setCenter(sportCenterRepository.findSportCenterById(0));

        when(locationRepository.findLocationByFloorAndRoom(floor, room)).thenReturn(location);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> locationService.createLocation(floor, room));
        assertEquals("The location already exists.", e.getMessage());
    }

    //--------------------------// Update Location Tests //--------------------------//

    @Test
    public void updateValidLocation() {
        int id = 33;
        String floor = "2";
        String room = "201";

        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);
        location.setCenter(sportCenterRepository.findAll().get(0));

        when(locationRepository.findLocationById(id)).thenReturn(location);

        String newFloor = "3";
        String newRoom = "301";
        Location updatedLocation = new Location();
        updatedLocation.setFloor(newFloor);
        updatedLocation.setRoom(newRoom);
        when(locationRepository.save(any(Location.class))).thenReturn(updatedLocation);

        Location savedLocation = locationService.updateLocation(id, newFloor, newRoom);

        verify(locationRepository, times(1)).findLocationById(id);
        verify(locationRepository, times(1)).save(any(Location.class));
        assertNotNull(savedLocation);
        assertEquals(newFloor, savedLocation.getFloor());
        assertEquals(newRoom, savedLocation.getRoom());
    }

    //--------------------------// Find Location Tests //--------------------------//

    @Test
    public void testReadLocationByValidId() {
        int id = 25;
        Location location = new Location();
        location.setFloor("aFloor");
        location.setRoom("aRoom");
        location.setCenter(sportCenterRepository.findAll().get(0));
        when(locationRepository.findLocationById(id)).thenReturn(location);

        Location foundLocation = locationService.findLocationById(id);

        assertNotNull(foundLocation);
        assertEquals(location.getFloor(), foundLocation.getFloor());
        assertEquals(location.getRoom(), foundLocation.getRoom());
    }

    @Test
    public void testReadLocationByInvalidId() {
        int id = 25;
        when(locationRepository.findLocationById(id)).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> locationService.findLocationById(id));
        assertEquals("There is no location with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testReadLocationByValidFloorAndRoom() {
        String floor = "aFloor";
        String room = "aRoom";
        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);
        location.setCenter(sportCenterRepository.findAll().get(0));
        when(locationRepository.findLocationByFloorAndRoom(floor, room)).thenReturn(location);

        Location foundLocation = locationService.findLocationByFloorAndRoom(floor, room);

        assertNotNull(foundLocation);
        assertEquals(location.getFloor(), foundLocation.getFloor());
        assertEquals(location.getRoom(), foundLocation.getRoom());

    }

    @Test
    public void testReadLocationByInvalidFloorOrRoom() {
        String floor = "aFloor";
        String room = "aRoom";

        when(locationRepository.findLocationByFloorAndRoom(floor, room)).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> locationService.findLocationByFloorAndRoom(floor, room));
        assertEquals("There is no location with floor " + floor + " or room " + room + ".", e.getMessage());
    }
}