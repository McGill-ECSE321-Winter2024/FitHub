package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Customer;

public class CustomerResponseDTO extends AccountResponseDTO {
    // Default constructor
    public CustomerResponseDTO() {
        super();
    }
    
    public CustomerResponseDTO(Customer customer) {
        super(customer);
    }


}
