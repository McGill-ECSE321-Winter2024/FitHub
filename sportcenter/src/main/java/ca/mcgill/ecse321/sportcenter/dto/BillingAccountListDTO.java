package ca.mcgill.ecse321.sportcenter.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;

public class BillingAccountListDTO {

    private List<BillingAccountResponseDTO> accounts;

    public BillingAccountListDTO(List<BillingAccountResponseDTO> accounts){
        this.accounts = accounts;
    }

    public BillingAccountListDTO(){
        this.accounts = new ArrayList<>();
    }

    public List<BillingAccountResponseDTO> getBillingAccounts(){
        return accounts;
    }

    public void setBillingAccounts(List<BillingAccountResponseDTO> accounts){
        this.accounts = accounts;
    }

    public static <T> List<BillingAccountResponseDTO> billingAccountListToBillingAccountResponseDTOList(List<T> accounts) {
        List<BillingAccountResponseDTO> list = new ArrayList<>();
        for (T account : accounts) {
            list.add(new BillingAccountResponseDTO((BillingAccount)account));
        }
        return list;
    }
    
}
