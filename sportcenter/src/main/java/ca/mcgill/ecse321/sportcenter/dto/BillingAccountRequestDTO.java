package ca.mcgill.ecse321.sportcenter.dto;

import java.math.BigInteger;
import java.sql.Date;

public class BillingAccountRequestDTO {

    private BigInteger cardNumber;
    private String cardHolder;
    private String billingAddress;
    private Integer cvv; 
    private boolean isDefault;
    private Date expirationDate;
    private CustomerResponseDTO customer;


    public BillingAccountRequestDTO(BigInteger cardNumber, String cardHolder, String billingAddress, Integer cvv, boolean isDefault, Date expirationDate, CustomerResponseDTO customer){

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
    
}
