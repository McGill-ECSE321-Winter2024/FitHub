package ca.mcgill.ecse321.sportcenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.repository.CustomerRepository;
import ca.mcgill.ecse321.sportcenter.repository.InstructorRepository;
import ca.mcgill.ecse321.sportcenter.repository.OwnerRepository;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Autowired    
    PasswordEncoder passwordEncoder;

    //--------------------------// Login to Account //--------------------------//

    /*
     * <p>Verify that the account with the email and the associated password exists in the system</p>
     * @param email of the account and its password
     * @return an error if no account in the system exist with this email and password else, returns the type of account it is
     * @author Julia
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Implement your custom authentication logic here
        // For example, you might authenticate against a user database
        
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Customer customer = customerRepository.findCustomerByEmail(email);
        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        Owner owner = ownerRepository.findOwnerByEmail(email);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(password);
        stringBuilder.append(" andddd ");
        stringBuilder.append(customer.getPassword());
        stringBuilder.append(passwordEncoder.matches(password, customer.getPassword()));
        System.out.println(stringBuilder.toString());

        if (customer != null && passwordEncoder.matches(password, customer.getPassword())) {
            return new UsernamePasswordAuthenticationToken(customer, password, customer.getAuthorities());
        }
        else if (instructor != null && passwordEncoder.matches(password, instructor.getPassword())) {
            return new UsernamePasswordAuthenticationToken(instructor, password, instructor.getAuthorities());
        }
        else if (owner != null && passwordEncoder.matches(password, owner.getPassword())) {
            return new UsernamePasswordAuthenticationToken(owner, password, owner.getAuthorities());
        }
        
        throw new IllegalArgumentException("No account in the system exists with this email and password");
    }
}
