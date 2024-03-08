package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/customers")
    public AccountResponseDTO createCustomerAccount(@RequestBody AccountRequestDTO account) {
        Account createdAccount = (Account) accountService.createCustomerAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(createdAccount);
    }
    
    @GetMapping("/instructors")
    public AccountResponseDTO createInstructorAccount(@RequestBody AccountRequestDTO account) {
        Account createdAccount = (Account) accountService.createInstructorAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(createdAccount);
    }
    
    @GetMapping("/owners")
    public AccountResponseDTO createOwnerAccount(@RequestBody AccountRequestDTO account) {
        Account createdAccount = (Account) accountService.createOwnerAccount(account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(createdAccount);
    }

    @GetMapping("/customers/{id}")
    public AccountResponseDTO updateCustomerAccount(@RequestBody Integer id, @RequestBody AccountRequestDTO account) {
        Account updatedAccount = (Account) accountService.updateCustomerAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(updatedAccount);
    }
    
    @GetMapping("/instructors/{id}")
    public AccountResponseDTO updateInstructorAccount(@RequestBody Integer id, @RequestBody AccountRequestDTO account) {
        Account updatedAccount = (Account) accountService.updateInstructorAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(updatedAccount);
    }
    
    @GetMapping("/owners/{id}")
    public AccountResponseDTO updateOwnerAccount(@RequestBody Integer id, @RequestBody AccountRequestDTO account) {
        Account updatedAccount = (Account) accountService.updateOwnerAccount(id, account.getEmail(), account.getPassword(), account.getName(), account.getImageURL());
        return new AccountResponseDTO(updatedAccount);
    }

    @GetMapping("/customers/{id}")
    public void deleteCustomerAccount(@RequestBody Integer id) {
        accountService.deleteCustomerAccount(id);
    }
    
    @GetMapping("/instructors/{id}")
    public void deleteInstructorAccount(@RequestBody Integer id) {
        accountService.deleteInstructorAccount(id);
    }
    
    @GetMapping("/owners/{id}")
    public void deleteOwnerAccount(@RequestBody Integer id, @RequestBody AccountRequestDTO account) {
        accountService.deleteOwnerAccount(id);
    }
    
    @GetMapping("/customers/{id}")
    public AccountResponseDTO findCustomerById(@PathVariable Integer id) {
        return new AccountResponseDTO((Account) accountService.findCustomerById(id));
    }
    
    @GetMapping("/instructors/{id}")
    public AccountResponseDTO findInstructorById(@PathVariable Integer id) {
        return new AccountResponseDTO((Account) accountService.findInstructorById(id));
    }
    
    @GetMapping("/owners/{id}")
    public AccountResponseDTO findOwnerById(@PathVariable Integer id) {
        return new AccountResponseDTO((Account) accountService.findOwnerById(id));
    }
    
    @GetMapping("/customers/{email}")
    public AccountResponseDTO findCustomerByEmail(@PathVariable String email) {
        return new AccountResponseDTO((Account) accountService.findCustomerByEmail(email));
    }
    
    @GetMapping("/instructors/{email}")
    public AccountResponseDTO findInstructorByEmail(@PathVariable String email) {
        return new AccountResponseDTO((Account) accountService.findInstructorByEmail(email));
    }
    
    @GetMapping("/owners/{email}")
    public AccountResponseDTO findOwnerByEmail(@PathVariable String email) {
        return new AccountResponseDTO((Account) accountService.findOwnerByEmail(email));
    }

    @GetMapping("/customers")
    public AccountListDTO findAllCustomers() {
        return new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllCustomers()));
    }

    @GetMapping("/instructors")
    public AccountListDTO findAllInstructors() {
        return new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllInstructors()));
    }

    @GetMapping("/owners")
    public AccountListDTO findAllOwners() {
        return new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllOwners()));
    }

    @GetMapping("/accounts")
    public AccountListDTO findAllAccounts() {
        return new AccountListDTO(AccountListDTO.accountListToAccountResponseDTOList(accountService.findAllAccounts()));
    }
}
