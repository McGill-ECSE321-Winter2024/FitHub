package ca.mcgill.ecse321.sportcenter.service;

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

import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.repository.LocationRepository;

@SpringBootTest
public class LocationServiceTests {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    public void testCreateValidLocation() {
        String floor = "aFloor";
        String room = "aRoom";
        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);

        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location createdLocation = locationService.createLocation(floor, room);

        assertNotNull(createdLocation);
        assertEquals(floor, createdLocation.getFloor());
        assertEquals(room, createdLocation.getRoom());
        verify(locationRepository, times(1)).save(createdLocation);
    }

    @Test
    public void testReadLocationByValidId() {
        int id = 25;
        Location location = new Location();
        location.setFloor("aFloor");
        location.setRoom("aRoom");
        when(locationRepository.findLocationById(id)).thenReturn(location);

        Location foundLocation = locationService.findLocationById(id);

        assertNotNull(foundLocation);
        assertEquals(location.getFloor(), foundLocation.getFloor());
        assertEquals(location.getRoom(), foundLocation.getRoom());
    }

    @Test
    public void testReadLocationByValidFloorAndRoom() {
        String floor = "aFloor";
        String room = "aRoom";
        Location location = new Location();
        location.setFloor(floor);
        location.setRoom(room);
        when(locationRepository.findLocationByFloorAndRoom(floor, room)).thenReturn(location);

        Location foundLocation = locationService.findLocationByFloorAndRoom(floor, room);

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
    public void testReadLocationByInvalidFloorOrRoom() {
        String floor = "aFloor";
        String room = "aRoom";

        when(locationRepository.findLocationByFloorAndRoom(floor, room)).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> locationService.findLocationByFloorAndRoom(floor, room));
        assertEquals("There is no location with floor " + floor + " or room " + room + ".", e.getMessage());
    }
}