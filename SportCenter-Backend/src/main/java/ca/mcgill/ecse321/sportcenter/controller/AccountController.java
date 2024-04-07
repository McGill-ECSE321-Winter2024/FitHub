package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Owner;
import ca.mcgill.ecse321.sportcenter.service.AccountService;

/**
 * <p>Controller class in charge of managing accounts. It implements following use cases: </p>
 * <p>Create, update, read and delete a account </p>
 * @author Julia
*/
@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/login-success")
    public ResponseEntity<String> loginSuccess() {
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    @GetMapping("/login-failure")
    public ResponseEntity<String> loginFailure() {
        return new ResponseEntity<String>("failure", HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logOut() {
        return new ResponseEntity<String>("logout", HttpStatus.OK);
    }
    @GetMapping("/logout-success")
    public ResponseEntity<String> logOutSuccess() {
        return new ResponseEntity<String>("logout", HttpStatus.OK);
    }

    @GetMapping("/role-id")
    public ResponseEntity<String> getRoleAndId() {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roleAndId = "visitor";

        // Check if the user is authenticated and has a specific role
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
                roleAndId = "owner,";
            } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_INSTRUCTOR"))) {
                roleAndId = "instructor,";
            } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                roleAndId = "customer,";
            }

            if (!roleAndId.equals("visitor")) {
                Account account = (Account) accountService.loadUserByUsername(authentication.getName());
                roleAndId += account.getId(); // add the id to the response
            }
        }

        return new ResponseEntity<String>(roleAndId, HttpStatus.OK);
    }

    //--------------------------// Create Account //--------------------------//
    
    @PostMapping(value={"/customers", "/customers/", "/public/customers"})
    public ResponseEntity<AccountResponseDTO> createCustomerAccount(@RequestBody AccountRequestDTO account) {
        try {
            Customer createdAccount = accountService.createCustomerAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL(), account.getPronouns());
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(createdAccount), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping(value={"/instructors", "/instructors/"})
    public ResponseEntity<AccountResponseDTO> createInstructorAccount(@RequestBody AccountRequestDTO account) {
        try {
            Instructor createdAccount = accountService.createInstructorAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL(), account.getPronouns());
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(createdAccount), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping(value={"/owners", "/owners/"})
    public ResponseEntity<AccountResponseDTO>  createOwnerAccount(@RequestBody AccountRequestDTO account) {
        try {
            Owner createdAccount = accountService.createOwnerAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL(), account.getPronouns());
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(createdAccount), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Update Account //--------------------------//

    @PutMapping(value={"/customers/{id}", "/customers/{id}/"})
    public ResponseEntity<AccountResponseDTO> updateCustomerAccount(@PathVariable Integer id, @RequestBody AccountRequestDTO account) {
        try {
            Customer updatedAccount = accountService.updateCustomerAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL(), account.getPronouns());
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(updatedAccount), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping(value={"/instructors/{id}", "/instructors/{id}/"})
    public ResponseEntity<AccountResponseDTO> updateInstructorAccount(@PathVariable Integer id, @RequestBody AccountRequestDTO account) {
        try {
            Instructor updatedAccount = accountService.updateInstructorAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL(), account.getPronouns());
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(updatedAccount), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping(value={"/owners/{id}", "/owners/{id}/"})
    public ResponseEntity<AccountResponseDTO> updateOwnerAccount(@PathVariable Integer id, @RequestBody AccountRequestDTO account) {
        try {
            Owner updatedAccount = accountService.updateOwnerAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL(), account.getPronouns());
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(updatedAccount), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    //--------------------------// Delete Account //--------------------------//

    @DeleteMapping(value={"/customers/{id}", "/customers/{id}/"})
    public ResponseEntity<String> deleteCustomerAccount(@PathVariable Integer id) {
        try {
            accountService.deleteCustomerAccount(id);
            return new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping(value={"/instructors/{id}", "/instructors/{id}/"})
    public ResponseEntity<String> deleteInstructorAccount(@PathVariable Integer id) {
        try {
            accountService.deleteInstructorAccount(id);
            return new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping(value={"/owners/{id}", "/owners/{id}/"})
    public ResponseEntity<String> deleteOwnerAccount(@PathVariable Integer id) {
        try {
            accountService.deleteOwnerAccount(id);
            return new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    //--------------------------// Getters //--------------------------//

    @GetMapping(value={"/customers/{id}", "/customers/{id}/"})
    public ResponseEntity<AccountResponseDTO> findCustomerById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(accountService.findCustomerById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value={"/instructors/{id}", "/instructors/{id}/"})
    public ResponseEntity<AccountResponseDTO> findInstructorById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(accountService.findInstructorById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value={"/owners/{id}", "/owners/{id}/"})
    public ResponseEntity<AccountResponseDTO> findOwnerById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(accountService.findOwnerById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountResponseDTO>(new AccountResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    // Can either get all customer with /customers
    // Or get a specific customer with its email /customers?email=julia@mail.com
    @GetMapping(value={"/customers", "/customers/"})
    public ResponseEntity<AccountListDTO> findAllCustomers(@RequestParam(name = "email", required = false) String email) {
        try {
            AccountListDTO accountList = new AccountListDTO();

            if (email!=null) {
                List<Customer> list = new ArrayList<>();
                list.add(accountService.findCustomerByEmail(email));
                accountList.setAccounts(AccountListDTO.accountListToAccountResponseDTOList(list));
            }
            else {
                accountList.setAccounts(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllCustomers()));
            }

            if (accountList.getAccounts().size() > 0)
                return new ResponseEntity<AccountListDTO>(accountList, HttpStatus.OK);
            else
                return new ResponseEntity<AccountListDTO>(accountList, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountListDTO>(new AccountListDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
    }

    @GetMapping(value={"/instructors", "/instructors/", "/public/instructors"})
    public ResponseEntity<AccountListDTO> findAllInstructors(@RequestParam(name = "email", required = false) String email) {
        try {
            AccountListDTO accountList = new AccountListDTO();
            if (email!=null) {
                List<Instructor> list = new ArrayList<>();
                list.add(accountService.findInstructorByEmail(email));
                accountList.setAccounts(AccountListDTO.accountListToAccountResponseDTOList(list));
            }
            else {
                accountList.setAccounts(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllInstructors()));
            }

            if (accountList.getAccounts().size() > 0)
            return new ResponseEntity<AccountListDTO>(accountList, HttpStatus.OK);
            else
                return new ResponseEntity<AccountListDTO>(accountList, HttpStatus.NO_CONTENT);
                
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountListDTO>(new AccountListDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value={"/owners", "/owners/"})
    public ResponseEntity<AccountListDTO> findAllOwners(@RequestParam(name = "email", required = false) String email) {
        try {
            AccountListDTO accountList = new AccountListDTO();
            if (email!=null) {
                List<Owner> list = new ArrayList<>();
                list.add(accountService.findOwnerByEmail(email));
                accountList.setAccounts(AccountListDTO.accountListToAccountResponseDTOList(list));
            }
            else {
                accountList.setAccounts(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllOwners()));
            }

            if (accountList.getAccounts().size() > 0)
                return new ResponseEntity<AccountListDTO>(accountList, HttpStatus.OK);
            else
                return new ResponseEntity<AccountListDTO>(accountList, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<AccountListDTO>(new AccountListDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value={"/accounts", "/accounts/"})
    public ResponseEntity<AccountListDTO> findAllAccounts() {
        AccountListDTO accountList = new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllAccounts()));

        if (accountList.getAccounts().size() > 0)
            return new ResponseEntity<AccountListDTO>(accountList, HttpStatus.OK);
        else
            return new ResponseEntity<AccountListDTO>(accountList, HttpStatus.NO_CONTENT);
    }
}
