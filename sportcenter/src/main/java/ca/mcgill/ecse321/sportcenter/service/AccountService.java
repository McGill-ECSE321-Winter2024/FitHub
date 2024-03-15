package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.AccountPrincipal;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;
import jakarta.transaction.Transactional;

/*
* <p>Service class in charge of managing accounts. It implements following use cases: </p>
* <p>Create, update, delete a customer/instructor/owner account and login to account</p>
* @author Julia
*/
@Service("userDetailsService")
public class AccountService implements UserDetailsService {
    //get user from the database, via Hibernate
    
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    OwnerRepository ownerRepository;
    
    @Autowired
    SportCenterRepository sportCenterRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(email);
        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        Owner owner = ownerRepository.findOwnerByEmail(email);

        if (customer != null) {
            return new AccountPrincipal(customer);
        }
        else if (instructor != null) {
            return new AccountPrincipal(instructor);
        }
        else if (owner != null) {
            return new AccountPrincipal(owner);
        }
        
        throw new UnsupportedOperationException("No account in the system exists with this email");
    }

    //--------------------------// Create Account //--------------------------//

    @Transactional
    public Customer createCustomerAccount(String email, String password, String name, String imageURL) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(sportCenterRepository.findSportCenterById(0));
        return customerRepository.save(customer);
    }
    
    @Transactional
    public Instructor createInstructorAccount(String email, String password, String name, String imageURL) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);

        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setCenter(sportCenterRepository.findSportCenterById(0));
        return instructorRepository.save(instructor);
    }

    @Transactional
    public Owner createOwnerAccount(String email, String password, String name, String imageURL) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);

        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(sportCenterRepository.findSportCenterById(0));
        return ownerRepository.save(owner);
    }
    
    //--------------------------// Update Account //--------------------------//
    
    @Transactional
    public Customer updateCustomerAccount(Integer id, String email, String password, String name, String imageURL) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);

        Customer customer = findCustomerById(id);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        return customerRepository.save(customer);
    }
    
    @Transactional
    public Instructor updateInstructorAccount(Integer id, String email, String password, String name, String imageURL) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);

        Instructor instructor = findInstructorById(id);
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        return instructorRepository.save(instructor);
    }

    @Transactional
    public Owner updateOwnerAccount(Integer id, String email, String password, String name, String imageURL) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);
        
        Owner owner = findOwnerById(id);
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setName(name);
        owner.setImageURL(imageURL);
        return ownerRepository.save(owner);
    }
    
    //--------------------------// Delete Account //--------------------------//
    
    @Transactional
    public void deleteCustomerAccount(Integer id) {
        customerRepository.delete(findCustomerById(id));
    }

    @Transactional
    public void deleteInstructorAccount(Integer id) {
        instructorRepository.delete(findInstructorById(id));
    }

    @Transactional
    public void deleteOwnerAccount(Integer id) {
        ownerRepository.delete(findOwnerById(id));
    }
    
    //--------------------------// Login to Account //--------------------------//

    /*
     * <p>Verify that the account with the email and the associated password exists in the system</p>
     * @param email of the account and its password
     * @return an error if no account in the system exist with this email and password else, returns the type of account it is
     * @author Julia
     */
    @Transactional
    public String loginToAccount(String email, String password) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        Owner owner = ownerRepository.findOwnerByEmail(email);

        if (customer != null && customer.getPassword().equals(password)) {
            return "customer";
        }
        else if (instructor != null && customer.getPassword().equals(password)) {
            return "instructor";
        }
        else if (owner != null && owner.getPassword().equals(password)) {
            return "owner";
        }
        
        throw new IllegalArgumentException("No account in the system exists with this email and password");
    }

    //--------------------------// Getters //--------------------------//

    @Transactional
	public Customer findCustomerById(Integer id) {
		Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new IllegalArgumentException("There is no customer with ID " + id + ".");
        }
		return customer;
	}

    @Transactional
	public Customer findCustomerByEmail(String email) {
		Customer customer = customerRepository.findCustomerByEmail(email.toLowerCase());
        if (customer == null) {
            throw new IllegalArgumentException("There is no customer with email " + email + ".");
        }
		return customer;
	}

    @Transactional
	public List<Customer> findAllCustomers() {
		return toList(customerRepository.findAll());
	}

    @Transactional
	public Instructor findInstructorById(Integer id) {
		Instructor instructor = instructorRepository.findInstructorById(id);
        if (instructor == null) {
            throw new IllegalArgumentException("There is no instructor with ID " + id + ".");
        }
		return instructor;
	}

    @Transactional
	public Instructor findInstructorByEmail(String email) {
		Instructor instructor = instructorRepository.findInstructorByEmail(email.toLowerCase());
        if (instructor == null) {
            throw new IllegalArgumentException("There is no instructor with email " + email + ".");
        }
		return instructor;
	}

    @Transactional
	public List<Instructor> findAllInstructors() {
		return toList(instructorRepository.findAll());
	}

    @Transactional
	public Owner findOwnerById(Integer id) {
		Owner owner = ownerRepository.findOwnerById(id);
        if (owner == null) {
            throw new IllegalArgumentException("There is no owner with ID " + id + ".");
        }
		return owner;
	}

    @Transactional
	public Owner findOwnerByEmail(String email) {
		Owner owner = ownerRepository.findOwnerByEmail(email.toLowerCase());
        if (owner == null) {
            throw new IllegalArgumentException("There is no owner with email " + email + ".");
        }
		return owner;
	}

    @Transactional
	public List<Owner> findAllOwners() {
		return toList(ownerRepository.findAll());
	}

    @Transactional
    public List<Account> findAllAccounts() {
        ArrayList<Account> accountsList = new ArrayList<>();
        addToAccountsList(accountsList, findAllCustomers());
        addToAccountsList(accountsList, findAllInstructors());
        addToAccountsList(accountsList, findAllOwners());
        return accountsList;
    }

    //--------------------------// Helper functions //--------------------------//

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    
    private <T> List<Account> addToAccountsList(ArrayList<Account> accountsList, Iterable<T> iterable){
		for (T t : iterable) {
			accountsList.add((Account) t);
		}
		return accountsList;
	}

    //--------------------------// Input validations //--------------------------//

    private void validAccountInfo(String email, String password, String name) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            throw new IllegalArgumentException("Empty fields for email, password or name are not valid");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email has to contain the character @");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("The password needs to have 8 characters or more");
        }
    }

    private void uniqueEmail(String email) {
        if (customerRepository.findCustomerByEmail(email) != null ||
            instructorRepository.findInstructorByEmail(email) != null ||
            ownerRepository.findOwnerByEmail(email) != null) {

            throw new IllegalArgumentException("The email is already associated to an account");
        }
    }
}
