package ca.mcgill.ecse321.sportcenter.service;

import java.sql.Time;

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

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;

@SpringBootTest
public class SportCenterManagementServiceTests {

    @Mock
    private SportCenterRepository sportCenterRepository;

    @InjectMocks
    private SportCenterManagementService sportCenterManagementService;

    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        sportCenterRepository.deleteAll();
    }

    //--------------------------// Create Sport Center Tests //--------------------------//

    @Test
    public void testCreateValidSportCenter() {
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "a@Email";
        String phoneNumber = "1234567890";

        SportCenter sportCenter = newSportCenter(name, openingTime, closingTime, address, email, phoneNumber);

        when(sportCenterRepository.save(any(SportCenter.class))).thenReturn(sportCenter);

        SportCenter createdSportCenter = sportCenterManagementService.createSportCenter(name, openingTime, closingTime, address, email, phoneNumber);
        
        checkResultSportCenter(createdSportCenter, name, openingTime, closingTime, address, email, phoneNumber);
        verify(sportCenterRepository, times(1)).save(createdSportCenter);
    }

    @Test
    public void testCreateSportCenterWhenAlreadyExists() {
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "a@Email";
        String phoneNumber = "1234567890";
        String expectedMessage = "Sport center already exists.";

        SportCenter sportCenter = sportCenterManagementService.createSportCenter(name, openingTime, closingTime, address, email, phoneNumber);
        when(sportCenterRepository.findSportCenterById(0)).thenReturn(sportCenter);
        
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sportCenterManagementService.createSportCenter(name, openingTime, closingTime, address, email, phoneNumber));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void testCreateSportCenterWithEmptyName() {
        // Set up test
        String name = "";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "a@Email";
        String phoneNumber = "1234567890";
        String expectedError = "Empty fields for name, address, email or phone number are not valid";

        // Use Sport Center Management Service and Assert
        checkCreationErrorAssertion(name, openingTime, closingTime, address, email, phoneNumber, expectedError);
    }
    
    @Test
    public void testCreateSportCenterWithEmptyAddress() {
        // Set up test
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "";
        String email = "a@Email";
        String phoneNumber = "1234567890";
        String expectedError = "Empty fields for name, address, email or phone number are not valid";

        // Use Sport Center Management Service and Assert
        checkCreationErrorAssertion(name, openingTime, closingTime, address, email, phoneNumber, expectedError);
    }

    @Test
    public void testCreateSportCenterWithEmptyPhoneNumber() {
        // Set up test
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "a@Email";
        String phoneNumber = "";
        String expectedError = "Empty fields for name, address, email or phone number are not valid";

        // Use Sport Center Management Service and Assert
        checkCreationErrorAssertion(name, openingTime, closingTime, address, email, phoneNumber, expectedError);
    }

    @Test
    public void testCreateSportCenterWithEmptyEmail() {
        // Set up test
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "";
        String phoneNumber = "1234567890";
        String expectedError = "Empty fields for name, address, email or phone number are not valid";

        // Use Sport Center Management Service and Assert
        checkCreationErrorAssertion(name, openingTime, closingTime, address, email, phoneNumber, expectedError);
    }

    @Test
    public void testCreateSportCenterWithInvalidEmail() {
        // Set up test
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "aEmail";
        String phoneNumber = "1234567890";
        String expectedError = "Email has to contain the character @";

        // Use Sport Center Management Service and Assert
        checkCreationErrorAssertion(name, openingTime, closingTime, address, email, phoneNumber, expectedError);
    }

    @Test
    public void testCreateSportCenterWithInvalidPhoneNumber() {
        // Set up test
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "a@Email";
        String phoneNumber = "aPhoneNumber";
        String expectedError = "Phone number has to contain digits and dashes only";

        // Use Sport Center Management Service and Assert
        checkCreationErrorAssertion(name, openingTime, closingTime, address, email, phoneNumber, expectedError);
    }

    //--------------------------// Update Sport Center Tests //--------------------------//

    @Test
    public void testUpdateOpeningTime() {
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "a@Email";
        String phoneNumber = "aPhoneNumber";

        Time newOpeningTime = Time.valueOf("9:0:0");

        SportCenter sportCenter = newSportCenter(name, openingTime, closingTime, address, email, phoneNumber);

        when(sportCenterRepository.findSportCenterById(0)).thenReturn(sportCenter);
        when(sportCenterRepository.save(any(SportCenter.class))).thenReturn(sportCenter);

        SportCenter savedSportCenter = sportCenterManagementService.updateTime(newOpeningTime, closingTime);
        
        assertEquals(newOpeningTime, savedSportCenter.getOpeningTime());

    }


    @Test
    public void testUpdateClosingTime() {
        String name = "aName";
        Time openingTime = Time.valueOf("6:0:0");
        Time closingTime = Time.valueOf("0:0:0");
        String address = "aAddress";
        String email = "a@Email";
        String phoneNumber = "aPhoneNumber";

        Time newClosingTime = Time.valueOf("22:0:0");

        SportCenter sportCenter = newSportCenter(name, openingTime, closingTime, address, email, phoneNumber);

        when(sportCenterRepository.findSportCenterById(0)).thenReturn(sportCenter);
        when(sportCenterRepository.save(any(SportCenter.class))).thenReturn(sportCenter);

        SportCenter savedSportCenter = sportCenterManagementService.updateTime(openingTime, newClosingTime);
        
        assertEquals(newClosingTime, savedSportCenter.getClosingTime());
    }

    //--------------------------// Helper methods //--------------------------//

    /**
     * Create new sport center with input variables.
     */
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

    /**
     * Check validity of sport center properties with input variables.
     */
    private void checkResultSportCenter(SportCenter sportCenter, String name, Time openingTime, Time closingTime, String address, String email, String phoneNumber) {
        assertNotNull(sportCenter);
        assertEquals(name, sportCenter.getName());
        assertEquals(openingTime, sportCenter.getOpeningTime());
        assertEquals(closingTime, sportCenter.getClosingTime());
        assertEquals(address, sportCenter.getAddress());
        assertEquals(email, sportCenter.getEmail());
        assertEquals(phoneNumber, sportCenter.getPhoneNumber());
    }

    /**
     * Check error assertion and expected message when craeting invalid sport center.
     */
    private void checkCreationErrorAssertion(String name, Time openingTime, Time closingTime, String address, String email, String phoneNumber, String expectedMessage) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sportCenterManagementService.createSportCenter(name, openingTime, closingTime, address, email, phoneNumber));
        assertEquals(expectedMessage, e.getMessage());
    }
}
