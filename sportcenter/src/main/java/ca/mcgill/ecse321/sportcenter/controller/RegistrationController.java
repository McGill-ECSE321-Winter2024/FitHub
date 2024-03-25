package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.CustomerResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationListDTO;
import ca.mcgill.ecse321.sportcenter.dto.RegistrationResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionListDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionResponseDTO;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.RegistrationService;
import ca.mcgill.ecse321.sportcenter.service.SessionService;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.Session;

/**
 * <p>Controller class in charge of managing registrations. It implements following use cases: </p>
 * <p>Create, update, read and delete a registration </p>
 * @author Tayba
*/
@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    //--------------------------// Create Registration //--------------------------//

    @PostMapping(value= {"/registrations", "/registrations/"})
    public ResponseEntity<RegistrationResponseDTO> createRegistration(@RequestParam Integer customerId, @RequestParam Integer sessionId) {
        try{
            Customer customer = accountService.findCustomerById(customerId);
            Session session = sessionService.findSessionById(sessionId);
            Registration createdRegistration = registrationService.createRegistration(customer, session);
            return new ResponseEntity<>(new RegistrationResponseDTO(createdRegistration), HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(new RegistrationResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Update Registration //--------------------------//

    @PutMapping(value = {"/registrations", "/registrations/"})
    public ResponseEntity<RegistrationResponseDTO> updateRegistration(@RequestParam Integer customerId, @RequestParam Integer sessionId) {
        try{
            Customer customer = accountService.findCustomerById(customerId);
            Session session = sessionService.findSessionById(sessionId);
            Registration updatedRegistration = registrationService.updateRegistration(customer, session);
            return new ResponseEntity<>(new RegistrationResponseDTO(updatedRegistration), HttpStatus.ACCEPTED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(new RegistrationResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Read Registration //--------------------------//

    @GetMapping(value={"/registrations/{key}", "/registrations/{key}/"})
    public ResponseEntity<RegistrationResponseDTO> findRegistrationByKey(@PathVariable Registration.Key key) {
        try{
            return new ResponseEntity<>(new RegistrationResponseDTO(registrationService.findRegistrationByKey(key)), HttpStatus.FOUND);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(new RegistrationResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = {"/registrations", "/registrations/"})
    public ResponseEntity<RegistrationListDTO> getAllRegistrations() {
        List<RegistrationResponseDTO> registrations = new ArrayList<>();
        try{
            for (Registration registration: registrationService.getAllRegistrations()) {
                registrations.add(new RegistrationResponseDTO(registration));
            }

            RegistrationListDTO registrationList = new RegistrationListDTO(registrations);
            if (registrationList.getRegistrations().size() > 0) {
                return new ResponseEntity<>(registrationList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(registrationList, HttpStatus.NO_CONTENT);
            }
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(new RegistrationListDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = {"/sessions/{sessionId}/customers", "/sessions/{sessionId}/customers/"})
    public ResponseEntity<AccountListDTO> getAllCustomersFromSession(@PathVariable Integer sessionId) {
        List<AccountResponseDTO> customers = new ArrayList<>();
        try{
            Session session = sessionService.findSessionById(sessionId);
            for (Registration registration: registrationService.getAllRegistrationsFromSession(session)) {
                customers.add(new CustomerResponseDTO(registration.getKey().getCustomer()));
            }

            AccountListDTO customerList = new AccountListDTO(customers);
            if (customerList.getAccounts().size() > 0) {
                return new ResponseEntity<>(customerList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(customerList, HttpStatus.NO_CONTENT);
            }
         }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(new AccountListDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = {"/customers/{customerId}/sessions", "/customers/{customerId}/sessions/"})
    public ResponseEntity<SessionListDTO> getAllSessionsFromCustomer(@PathVariable Integer customerId) {
        List<SessionResponseDTO> sessions = new ArrayList<>();
        try{
            Customer customer = accountService.findCustomerById(customerId);
            for (Registration registration: registrationService.getAllRegistrationsFromCustomer(customer)) {
                sessions.add(new SessionResponseDTO(registration.getKey().getSession()));
            }

            SessionListDTO sessionList = new SessionListDTO(sessions);
            if (sessionList.getSessions().size() > 0) {
                return new ResponseEntity<>(sessionList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(sessionList, HttpStatus.NO_CONTENT);
            }
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>( new SessionListDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Delete Registration //--------------------------//

    @DeleteMapping(value = {"/registrations/{customerId}/{sessionId}", "/registrations/{customerId}/{sessionId}/"})
    public ResponseEntity<Void> cancelRegistration(@PathVariable Integer customerId, @PathVariable Integer sessionId) {
        Registration registration = registrationService.findRegistration(customerId, sessionId);
        boolean deletionSuccessful = registrationService.cancelRegistration(registration);
        if (deletionSuccessful) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
