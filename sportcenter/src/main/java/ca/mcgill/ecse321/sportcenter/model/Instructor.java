package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends Account
{

  protected Instructor() {
    
  }

  public Instructor(String aEmail, String aPassword, String aName, String aImageURL, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aImageURL, aCenter);
  }
  
  public void delete()
  {
    super.delete();
  }

}