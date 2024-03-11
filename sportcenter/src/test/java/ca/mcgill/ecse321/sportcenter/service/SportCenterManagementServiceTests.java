package ca.mcgill.ecse321.sportcenter.service;

import java.sql.Time;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Answers.valueOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;

@SpringBootTest
public class SportCenterManagementServiceTests {

    @Mock
    private SportCenterRepository sportCenterRepository;

    @InjectMocks
    private SportCenterManagementService sportCenterManagementService;

    private SportCenter newSportCenter(String name, Time openingTime, Time closingTime, String address, String email, String phoneNumber) {
        SportCenter sportCenter = new SportCenter();
        sportCenter.setName(name);
        sportCenter.setOpeningTime(openingTime);
        sportCenter.setClosingTime(closingTime);
        sportCenter.setAddress(address);
        sportCenter.setEmail(email);
        sportCenter.setPhoneNumber(phoneNumber);
        return sportCenter;
    };

    private void checkResultSportCenter(SportCenter sportCenter, String name, Time openingTime, Time closingTime, String address, String email, String phoneNumber) {
        assertNotNull(sportCenter);
        assertEquals(name, sportCenter.getName());
        assertEquals(openingTime, sportCenter.getOpeningTime());
        assertEquals(closingTime, sportCenter.getClosingTime());
        assertEquals(address, sportCenter.getAddress());
        assertEquals(email, sportCenter.getEmail());
        assertEquals(phoneNumber, sportCenter.getPhoneNumber());
    }

    @Test
    public void testCreateValidSportCenter() {
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "aEmail";
        String phoneNumber = "aPhoneNumber";

        SportCenter sportCenter = newSportCenter(name, openingTime, closingTime, address, email, phoneNumber);

        when(sportCenterRepository.save(any(SportCenter.class))).thenReturn(sportCenter);

        SportCenter createdSportCenter = sportCenterManagementService.createSportCenter(name, openingTime, closingTime, address, email, phoneNumber);
        
        checkResultSportCenter(createdSportCenter, name, openingTime, closingTime, address, email, phoneNumber);
        verify(sportCenterRepository, times(1)).save(createdSportCenter);
    }

    @Test
    public void testUpdateOpeningTime() {
        int id = 1;
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "aEmail";
        String phoneNumber = "aPhoneNumber";

        SportCenter sportCenter = newSportCenter(name, openingTime, closingTime, address, email, phoneNumber);

        when(sportCenterRepository.findSportCenterById(id)).thenReturn(sportCenter);
        
        Time newOpeningTime = Time.valueOf("9:0:0");
        SportCenter updatedSportCenter = newSportCenter(name, newOpeningTime, closingTime, address, email, phoneNumber);
        
        when(sportCenterRepository.save(any(SportCenter.class))).thenReturn(updatedSportCenter);

        SportCenter savedSportCenter = sportCenterManagementService.updateOpeningTime(id, newOpeningTime);
        
        assertEquals(newOpeningTime, savedSportCenter.getOpeningTime());
    }

    @Test
    public void testUpdateClosingTime() {
        int id = 1;
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "aEmail";
        String phoneNumber = "aPhoneNumber";

        SportCenter sportCenter = newSportCenter(name, openingTime, closingTime, address, email, phoneNumber);

        when(sportCenterRepository.findSportCenterById(id)).thenReturn(sportCenter);
        
        Time newClosingTime = Time.valueOf("22:0:0");
        SportCenter updatedSportCenter = newSportCenter(name, openingTime, newClosingTime, address, email, phoneNumber);
        
        when(sportCenterRepository.save(any(SportCenter.class))).thenReturn(updatedSportCenter);

        SportCenter savedSportCenter = sportCenterManagementService.updateClosingTime(id, newClosingTime);
        
        assertEquals(newClosingTime, savedSportCenter.getClosingTime());
    }

    @Test
    public void testReadSportCenterByValidId() {
        int id = 1;
        SportCenter sportCenter = newSportCenter("aName", Time.valueOf("6:0:0"), Time.valueOf("0:0:0"), "aAddress", "aEmail", "aPhoneNumber");
        
        when(sportCenterRepository.findSportCenterById(id)).thenReturn(sportCenter);

        SportCenter foundSportCenter = sportCenterManagementService.findSportCenterById(id);
        checkResultSportCenter(foundSportCenter, sportCenter.getName(), sportCenter.getOpeningTime(), sportCenter.getClosingTime(), sportCenter.getAddress(), sportCenter.getEmail(), sportCenter.getPhoneNumber());
    }

    @Test
    public void testReadSportCenterByInvalidId() {
        int id = 1;
        when(sportCenterRepository.findSportCenterById(id)).thenReturn(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sportCenterManagementService.findSportCenterById(id));
        assertEquals("There is no sport center with ID " + id + ".", e.getMessage());
    }


}
