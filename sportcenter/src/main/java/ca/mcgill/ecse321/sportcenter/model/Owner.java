package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;

@Entity
public class Owner extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Owner() {
    
  }

  public Owner(String aEmail, String aPassword, String aName, String aImageURL, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aImageURL, aCenter);
  }
  
  public void delete()
  {
    super.delete();
  }

}