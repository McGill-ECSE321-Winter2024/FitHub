package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends Account
{

  public Instructor(String aEmail, String aPassword, String aName, String aImageURL, int aId, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aImageURL, aId, aCenter);
  }
  
  public void delete()
  {
    super.delete();
  }

}