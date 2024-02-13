package ca.mcgill.ecse321.sportcenter.model;


// line 16 "model.ump"
// line 102 "model.ump"
public class Owner extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aEmail, String aPassword, String aName, int aId, SportCenter aCenter)
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