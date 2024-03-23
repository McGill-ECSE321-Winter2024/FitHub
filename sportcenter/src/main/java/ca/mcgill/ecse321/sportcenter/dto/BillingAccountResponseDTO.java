package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.BillingAccount;

import java.math.BigInteger;
import java.sql.Date;

public class BillingAccountResponseDTO {
    

    private BigInteger cardNumber;
    private String cardHolder;
    private String billingAddress;
    private Integer cvv; 
    private Integer id;
    private boolean isDefault;
    private Date expirationDate;
    private CustomerResponseDTO customer;

    public BillingAccountResponseDTO(BillingAccount aAccount){

        this.id = aAccount.getId();
        this.cardNumber = aAccount.getCardNumber();
        this.cardHolder = aAccount.getCardHolder();
        this.billingAddress = aAccount.getBillingAddress();
        this.expirationDate = aAccount.getExpirationDate();
        this.cvv = aAccount.getCvv();
        this.isDefault = aAccount.getIsDefault();
        this.customer = new CustomerResponseDTO(aAccount.getCustomer());

    }

    public BillingAccountResponseDTO(){
        
    }

    //--------------------- Getters -------------------//

    public BigInteger getCardNumber(){
        return cardNumber;
    }

    public String getCardHolder(){
        return cardHolder;
    }

    public String getBillingAddress(){
        return billingAddress;
    }

    public Integer getCvv(){
        return cvv;
    }

    public Date getExpirationDate(){
        return expirationDate;
    }

    public boolean getIsDefault(){
        return isDefault;
    }

    public CustomerResponseDTO getCustomer(){
        return customer;
    }

    public Integer getId(){
        return id;
    }

    //--------------------- Setters -------------------//

    public void setCardHolder(String aCardHolder){
        this.cardHolder = aCardHolder;
    }

    public void setCardNumber(BigInteger aCardNumber){
        this.cardNumber = aCardNumber;
    }

    public void setBillingAddress(String aBillingAddress){
        this.billingAddress = aBillingAddress;
    }

    public void setCvv(Integer aCvv){
        this.cvv = aCvv;
    }

    public void setExpirationDate(Date aExpirationDate){
        this.expirationDate = aExpirationDate;
    }

    public void setIsDefault(boolean aIsDefault){
        this.isDefault = aIsDefault;
    }

    public void setCustomer(CustomerResponseDTO aCustomer){
        this.customer = aCustomer;
    }

    public void setId(Integer aId){
        this.id = aId;
    }

}
