package ca.mcgill.ecse321.sportcenter.dto;

import java.util.List;

public class RegistrationListDTO {
    private List<RegistrationResponseDTO> registrations;

    public RegistrationListDTO(List<RegistrationResponseDTO> registrations) {
        this.registrations = registrations;
    }

    public RegistrationListDTO(){
        
    }

    public List<RegistrationResponseDTO> getRegistrations() {
        return this.registrations;
    }

    public void setRegistrations(List<RegistrationResponseDTO> registrations) {
        this.registrations = registrations;
    }
}
