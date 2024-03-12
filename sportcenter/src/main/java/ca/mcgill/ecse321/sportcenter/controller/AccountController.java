package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;
    
    @PostMapping(value={"/customers", "/customers/"})
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO createCustomerAccount(@RequestBody AccountRequestDTO account) {
        Account createdAccount = (Account) accountService.createCustomerAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(createdAccount);
    }
    
    @PostMapping(value={"/instructors", "/instructors/"})
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO createInstructorAccount(@RequestBody AccountRequestDTO account) {
        Account createdAccount = (Account) accountService.createInstructorAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(createdAccount);
    }
    
    @PostMapping(value={"/owners", "/owners/"})
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO createOwnerAccount(@RequestBody AccountRequestDTO account) {
        Account createdAccount = (Account) accountService.createOwnerAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(createdAccount);
    }

    @PutMapping(value={"/customers/{id}", "/customers/{id}/"})
    public AccountResponseDTO updateCustomerAccount(@PathVariable Integer id, @RequestBody AccountRequestDTO account) {
        Account updatedAccount = (Account) accountService.updateCustomerAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(updatedAccount);
    }
    
    @PutMapping(value={"/instructors/{id}", "/instructors/{id}/"})
    public AccountResponseDTO updateInstructorAccount(@PathVariable Integer id, @RequestBody AccountRequestDTO account) {
        Account updatedAccount = (Account) accountService.updateInstructorAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(updatedAccount);
    }
    
    @PutMapping(value={"/owners/{id}", "/owners/{id}/"})
    public AccountResponseDTO updateOwnerAccount(@PathVariable Integer id, @RequestBody AccountRequestDTO account) {
        Account updatedAccount = (Account) accountService.updateOwnerAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(updatedAccount);
    }

    @DeleteMapping(value={"/customers/{id}", "/customers/{id}/"})
    public void deleteCustomerAccount(@PathVariable Integer id) {
        accountService.deleteCustomerAccount(id);
    }
    
    @DeleteMapping(value={"/instructors/{id}", "/instructors/{id}/"})
    public void deleteInstructorAccount(@PathVariable Integer id) {
        accountService.deleteInstructorAccount(id);
    }
    
    @DeleteMapping(value={"/owners/{id}", "/owners/{id}/"})
    public void deleteOwnerAccount(@PathVariable Integer id, @RequestBody AccountRequestDTO account) {
        accountService.deleteOwnerAccount(id);
    }
    
    @GetMapping(value={"/customers/{id}", "/customers/{id}/"})
    public AccountResponseDTO findCustomerById(@PathVariable Integer id) {
        return new AccountResponseDTO((Account) accountService.findCustomerById(id));
    }
    
    @GetMapping(value={"/instructors/{id}", "/instructors/{id}/"})
    public AccountResponseDTO findInstructorById(@PathVariable Integer id) {
        return new AccountResponseDTO((Account) accountService.findInstructorById(id));
    }
    
    @GetMapping(value={"/owners/{id}", "/owners/{id}/"})
    public AccountResponseDTO findOwnerById(@PathVariable Integer id) {
        return new AccountResponseDTO((Account) accountService.findOwnerById(id));
    }
    
    @GetMapping(value={"/customers", "/customers/"})
    public AccountResponseDTO findCustomerByEmail(@RequestParam("email") String email) {
        return new AccountResponseDTO((Account) accountService.findCustomerByEmail(email));
    }
    
    @GetMapping(value={"/instructors", "/instructors/"})
    public AccountResponseDTO findInstructorByEmail(@RequestParam("email") String email) {
        return new AccountResponseDTO((Account) accountService.findInstructorByEmail(email));
    }
    
    @GetMapping(value={"/owners", "/owners/"})
    public AccountResponseDTO findOwnerByEmail(@RequestParam("email") String email) {
        return new AccountResponseDTO((Account) accountService.findOwnerByEmail(email));
    }

    @GetMapping(value={"/customers", "/customers/"})
    public AccountListDTO findAllCustomers() {
        return new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllCustomers()));
    }

    @GetMapping(value={"/instructors", "/instructors/"})
    public AccountListDTO findAllInstructors() {
        return new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllInstructors()));
    }

    @GetMapping(value={"/owners", "/owners/"})
    public AccountListDTO findAllOwners() {
        return new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllOwners()));
    }

    @GetMapping(value={"/accounts", "/accounts/"})
    public AccountListDTO findAllAccounts() {
        return new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllAccounts()));
    }
}
