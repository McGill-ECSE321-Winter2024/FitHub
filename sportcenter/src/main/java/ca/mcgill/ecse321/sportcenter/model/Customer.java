package ca.mcgill.ecse321.sportcenter.model;

// line 24 "model.ump"
// line 112 "model.ump"
public class Customer extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Associations
  private Registration registration;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aPassword, String aName, int aId, SportCenter aCenter, Registration aRegistration)
  {
    super(aEmail, aPassword, aName, aId, aCenter);
    if (!setRegistration(aRegistration))
    {
      throw new RuntimeException("Unable to create Customer due to aRegistration. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Registration getRegistration()
  {
    return registration;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRegistration(Registration aNewRegistration)
  {
    boolean wasSet = false;
    if (aNewRegistration != null)
    {
      registration = aNewRegistration;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    registration = null;
    super.delete();
  }

}