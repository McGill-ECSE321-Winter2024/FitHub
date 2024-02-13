package ca.mcgill.ecse321.sportcenter.model;

// line 43 "model.ump"
// line 128 "model.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private String foor;
  private String room;

  //Location Associations
  private SportCenter center;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(String aFoor, String aRoom, SportCenter aCenter)
  {
    foor = aFoor;
    room = aRoom;
    boolean didAddCenter = setCenter(aCenter);
    if (!didAddCenter)
    {
      throw new RuntimeException("Unable to create location due to center. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFoor(String aFoor)
  {
    boolean wasSet = false;
    foor = aFoor;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoom(String aRoom)
  {
    boolean wasSet = false;
    room = aRoom;
    wasSet = true;
    return wasSet;
  }

  public String getFoor()
  {
    return foor;
  }

  public String getRoom()
  {
    return room;
  }
  /* Code from template association_GetOne */
  public SportCenter getCenter()
  {
    return center;
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
      existingCenter.removeLocation(this);
    }
    center.addLocation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    SportCenter placeholderCenter = center;
    this.center = null;
    if(placeholderCenter != null)
    {
      placeholderCenter.removeLocation(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "foor" + ":" + getFoor()+ "," +
            "room" + ":" + getRoom()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "center = "+(getCenter()!=null?Integer.toHexString(System.identityHashCode(getCenter())):"null");
  }
}