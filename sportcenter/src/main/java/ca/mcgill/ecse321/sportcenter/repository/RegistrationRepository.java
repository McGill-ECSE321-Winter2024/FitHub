package ca.mcgill.ecse321.sportcenter.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.sportcenter.model.Registration;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import java.util.List;

public interface RegistrationRepository extends CrudRepository<Registration, Registration.Key> {
    public Registration findRegistrationByKey(Registration.Key key);
    public List<Registration> findAllByKeySession(Session session);
    public List<Registration> findAllByKeyCustomer(Customer customer);
}
