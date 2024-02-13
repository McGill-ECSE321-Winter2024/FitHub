package ca.mcgill.ecse321.sportcenter.model;

// line 28 "model.ump"
// line 116 "model.ump"
public class Registration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Registration Associations
  private Customer customer;
  private Session session;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Registration(Customer aCustomer, Session aSession)
  {
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Registration due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSession(aSession))
    {
      throw new RuntimeException("Unable to create Registration due to aSession. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public Session getSession()
  {
    return session;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSession(Session aNewSession)
  {
    boolean wasSet = false;
    if (aNewSession != null)
    {
      session = aNewSession;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    customer = null;
    session = null;
  }

}