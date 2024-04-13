package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends Account
{
  public Instructor() {
    setAuthority(Authority.ROLE_INSTRUCTOR);
  }

  public Instructor(String aEmail, String aPassword, String aName, String aImageURL, String aPronouns, int aId, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aImageURL, aPronouns, aId, aCenter);
    setAuthority(Authority.ROLE_INSTRUCTOR);
  }

  
  public void delete()
  {
    super.delete();
  }

}