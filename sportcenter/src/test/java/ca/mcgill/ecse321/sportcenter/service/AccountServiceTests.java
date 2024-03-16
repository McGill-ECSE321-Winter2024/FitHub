package ca.mcgill.ecse321.sportcenter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.OwnerRepository;
import ca.mcgill.ecse321.sportcenter.repository.SportCenterRepository;

@SpringBootTest
public class AccountServiceTests {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private SportCenterRepository sportCenterRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService accountService;

    @InjectMocks
    private SportCenterManagementService sportCenterManagementService;
    
    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        instructorRepository.deleteAll();
        ownerRepository.deleteAll();
        sportCenterRepository.deleteAll();
    }

    /**
     * Create and save a SportCenter instance before each test.
     */
    @BeforeEach
    public void createAndSaveSportCenter() {
        SportCenter sportCenter = new SportCenter();
        sportCenter.setName("FitHub");
        sportCenter.setOpeningTime(Time.valueOf("08:00:00"));
        sportCenter.setClosingTime(Time.valueOf("18:00:00"));
        sportCenter.setEmail("info@fithub.com");
        sportCenter.setPhoneNumber("421-436-4444");
        sportCenter.setAddress("2011, University Street, Montreal");

        // Save sportCenterRepo
        List<SportCenter> listSportCenter = new ArrayList<>();
        listSportCenter.add(sportCenter);
        when(sportCenterRepository.findAll()).thenReturn(listSportCenter);
    }

    
    //--------------------------// Create Account Tests //--------------------------//

    @Test
    public void testCreateValidCustomerAccount() {
        // Set up test
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String imageURL = "pfp.png";
        
        Customer julia = new Customer();
        julia.setEmail(email);
        julia.setPassword(password);
        julia.setName(name);
        julia.setImageURL(imageURL);
        julia.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(customerRepository.save(any(Customer.class))).thenReturn(julia);

        // Act
        Customer createdCustomer = accountService.createCustomerAccount(email, password, name, imageURL);
    
        // Assert
        assertNotNull(createdCustomer);
        assertEquals(email.toLowerCase(), createdCustomer.getEmail());
        assertEquals(password, createdCustomer.getPassword());
        assertEquals(name, createdCustomer.getName());
        assertEquals(imageURL, createdCustomer.getImageURL());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
    
    @Test
    public void testCreateValidInstructorAccount() {
        // Set up test
        String email = "olivia@mail.com";
        String password = "secretPassword";
        String name = "Olivia";
        String imageURL = "pfp.png";
        
        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor createdInstructor = accountService.createInstructorAccount(email, password, name, imageURL);
    
        // Assert
        assertNotNull(createdInstructor);
        assertEquals(email.toLowerCase(), createdInstructor.getEmail());
        assertEquals(password, createdInstructor.getPassword());
        assertEquals(name, createdInstructor.getName());
        assertEquals(imageURL, createdInstructor.getImageURL());
        verify(instructorRepository, times(1)).save(any(Instructor.class));
    }

    @Test
    public void testCreateValidOwnerAccount() {
        // Set up test
        String email = "manager@mail.com";
        String password = "secretPassword";
        String name = "Lory";
        String imageURL = "pfp.png";
        
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        // Use the AccountService
        Owner createdOwner = accountService.createOwnerAccount(email, password, name, imageURL);
    
        // Assert
        assertNotNull(createdOwner);
        assertEquals(email.toLowerCase(), createdOwner.getEmail());
        assertEquals(password, createdOwner.getPassword());
        assertEquals(name, createdOwner.getName());
        assertEquals(imageURL, createdOwner.getImageURL());
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    public void testCreateInvalidCustomerAccount() { // No password
        // Set up test
        String email = "julia@mail.com";
        String password = "";
        String name = "Julia";
        String imageURL = "pfp.png";
        
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(toList(sportCenterRepository.findAll()).get(0));

        // Use Account Service and Assert
        assertThrows(IllegalArgumentException.class, () -> accountService.createCustomerAccount(email, password, name, imageURL));
    }
    
    @Test
    public void testCreateInvalidInstructorAccount() { // Invalid email address
        // Set up test
        String email = "oliviamail.com";
        String password = "secretPassword";
        String name = "Olivia";
        String imageURL = "pfp.png";
        
        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setCenter(toList(sportCenterRepository.findAll()).get(0));

        // Use Account Service and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> accountService.createOwnerAccount(email, password, name, imageURL));
        assertEquals("Email has to contain the character @", e.getMessage());
    }

    @Test
    public void testCreateInvalidOwnerAccount() { // No name
        // Set up test
        String email = "manager@mail.com";
        String password = "secretPassword";
        String name = "";
        String imageURL = "pfp.png";
        
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(toList(sportCenterRepository.findAll()).get(0));

        // Use Account Service and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> accountService.createOwnerAccount(email, password, name, imageURL));
        assertEquals("Empty fields for email, password or name are not valid", e.getMessage());
    }

    @Test
    public void testCreateInvalidDuplicateAccount() { // email already associated to an account
        // Set up test
        String email = "manager@mail.com";
        String password = "secretPassword";
        String name = "Thomas";
        String imageURL = "pfp.png";

        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        
        // Use Account Service and Assert
        accountService.createInstructorAccount(email, password, name, imageURL);
        
        when(instructorRepository.findInstructorByEmail(email)).thenReturn(instructor);
        assertThrows(IllegalArgumentException.class, () -> accountService.createOwnerAccount(email, password, name, imageURL));
    }

    //--------------------------// Update Account Tests //--------------------------//

    @Test
    public void testUpdateValidCustomerAccount() {
        // Set up test
        int id = 64;
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String imageURL = "pfp.png";
        
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(customerRepository.findCustomerById(id)).thenReturn(customer);

        // Use the AccountService
        String newEmail = "new@mail.com";
        String newPassword = "newPassword";
        String newName = "Lory";
        String newURL = "pfp.jpeg";
        Customer updatedCustomer = new Customer();
        updatedCustomer.setEmail(newEmail);
        updatedCustomer.setPassword(newPassword);
        updatedCustomer.setName(newName);
        updatedCustomer.setImageURL(newURL);
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer savedCustomer = accountService.updateCustomerAccount(id, newEmail, newPassword, newName, newURL);
    
        // Assert
        verify(customerRepository, times(1)).findCustomerById(id);
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertNotNull(savedCustomer);
        assertEquals(newEmail.toLowerCase(), savedCustomer.getEmail());
        assertEquals(newPassword, savedCustomer.getPassword());
        assertEquals(newName, savedCustomer.getName());
        assertEquals(newURL, savedCustomer.getImageURL());
    }

    @Test
    public void testUpdateValidInstructorAccount() {
        // Set up test
        int id = 64;
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String imageURL = "pfp.png";
        
        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(instructorRepository.findInstructorById(id)).thenReturn(instructor);

        // Use the AccountService
        String newEmail = "new@mail.com";
        String newPassword = "newPassword";
        String newName = "Lory";
        String newURL = "pfp.jpeg";
        Instructor updatedInstructor = new Instructor();
        updatedInstructor.setEmail(newEmail);
        updatedInstructor.setPassword(newPassword);
        updatedInstructor.setName(newName);
        updatedInstructor.setImageURL(newURL);
        when(instructorRepository.save(any(Instructor.class))).thenReturn(updatedInstructor);

        Instructor savedInstructor = accountService.updateInstructorAccount(id, newEmail, newPassword, newName, newURL);
    
        // Assert
        verify(instructorRepository, times(1)).findInstructorById(id);
        verify(instructorRepository, times(1)).save(any(Instructor.class));
        assertNotNull(savedInstructor);
        assertEquals(newEmail.toLowerCase(), savedInstructor.getEmail());
        assertEquals(newPassword, savedInstructor.getPassword());
        assertEquals(newName, savedInstructor.getName());
        assertEquals(newURL, savedInstructor.getImageURL());
    }

    @Test
    public void testUpdateValidOwnerAccount() {
        // Set up test
        int id = 64;
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String imageURL = "pfp.png";
        
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(ownerRepository.findOwnerById(id)).thenReturn(owner);

        // Use the AccountService
        String newEmail = "new@mail.com";
        String newPassword = "newPassword";
        String newName = "Lory";
        String newURL = "pfp.jpeg";
        Owner updatedOwner = new Owner();
        updatedOwner.setEmail(newEmail);
        updatedOwner.setPassword(newPassword);
        updatedOwner.setName(newName);
        updatedOwner.setImageURL(newURL);
        when(ownerRepository.save(any(Owner.class))).thenReturn(updatedOwner);

        Owner savedOwner = accountService.updateOwnerAccount(id, newEmail, newPassword, newName, newURL);
    
        // Assert
        verify(ownerRepository, times(1)).findOwnerById(id);
        verify(ownerRepository, times(1)).save(any(Owner.class));
        assertNotNull(savedOwner);
        assertEquals(newEmail.toLowerCase(), savedOwner.getEmail());
        assertEquals(newPassword, savedOwner.getPassword());
        assertEquals(newName, savedOwner.getName());
        assertEquals(newURL, savedOwner.getImageURL());
    }

    //--------------------------// Find Account Tests //--------------------------//

    @Test
    public void testReadCustomerByValidId() {
        // Set up test
        int id = 64;
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String imageURL = "pfp.png";
        
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(customerRepository.findCustomerById(id)).thenReturn(customer);

        // Use the AccountService
        Customer foundCustomer = accountService.findCustomerById(id);
    
        // Assert
        assertNotNull(foundCustomer);
        assertEquals(email.toLowerCase(), foundCustomer.getEmail());
        assertEquals(password, foundCustomer.getPassword());
        assertEquals(name, foundCustomer.getName());
        assertEquals(imageURL, foundCustomer.getImageURL());
    }
    
    @Test
    public void testReadInstructorByValidId() {
        // Set up test
        int id = 64;
        String email = "kitKat@mail.com";
        String password = "secretPassword";
        String name = "Kitkat";
        String imageURL = "pfp.png";
        
        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(instructorRepository.findInstructorById(id)).thenReturn(instructor);

        // Use the AccountService
        Instructor foundInstructor = accountService.findInstructorById(id);
    
        // Assert
        assertNotNull(foundInstructor);
        assertEquals(email.toLowerCase(), foundInstructor.getEmail());
        assertEquals(password, foundInstructor.getPassword());
        assertEquals(name, foundInstructor.getName());
        assertEquals(imageURL, foundInstructor.getImageURL());
    }
    
    @Test
    public void testReadOwnerByValidId() {
        // Set up test
        int id = 64;
        String email = "Watermelon@mail.com";
        String password = "secretPassword";
        String name = "Water Melon";
        String imageURL = "pfp.png";
        
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(ownerRepository.findOwnerById(id)).thenReturn(owner);

        // Use the AccountService
        Owner foundOwner = accountService.findOwnerById(id);
    
        // Assert
        assertNotNull(foundOwner);
        assertEquals(email.toLowerCase(), foundOwner.getEmail());
        assertEquals(password, foundOwner.getPassword());
        assertEquals(name, foundOwner.getName());
        assertEquals(imageURL, foundOwner.getImageURL());
    }

    
    @Test
    public void testReadCustomerByInvalidId() {
        // Set up test
        int id = 64;

        when(customerRepository.findCustomerById(id)).thenReturn(null);

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> accountService.findCustomerById(id));
        assertEquals("There is no customer with ID " + id + ".", e.getMessage());
    }
    
    @Test
    public void testReadInstructorByInvalidId() {
        // Set up test
        int id = 64;

        when(instructorRepository.findInstructorById(id)).thenReturn(null);

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> accountService.findInstructorById(id));
        assertEquals("There is no instructor with ID " + id + ".", e.getMessage());
    }
    
    @Test
    public void testReadOwnerByInvalidId() {
        // Set up test
        int id = 64;

        when(ownerRepository.findOwnerById(id)).thenReturn(null);

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> accountService.findOwnerById(id));
        assertEquals("There is no owner with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testReadCustomerByValidEmail() {
        // Set up test
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String imageURL = "pfp.png";
        
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(customerRepository.findCustomerByEmail(email.toLowerCase())).thenReturn(customer);

        // Use the AccountService
        Customer foundCustomer = accountService.findCustomerByEmail(email);
    
        // Assert
        assertNotNull(foundCustomer);
        assertEquals(email.toLowerCase(), foundCustomer.getEmail());
        assertEquals(password, foundCustomer.getPassword());
        assertEquals(name, foundCustomer.getName());
        assertEquals(imageURL, foundCustomer.getImageURL());
    }
    
    @Test
    public void testReadInstructorByValidEmail() {
        // Set up test
        String email = "kitKat@mail.com";
        String password = "secretPassword";
        String name = "Kitkat";
        String imageURL = "pfp.png";
        
        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(instructorRepository.findInstructorByEmail(email.toLowerCase())).thenReturn(instructor);

        // Use the AccountService
        Instructor foundInstructor = accountService.findInstructorByEmail(email);
    
        // Assert
        assertNotNull(foundInstructor);
        assertEquals(email.toLowerCase(), foundInstructor.getEmail());
        assertEquals(password, foundInstructor.getPassword());
        assertEquals(name, foundInstructor.getName());
        assertEquals(imageURL, foundInstructor.getImageURL());
    }
    
    @Test
    public void testReadOwnerByValidEmail() {
        // Set up test
        String email = "Watermelon@mail.com";
        String password = "secretPassword";
        String name = "Water Melon";
        String imageURL = "pfp.png";
        
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(toList(sportCenterRepository.findAll()).get(0));

        when(ownerRepository.findOwnerByEmail(email.toLowerCase())).thenReturn(owner);

        // Use the AccountService
        Owner foundOwner = accountService.findOwnerByEmail(email);
    
        // Assert
        assertNotNull(foundOwner);
        assertEquals(email.toLowerCase(), foundOwner.getEmail());
        assertEquals(password, foundOwner.getPassword());
        assertEquals(name, foundOwner.getName());
        assertEquals(imageURL, foundOwner.getImageURL());
    }

    
    @Test
    public void testReadCustomerByInvalidEmail() {
        // Set up test
        String email = "customer@mail.com";

        when(customerRepository.findCustomerByEmail(email)).thenReturn(null);

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> accountService.findCustomerByEmail(email));
        assertEquals("There is no customer with email " + email + ".", e.getMessage());
    }
    
    @Test
    public void testReadInstructorByInvalidEmail() {
        // Set up test
        String email = "instructor@mail.com";

        when(instructorRepository.findInstructorByEmail(email)).thenReturn(null);

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> accountService.findInstructorByEmail(email));
        assertEquals("There is no instructor with email " + email + ".", e.getMessage());
    }
    
    @Test
    public void testReadOwnerByInvalidEmail() {
        // Set up test
        String email = "owner@mail.com";

        when(ownerRepository.findOwnerByEmail(email)).thenReturn(null);

        // Use the AccountService and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> accountService.findOwnerByEmail(email));
        assertEquals("There is no owner with email " + email + ".", e.getMessage());
    }

    
    //--------------------------// Helper functions //--------------------------//

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
