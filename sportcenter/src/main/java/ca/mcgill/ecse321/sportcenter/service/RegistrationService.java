package ca.mcgill.ecse321.sportcenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.sportcenter.repository.RegistrationRepository;

/*
* <p>Service class in charge of managing locations. It implements following use cases: </p>
* <p>Create, update, delete a location.</p>
* @author Tayba
*/
@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private AccountService accountService;
}
