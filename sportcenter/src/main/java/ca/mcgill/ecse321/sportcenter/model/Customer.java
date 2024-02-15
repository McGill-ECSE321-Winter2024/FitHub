package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;

@Entity
public class Customer extends Account
{
  public Customer(String aEmail, String aPassword, String aName, int aId, SportCenter aCenter)
  {
    super(aEmail, aPassword, aName, aId, aCenter);
  }

  public void delete()
  {
    super.delete();
  }

}