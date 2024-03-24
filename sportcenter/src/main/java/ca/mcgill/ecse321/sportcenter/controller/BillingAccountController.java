package ca.mcgill.ecse321.sportcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.sportcenter.dto.BillingAccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.BillingAccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.BillingAccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionListDTO;
import ca.mcgill.ecse321.sportcenter.dto.SessionResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.BillingAccount;
import ca.mcgill.ecse321.sportcenter.model.Customer;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.BillingAccountService;

@CrossOrigin(origins = "*")
@RestController
public class BillingAccountController {

    @Autowired
    BillingAccountService billingService;

    @Autowired
    private AccountService accountService;

    //--------------------------// Create Account //--------------------------//

    @PostMapping(value={"/customers/{cId}/billing-accounts", "/customers/{cId}/billing-accounts/"} )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BillingAccountResponseDTO> createBillingAccount(@RequestBody BillingAccountRequestDTO account, @PathVariable int cId){
        Customer customer = accountService.findCustomerById(cId);
        return new ResponseEntity<BillingAccountResponseDTO>( new BillingAccountResponseDTO(billingService.createBillingAccount(account.getCardNumber(), account.getCardHolder(), account.getBillingAddress(), account.getCvv(), account.getIsDefault(), account.getExpirationDate(), customer)), HttpStatus.CREATED);
    }
    
    //--------------------------// Update Account //--------------------------//

    @PutMapping(value={"/customers/{cId}/billing-accounts/{id}", "/customers/{cId}/billing-accounts/{id}/"} )
    public ResponseEntity<BillingAccountResponseDTO> updateBillingAccount(@RequestBody BillingAccountRequestDTO account, @PathVariable int id, @PathVariable int cId){
        return new ResponseEntity<BillingAccountResponseDTO>( new BillingAccountResponseDTO(billingService.updateBillingAccount(id, account.getCardNumber(), account.getCardHolder(), account.getBillingAddress(), account.getCvv(), account.getIsDefault(), account.getExpirationDate())), HttpStatus.ACCEPTED);
    }

    //--------------------------// Delete Account //--------------------------//

    @DeleteMapping(value={"/customers/{cId}/billing-accounts/{id}", "/customers/{cId}/billing-accounts/{id}/"})
    public ResponseEntity<Void> deleteBillingAccount(@PathVariable Integer id) {
        boolean deletionSuccessful = billingService.deleteBillingAccount(id);
        if (deletionSuccessful) {
            return ResponseEntity.noContent().build(); // 204 NO_CONTENT
        } else {
            return ResponseEntity.notFound().build(); // 404 NOT_FOUND if the billing account with the specified ID was not found
        }
    }

    //--------------------------// Getters //--------------------------//

    @GetMapping(value={"/billing-accounts/{id}", "/billing-accounts/{id}/"})
    public ResponseEntity<BillingAccountResponseDTO> findBillingAccountById(@PathVariable Integer id) {
        return new ResponseEntity<BillingAccountResponseDTO>(new BillingAccountResponseDTO(billingService.findBillingAccountById(id)), HttpStatus.FOUND);
    }

    @GetMapping(value={"/billing-accounts", "/billing-accounts/"})
   public ResponseEntity< BillingAccountListDTO> findAllBillingAccounts() {
       List<BillingAccountResponseDTO> accounts = new ArrayList<BillingAccountResponseDTO>();
       for (BillingAccount account : billingService.findAllBillingAccounts()){
           accounts.add(new BillingAccountResponseDTO(account));
       }
       if(accounts.isEmpty())
           return new ResponseEntity<BillingAccountListDTO>(new BillingAccountListDTO(accounts),HttpStatus.NO_CONTENT);
       else{
           return new ResponseEntity<BillingAccountListDTO>(new BillingAccountListDTO(accounts),HttpStatus.OK);
       }
   }

   @GetMapping(value={"/customers/{cId}/billing-accounts", "/customers/{cId}/billing-accounts/"})
   public ResponseEntity<BillingAccountListDTO> findBillingAccountsByCustomer(@PathVariable int cId){

        Customer customer = accountService.findCustomerById(cId);
      
       
       List<BillingAccountResponseDTO> accounts = new ArrayList<BillingAccountResponseDTO>();
       for (BillingAccount account : billingService.findBillingAccountByCustomer(customer)){
           accounts.add(new BillingAccountResponseDTO(account));
       }
       
       if(accounts.isEmpty())
           return new ResponseEntity<BillingAccountListDTO>(new BillingAccountListDTO(accounts),HttpStatus.NO_CONTENT);
       else{
           return new ResponseEntity<BillingAccountListDTO>(new BillingAccountListDTO(accounts),HttpStatus.OK);
       }
       
   }

   @GetMapping(value={"/customers/{cId}/billing-account", "/customers/{cId}/billing-account"})
    public ResponseEntity<BillingAccountResponseDTO> findDefaultBillingAccountById(@PathVariable Integer cId) {
        
        Customer customer = accountService.findCustomerById(cId);
        
        //BillingAccount account = new BillingAccount();

        try{
            BillingAccount account = billingService.findDefaultBillingAccountOfCustomer(customer);
            return new ResponseEntity<BillingAccountResponseDTO>(new BillingAccountResponseDTO(account), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<BillingAccountResponseDTO>(new BillingAccountResponseDTO(),HttpStatus.NO_CONTENT);
        }
    
    }


}
