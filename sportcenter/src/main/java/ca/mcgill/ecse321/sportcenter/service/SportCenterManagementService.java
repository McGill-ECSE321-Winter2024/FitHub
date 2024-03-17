package ca.mcgill.ecse321.sportcenter.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Location;
import ca.mcgill.ecse321.sportcenter.model.Account;

/*
* <p>Service class in charge of managing accounts. It implements following use cases: </p>
* <p>Create, find, update, delete a sport center</p>
* @author James
*/
@Service
public class SportCenterManagementService {

    @Autowired
    private SportCenterRepository sportCenterRepository;

    //--------------------------// Create Sport Center //--------------------------//

    @Transactional
	public SportCenter createSportCenter(String name, Time openingTime, Time closingTime, String address, String email, String phoneNumber) {
        SportCenter sportCenter = sportCenterRepository.findSportCenterById(0);
        if (sportCenter != null) {
            throw new IllegalArgumentException("Sport center already exists.");
        }
        validSportCentertInfo(name, address, email, phoneNumber);
        validPhoneNumber(phoneNumber);
		SportCenter createdSportCenter = new SportCenter();
		createdSportCenter.setName(name);
        createdSportCenter.setOpeningTime(openingTime);
        createdSportCenter.setClosingTime(closingTime);
        createdSportCenter.setAddress(address);
        createdSportCenter.setEmail(email);
        createdSportCenter.setPhoneNumber(phoneNumber);
		sportCenterRepository.save(createdSportCenter);
		return createdSportCenter;
	}

    //--------------------------// Update Sport Center //--------------------------//

    @Transactional
    public SportCenter updateSportCenter(Time newOpeningTime, Time newClosingTime, String newAddress) {
        if (newAddress.isEmpty()) {
            throw new IllegalArgumentException("Empty address is not valid");
        }

        SportCenter sportCenter = sportCenterRepository.findSportCenterById(0);

        sportCenter.setOpeningTime(newOpeningTime);
        sportCenter.setClosingTime(newClosingTime);
        sportCenter.setAddress(newAddress);
        sportCenterRepository.save(sportCenter);

        return sportCenter;
    }

    //--------------------------// Delete Sport Center //--------------------------//

    @Transactional
    public void deleteSportCenter() {
        SportCenter sportCenter = sportCenterRepository.findSportCenterById(0);
        if (sportCenter == null) {
            throw new IllegalArgumentException("Sport center does not exist.");
        }
        sportCenterRepository.delete(sportCenter);
    }

    //--------------------------// Input Validation //--------------------------//

    private void validSportCentertInfo(String name, String address, String email, String phoneNumber) {
        if (name.isEmpty() || address.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Empty fields for name, address, email or phone number are not valid");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email has to contain the character @");
        }
    }

    private void validPhoneNumber(String phoneNumber) {
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i); 
            if (!Character.isDigit(c) && c != '-') {
                throw new IllegalArgumentException("Phone number has to contain digits and dashes only");
            }
        }
    }

}
