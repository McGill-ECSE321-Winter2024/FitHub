package ca.mcgill.ecse321.sportcenter.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.Account;

public class AccountListDTO {
    private List<AccountResponseDTO> accounts;
    private String error;

    public AccountListDTO() {
        this.accounts = new ArrayList<>();
        this.error = "";
    }

    public AccountListDTO(String error) {
        this.error = error;
    }

    public AccountListDTO(List<AccountResponseDTO> accounts) {
        this.accounts = accounts;
        this.error = "";
    }

    public static <T> List<AccountResponseDTO> accountListToAccountResponseDTOList(List<T> accounts) {
        List<AccountResponseDTO> list = new ArrayList<>();
        for (T account : accounts) {
            list.add(AccountResponseDTO.create((Account) account));
        }
        return list;
    }

    public List<AccountResponseDTO> getAccounts() {
        return accounts;
    }

    public String getError() {
        return this.error;
    }

    public void setAccounts(List<AccountResponseDTO> accounts) {
        this.accounts = accounts;
    }

    public void setError(String error) {
        this.error = error;
    }
}
