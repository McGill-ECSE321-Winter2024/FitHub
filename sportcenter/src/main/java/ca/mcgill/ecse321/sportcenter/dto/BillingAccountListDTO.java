package ca.mcgill.ecse321.sportcenter.dto;

import java.util.List;

public class BillingAccountListDTO {

    private List<BillingAccountResponseDTO> accounts;

    public BillingAccountListDTO(List<BillingAccountResponseDTO> accounts){
        this.accounts = accounts;
    }

    public List<BillingAccountResponseDTO> getAccounts(){
        return accounts;
    }

    public void setAccounts(List<BillingAccountResponseDTO> accounts){
        this.accounts = accounts;
    }


    
}
