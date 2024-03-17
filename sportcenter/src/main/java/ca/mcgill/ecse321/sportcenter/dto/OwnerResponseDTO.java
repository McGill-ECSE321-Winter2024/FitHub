package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Owner;

public class OwnerResponseDTO extends AccountResponseDTO {
    // Default constructor
    public OwnerResponseDTO() {
        super();
    }
    
    public OwnerResponseDTO(Owner owner) {
        super(owner);
    }
}
