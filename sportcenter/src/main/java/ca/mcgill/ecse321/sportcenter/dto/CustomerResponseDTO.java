package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Customer;

public class CustomerResponseDTO extends AccountResponseDTO {
    
    public CustomerResponseDTO(Customer customer) {
        super(customer);
    }
}
