package ca.mcgill.ecse321.sportcenter.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.Account;

public class AccountListDTO {
    private List<AccountResponseDTO> accounts;

    public AccountListDTO(List<AccountResponseDTO> accounts) {
        this.accounts = accounts;
    }

    public static <T> List<AccountResponseDTO> accountListToAccountResponseDTOList(List<T> accounts) {
        List<AccountResponseDTO> list = new ArrayList<>();
        for (T account : accounts) {
            list.add(new AccountResponseDTO((Account) account));
        }
        return list;
    }

    public List<AccountResponseDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResponseDTO> accounts) {
        this.accounts = accounts;
    }
}
