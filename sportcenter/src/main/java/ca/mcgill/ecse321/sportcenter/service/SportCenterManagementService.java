package ca.mcgill.ecse321.sportcenter.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

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
        validSportCentertInfo(name, address, email, phoneNumber);
		SportCenter sportCenter = new SportCenter();
		sportCenter.setName(name);
        sportCenter.setOpeningTime(openingTime);
        sportCenter.setClosingTime(closingTime);
        sportCenter.setAddress(address);
        sportCenter.setEmail(email);
        sportCenter.setPhoneNumber(phoneNumber);
		sportCenterRepository.save(sportCenter);
		return sportCenter;
	}

    //--------------------------// Find Sport Center(s) //--------------------------//

    @Transactional
    public SportCenter findSportCenterById(int id) {
        SportCenter sportCenter = sportCenterRepository.findSportCenterById(id);

        if (sportCenter == null) {
            throw new IllegalArgumentException("There is no sport center with ID " + id + ".");
        }

        return sportCenter;
    }

    @Transactional
    public List<SportCenter> getAllSportCenters() {
        return toList(sportCenterRepository.findAll());
    }

    @Transactional
    public SportCenter getSportCenter() {
        return getAllSportCenters().get(0);
    }

    //--------------------------// Update Sport Center //--------------------------//

    @Transactional
    public SportCenter updateOpeningTime(int id, Time openingTime) {
        SportCenter sportCenter = findSportCenterById(id);

        sportCenter.setOpeningTime(openingTime);
        sportCenterRepository.save(sportCenter);

        return sportCenter;
    }

    @Transactional
    public SportCenter updateClosingTime(int id, Time closingTime) {
        SportCenter sportCenter = findSportCenterById(id);

        sportCenter.setClosingTime(closingTime);
        sportCenterRepository.save(sportCenter);

        return sportCenter;
    }

    //--------------------------// Delete Sport Center //--------------------------//

    @Transactional
    public void deleteSportCenter(int id) {
        SportCenter sportCenter = findSportCenterById(id);
        sportCenterRepository.delete(sportCenter);
    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
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

}
