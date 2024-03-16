package ca.mcgill.ecse321.sportcenter.model;
import jakarta.persistence.Entity;

@Entity
public class Customer extends Account
{
  public Customer() {
    setAuthority(Authority.ROLE_CUSTOMER);
  }

  public Customer(String aEmail, String aPassword, String aName, String aImageURL, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aImageURL, aCenter);
    setAuthority(Authority.ROLE_CUSTOMER);
  }

  public void delete()
  {
    super.delete();
  }
}