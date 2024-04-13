package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.repository.RegistrationRepository;

/*
* <p>Service class in charge of managing registrations. It implements following use cases: </p>
* <p>Create, update, delete a registration as well as the use case of registering to a session.</p>
* @author Tayba
*/
@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    //--------------------------// Create Registration //--------------------------//

    @Transactional
    public Registration createRegistration(Customer customer, Session session) {
        Registration existingRegistration = findRegistration(customer, session);
        if (existingRegistration != null) {
            throw new IllegalArgumentException("Customer " + customer.getId() + " has already registered for session " + session.getId());
        }

        Registration.Key key = new Registration.Key(customer, session);
        Registration registration = new Registration(key);
        registrationRepository.save(registration);
        return registration;
    }

    //--------------------------// Update Registration //--------------------------//
    
    @Transactional
    public Registration updateRegistration(Customer customer, Session session) {
        Registration registration = findRegistration(customer, session);
        registration.getKey().setCustomer(customer);
        registration.getKey().setSession(session);
        return registrationRepository.save(registration);
    }

    //--------------------------// Delete Registration //--------------------------//
    
    @Transactional
    public void deleteRegistration(Registration.Key key) {
        registrationRepository.delete(findRegistrationByKey(key));
    }

    @Transactional
    public boolean cancelRegistration(Registration registration) {
        if (registration == null) {
            return false;
        }
        deleteRegistration(registration.getKey());
        return true;
    }

    //--------------------------// Getters //--------------------------//

    @Transactional
    public Registration findRegistrationByKey(Registration.Key key) {
        Registration registration = registrationRepository.findRegistrationByKey(key);

        if (registration == null) {
            throw new IllegalArgumentException("There is no registration with key " + key + ".");
        }
        
        return registration;
    }

    @Transactional
    public Registration findRegistration(Integer customerId, Integer sessionId) {
        Customer customer = accountService.findCustomerById(customerId);
        Session session = sessionService.findSessionById(sessionId);
        return findRegistration(customer, session);
    }

    @Transactional
    public List<Registration> getAllRegistrations() {
        return toList(registrationRepository.findAll());
    }

    @Transactional
    public List<Registration> getAllRegistrationsFromSession(Session session) {
        return toList(registrationRepository.findAllByKeySession(session));
    }

    @Transactional
    public List<Registration> getAllRegistrationsFromCustomer(Customer customer) {
        return toList(registrationRepository.findAllByKeyCustomer(customer));
    }  

    //--------------------------// Helper functions //--------------------------//

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    private Registration findRegistration(Customer customer, Session session) {
        return registrationRepository.findRegistrationByKey(new Registration.Key(customer, session));
    }


}