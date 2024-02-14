package ca.mcgill.ecse321.sportcenter.model;

// line 8 "model.ump"
// line 95 "model.ump"
public abstract class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  private String email;
  private String password;
  private String name;
  private int id;

  //Account Associations
  private Image pfp;
  private SportCenter center;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String aEmail, String aPassword, String aName, int aId, SportCenter aCenter)
  {
    email = aEmail;
    password = aPassword;
    name = aName;
    id = aId;
    boolean didAddCenter = setCenter(aCenter);
    if (!didAddCenter)
    {
      throw new RuntimeException("Unable to create account due to center. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Image getPfp()
  {
    return pfp;
  }

  public boolean hasPfp()
  {
    boolean has = pfp != null;
    return has;
  }
  /* Code from template association_GetOne */
  public SportCenter getCenter()
  {
    return center;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setPfp(Image aNewPfp)
  {
    boolean wasSet = false;
    if (pfp != null && !pfp.equals(aNewPfp) && equals(pfp.getAccount()))
    {
      //Unable to setPfp, as existing pfp would become an orphan
      return wasSet;
    }

    pfp = aNewPfp;
    Account anOldAccount = aNewPfp != null ? aNewPfp.getAccount() : null;

    if (!this.equals(anOldAccount))
    {
      if (anOldAccount != null)
      {
        anOldAccount.pfp = null;
      }
      if (pfp != null)
      {
        pfp.setAccount(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCenter(SportCenter aCenter)
  {
    boolean wasSet = false;
    if (aCenter == null)
    {
      return wasSet;
    }

    SportCenter existingCenter = center;
    center = aCenter;
    if (existingCenter != null && !existingCenter.equals(aCenter))
    {
      existingCenter.removeAccount(this);
    }
    center.addAccount(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Image existingPfp = pfp;
    pfp = null;
    if (existingPfp != null)
    {
      existingPfp.delete();
      existingPfp.setAccount(null);
    }
    SportCenter placeholderCenter = center;
    this.center = null;
    if(placeholderCenter != null)
    {
      placeholderCenter.removeAccount(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "pfp = "+(getPfp()!=null?Integer.toHexString(System.identityHashCode(getPfp())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "center = "+(getCenter()!=null?Integer.toHexString(System.identityHashCode(getCenter())):"null");
  }
}