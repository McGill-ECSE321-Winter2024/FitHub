package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Instructor;

public class InstructorResponseDTO extends AccountResponseDTO {
    
    public InstructorResponseDTO(Instructor instructor) {
        super(instructor);
    }
}
