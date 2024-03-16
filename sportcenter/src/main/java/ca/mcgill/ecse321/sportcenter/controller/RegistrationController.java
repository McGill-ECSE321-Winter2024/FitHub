package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.RegistrationResponseDTO;
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
@RestController
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/register")
    public RegistrationResponseDTO register(@RequestParam Integer customerId, @RequestParam Integer sessionId) {
        Registration registration = registrationService.register(customerId, sessionId);
        return new RegistrationResponseDTO(registration);
    }

    @DeleteMapping("/registration/cancel")
    public void cancelRegistration(@RequestParam Integer customerId, @RequestParam Integer sessionId) {
        Registration registration = registrationService.findRegistration(customerId, sessionId);
        registrationService.cancelRegistration(registration);
    }

    @GetMapping("/registration/{customerId}/{sessionId}")
    public RegistrationResponseDTO getRegistration(@PathVariable Integer customerId, @PathVariable Integer sessionId) {
        Registration registration = registrationService.findRegistration(customerId, sessionId);
        return new RegistrationResponseDTO(registration);
    }

    @PutMapping("/registration/update")
    public RegistrationResponseDTO updateRegistration(@RequestParam Integer customerId, @RequestParam Integer sessionId) {
        Customer customer = accountService.findCustomerById(customerId);
        Session session = sessionService.findSessionById(sessionId);
        Registration registration = registrationService.updateRegistration(customer, session);
        return new RegistrationResponseDTO(registration);
    }

    @PostMapping(value = {"/registration/new"})
    public RegistrationResponseDTO createRegistration(@RequestParam Integer customerId, @RequestParam Integer sessionId) {
        Customer newCustomer = accountService.findCustomerById(customerId);
        Session newSession = sessionService.findSessionById(sessionId);
        Registration registration = registrationService.createRegistration(newCustomer, newSession);
        return new RegistrationResponseDTO(registration);
    }
}
