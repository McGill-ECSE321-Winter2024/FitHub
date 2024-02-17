package ca.mcgill.ecse321.sportcenter.model;
import jakarta.persistence.Entity;

@Entity
public class Customer extends Account
{
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer() {
    
  }

  public Customer(String aEmail, String aPassword, String aName, String aImageURL, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aImageURL, aCenter);
  }

  public void delete()
  {
    super.delete();
  }

}