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

  public Owner(String aEmail, String aPassword, String aName, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aCenter);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}