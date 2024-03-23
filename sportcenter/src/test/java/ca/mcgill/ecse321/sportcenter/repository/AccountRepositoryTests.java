package ca.mcgill.ecse321.sportcenter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Customer;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;

/**
 * This class represents the JUnit test cases for the AccountRepository.
 * It ensures the correctness of CRUD operations related to various account types.
 * Specifically, it tests the creation and retrieval of Owner, Instructor, and Customer entities.
 */
@SpringBootTest
public class AccountRepositoryTests extends CommonTestSetup {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private OwnerRepository ownerRepo;

    private SportCenter sportCenter;

    /**
     * Tests the creation and retrieval of an Owner entity.
     */
    @Test
    public void testCreateAndReadOwner() {
        // Create the owner 
        String email = "Jumijabasali@info.com";
        String password = "sportcenter";
        String name = "Jumijabasali";
        String imageURL = "pfp.com";
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setName(name);
        owner.setImageURL(imageURL);
        owner.setCenter(sportCenter);
        
        // Save into database
        owner = ownerRepo.save(owner);
        Integer ownerId = owner.getId();
        
        // Read back from database
        Owner ownerDb = (Owner) ownerRepo.findOwnerById(owner.getId());

        // Test if we found the owner
        assertNotNull(ownerDb);
        // Test if the id is the same
        assertEquals(ownerId, ownerDb.getId());
        // Test if other attributes are the same
        assertEquals(email.toLowerCase(), ownerDb.getEmail());
        assertEquals(password, ownerDb.getPassword());
        assertEquals(name, ownerDb.getName());
        assertEquals(imageURL, ownerDb.getImageURL());

        
        // Read back from database
        ownerDb = (Owner) ownerRepo.findOwnerByEmail(owner.getEmail());

        // Test if we found the owner
        assertNotNull(ownerDb);
        // Test if the id is the same
        assertEquals(ownerId, ownerDb.getId());
        // Test if other attributes are the same
        assertEquals(email.toLowerCase(), ownerDb.getEmail());
        assertEquals(password, ownerDb.getPassword());
        assertEquals(name, ownerDb.getName());
        assertEquals(imageURL, ownerDb.getImageURL());
    }

    /**
     * Tests the creation and retrieval of an Instructor entity.
     */
    @Test
    public void testCreateAndReadInstructor() {
        // Create the instructor 
        String email = "Jumijabasali@fithub.com";
        String password = "sportcenter";
        String name = "Jumijabasali";
        String imageURL = "pfp.com";
        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setName(name);
        instructor.setImageURL(imageURL);
        instructor.setCenter(sportCenter);
        
        // Save into database
        instructor = instructorRepo.save(instructor);
        Integer instructorId = instructor.getId();
        
        // Read back from database
        Instructor instructorDb = (Instructor) instructorRepo.findInstructorById(instructor.getId());

        // Test if we found the instructor
        assertNotNull(instructorDb);
        // Test if the id is the same
        assertEquals(instructorId, instructorDb.getId());
        // Test if other attributes are the same
        assertEquals(email.toLowerCase(), instructorDb.getEmail());
        assertEquals(password, instructorDb.getPassword());
        assertEquals(name, instructorDb.getName());
        assertEquals(imageURL, instructorDb.getImageURL());

        
        // Read back from database
        instructorDb = (Instructor) instructorRepo.findInstructorByEmail(instructor.getEmail());

        // Test if we found the instructor
        assertNotNull(instructorDb);
        // Test if the id is the same
        assertEquals(instructorId, instructorDb.getId());
        // Test if other attributes are the same
        assertEquals(email.toLowerCase(), instructorDb.getEmail());
        assertEquals(password, instructorDb.getPassword());
        assertEquals(name, instructorDb.getName());
        assertEquals(imageURL, instructorDb.getImageURL());
    }

    /**
     * Tests the creation and retrieval of a Customer entity.
     */
    @Test
    public void testCreateAndReadCustomer() {
        // Create the customer 
        String email = "Jumijabasali@fithub.com";
        String password = "sportcenter";
        String name = "Jumijabasali";
        String imageURL = "pfp.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setName(name);
        customer.setImageURL(imageURL);
        customer.setCenter(sportCenter);
        
        // Save into database
        customer = customerRepo.save(customer);
        Integer customerId = customer.getId();
        
        // Read back from database
        Customer customerDb = (Customer) customerRepo.findCustomerById(customer.getId());

        // Test if we found the customer
        assertNotNull(customerDb);
        // Test if the id is the same
        assertEquals(customerId, customerDb.getId());
        // Test if other attributes are the same
        assertEquals(email.toLowerCase(), customerDb.getEmail());
        assertEquals(password, customerDb.getPassword());
        assertEquals(name, customerDb.getName());
        assertEquals(imageURL, customerDb.getImageURL());
        
        // Read back from database
        customerDb = (Customer) customerRepo.findCustomerByEmail(customer.getEmail());

        // Test if we found the customer
        assertNotNull(customerDb);
        // Test if the id is the same
        assertEquals(customerId, customerDb.getId());
        // Test if other attributes are the same
        assertEquals(email.toLowerCase(), customerDb.getEmail());
        assertEquals(password, customerDb.getPassword());
        assertEquals(name, customerDb.getName());
        assertEquals(imageURL, customerDb.getImageURL());
    }
}
