package ca.mcgill.ecse321.sportcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired SportCenterManagementService sportCenterManagementService;
    
    //--------------------------// UserDetailsService overriding //--------------------------//

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(email);
        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        Owner owner = ownerRepository.findOwnerByEmail(email);

        if (customer != null) {
            return customer;
        }
        else if (instructor != null) {
            return instructor;
        }
        else if (owner != null) {
            return owner;
        }
        
        throw new UnsupportedOperationException("No account in the system exists with this email: " + email);
    }

    //--------------------------// Create Account //--------------------------//

    @Transactional
    public Customer createCustomerAccount(String email, String password, String name, String imageURL, String pronouns) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);

        if (pronouns.isEmpty()) {
            pronouns = "Any";
        }

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setPronouns(pronouns);
        customer.setCenter(toList(sportCenterRepository.findAll()).get(0));
        return customerRepository.save(customer);
    }
    
    @Transactional
    public Instructor createInstructorAccount(String email, String password, String name, String imageURL, String pronouns) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);

        if (pronouns.isEmpty()) {
            pronouns = "Any";
        }

        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(passwordEncoder.encode(password));
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setPronouns(pronouns);
        instructor.setCenter(toList(sportCenterRepository.findAll()).get(0));
        return instructorRepository.save(instructor);
    }

    @Transactional
    public Owner createOwnerAccount(String email, String password, String name, String imageURL, String pronouns) {
        validAccountInfo(email, password, name);
        uniqueEmail(email);

        if (pronouns.isEmpty()) {
            pronouns = "Any";
        }

        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(passwordEncoder.encode(password));
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(toList(sportCenterRepository.findAll()).get(0));
        return ownerRepository.save(owner);
    }
    
    //--------------------------// Update Account //--------------------------//
    
    @Transactional
    public Customer updateCustomerAccount(Integer id, String email, String password, String name, String imageURL, String pronouns) {
        validAccountInfo(email, password, name);

        if (pronouns.isEmpty()) {
            pronouns = "Any";
        }

        Customer customer = findCustomerById(id);

        // If it is the same email, then dont check if it is unique (it is not since it is used for this exact account), else verify
        if (!customer.getEmail().equalsIgnoreCase(email)) {
            uniqueEmail(email);
            customer.setEmail(email);
        }

        customer.setPassword(passwordEncoder.encode(password));
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(toList(sportCenterRepository.findAll()).get(0));
        return customerRepository.save(customer);
    }
    
    @Transactional
    public Instructor updateInstructorAccount(Integer id, String email, String password, String name, String imageURL, String pronouns) {
        validAccountInfo(email, password, name);

        if (pronouns.isEmpty()) {
            pronouns = "Any";
        }

        Instructor instructor = findInstructorById(id);

        // If it is the same email, then dont check if it is unique (it is not since it is used for this exact account), else verify
        if (!instructor.getEmail().equalsIgnoreCase(email)) {
            uniqueEmail(email);
            instructor.setEmail(email);
        }

        instructor.setEmail(email);
        instructor.setPassword(passwordEncoder.encode(password));
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setCenter(toList(sportCenterRepository.findAll()).get(0));
        return instructorRepository.save(instructor);
    }

    @Transactional
    public Owner updateOwnerAccount(Integer id, String email, String password, String name, String imageURL, String pronouns) {
        validAccountInfo(email, password, name);

        if (pronouns.isEmpty()) {
            pronouns = "Any";
        }
        
        Owner owner = findOwnerById(id);

        // If it is the same email, then dont check if it is unique (it is not since it is used for this exact account), else verify
        if (!owner.getEmail().equalsIgnoreCase(email)) {
            uniqueEmail(email);
            owner.setEmail(email);
        }

        owner.setEmail(email);
        owner.setPassword(passwordEncoder.encode(password));
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(toList(sportCenterRepository.findAll()).get(0));
        return ownerRepository.save(owner);
    }
    
    //--------------------------// Delete Account //--------------------------//
    
    @Transactional
    public void deleteCustomerAccount(Integer id) {
        Account account = findCustomerById(id);
        SportCenter sportCenter = sportCenterManagementService.getSportCenter();
        sportCenter.removeAccount(account);
        sportCenterRepository.save(sportCenter);

        account.delete();
        customerRepository.save((Customer) account);
        customerRepository.delete((Customer) account);
    }

    @Transactional
    public void deleteInstructorAccount(Integer id) {
        Account account = findInstructorById(id);
        SportCenter sportCenter = sportCenterManagementService.getSportCenter();
        sportCenter.removeAccount(account);
        sportCenterRepository.save(sportCenter);

        account.delete();
        instructorRepository.save((Instructor) account);
        instructorRepository.delete((Instructor) account);
    }

    @Transactional
    public void deleteOwnerAccount(Integer id) {
        Account account = findOwnerById(id);
        SportCenter sportCenter = sportCenterManagementService.getSportCenter();
        sportCenter.removeAccount(account);
        sportCenterRepository.save(sportCenter);

        account.delete();
        ownerRepository.save((Owner) account);
        ownerRepository.delete((Owner) account);
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