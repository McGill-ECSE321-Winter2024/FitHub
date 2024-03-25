package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Registration;

public class RegistrationResponseDTO {
    private AccountResponseDTO account;
    private SessionResponseDTO session;

    public RegistrationResponseDTO() {
        
    }

    public RegistrationResponseDTO(Registration registration) {
        this.account = AccountResponseDTO.create(registration.getKey().getCustomer());
        this.session = new SessionResponseDTO(registration.getKey().getSession());
    }

    public AccountResponseDTO getAccount() {
        return this.account;
    }

    public void setAccount(AccountResponseDTO account) {
        this.account = account;
    }

    public SessionResponseDTO getSession() {
        return this.session;
    }

    public void setSession(SessionResponseDTO session) {
        this.session = session;
    }


}
