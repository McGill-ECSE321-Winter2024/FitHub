package ca.mcgill.ecse321.sportcenter.dto;

import java.util.List;

public class AccountListDTO {
    private List<AccountResponseDTO> accounts;

    public AccountListDTO(List<AccountResponseDTO> accounts) {
        this.accounts = accounts;
    }

    public List<AccountResponseDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResponseDTO> accounts) {
        this.accounts = accounts;
    }
}
