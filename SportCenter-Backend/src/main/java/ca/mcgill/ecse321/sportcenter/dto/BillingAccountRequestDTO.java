package ca.mcgill.ecse321.sportcenter.dto;

import java.time.LocalDate;

public class BillingAccountRequestDTO {

    private String cardNumber;
    private String cardHolder;
    private String billingAddress;
    private Integer cvv; 
    private boolean isDefault;
    private LocalDate expirationDate;
    private CustomerResponseDTO customer;


    public BillingAccountRequestDTO(String cardNumber, String cardHolder, String billingAddress, Integer cvv, boolean isDefault, LocalDate expirationDate, CustomerResponseDTO customer){

        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.billingAddress = billingAddress;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.isDefault = isDefault; 
        this.customer = customer;

    }

    public BillingAccountRequestDTO(){
        
    }

    //--------------------- Getters -------------------//

    public String getCardNumber(){
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

    public LocalDate getExpirationDate(){
        return expirationDate;
    }

    public boolean getIsDefault(){
        return isDefault;
    }

    public CustomerResponseDTO getCustomer(){
        return customer;
    }

    //--------------------- Setters -------------------//

    public void setCardHolder(String aCardHolder){
        this.cardHolder = aCardHolder;
    }

    public void setCardNumber(String aCardNumber){
        this.cardNumber = aCardNumber;
    }

    public void setBillingAddress(String aBillingAddress){
        this.billingAddress = aBillingAddress;
    }

    public void setCvv(Integer aCvv){
        this.cvv = aCvv;
    }

    public void setExpirationDate(LocalDate aExpirationDate){
        this.expirationDate = aExpirationDate;
    }

    public void setIsDefault(boolean aIsDefault){
        this.isDefault = aIsDefault;
    }

    public void setCustomer(CustomerResponseDTO aCustomer){
        this.customer = aCustomer;
    }
    
}
