package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
// line 2 "model.ump"
// line 90 "model.ump"
@Entity
public class Image
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Image Attributes
  @Id
  @GeneratedValue
  private int id;
  private String url;

  //Image Associations
  private Account account;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Image(String aUrl, int aId, Account aAccount)
  {
    url = aUrl;
    id = aId;
    boolean didAddAccount = setAccount(aAccount);
    if (!didAddAccount)
    {
      throw new RuntimeException("Unable to create pfp due to account. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
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

  public String getUrl()
  {
    return url;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Account getAccount()
  {
    return account;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAccount(Account aNewAccount)
  {
    boolean wasSet = false;
    if (aNewAccount == null)
    {
      //Unable to setAccount to null, as pfp must always be associated to a account
      return wasSet;
    }
    
    Image existingPfp = aNewAccount.getPfp();
    if (existingPfp != null && !equals(existingPfp))
    {
      //Unable to setAccount, the current account already has a pfp, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Account anOldAccount = account;
    account = aNewAccount;
    account.setPfp(this);

    if (anOldAccount != null)
    {
      anOldAccount.setPfp(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Account existingAccount = account;
    account = null;
    if (existingAccount != null)
    {
      existingAccount.setPfp(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "url" + ":" + getUrl()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "account = "+(getAccount()!=null?Integer.toHexString(System.identityHashCode(getAccount())):"null");
  }
}