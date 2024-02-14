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

  public Instructor(String aEmail, String aPassword, String aName, int aId, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aId, aCenter);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}