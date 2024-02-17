package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  protected Instructor() {
    
  }

  public Instructor(String aEmail, String aPassword, String aName, SportCenter aCenter)
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