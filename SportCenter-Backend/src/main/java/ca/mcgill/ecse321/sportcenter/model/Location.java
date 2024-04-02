package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Location
{

  private String floor;
  private String room;
  
  @Id
  @GeneratedValue
  private int id;

  @ManyToOne
  @JoinColumn(name="sport_center_id")
  private SportCenter sport_center;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location() {
    
  }

  public Location(String aFloor, String aRoom, SportCenter aCenter)
  {
    floor = aFloor;
    room = aRoom;
    boolean didAddCenter = setCenter(aCenter);
    if (!didAddCenter)
    {
      throw new RuntimeException("Unable to create location due to center. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setFloor(String aFloor)
  {
    boolean wasSet = false;
    floor = aFloor;
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

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getFloor()
  {
    return floor;
  }

  public String getRoom()
  {
    return room;
  }

  public int getId()
  {
    return id;
  }

  public SportCenter getCenter()
  {
    return sport_center;
  }
  public boolean setCenter(SportCenter aCenter)
  {
    boolean wasSet = false;
    if (aCenter == null)
    {
      return wasSet;
    }

    SportCenter existingCenter = sport_center;
    sport_center = aCenter;
    if (existingCenter != null && !existingCenter.equals(aCenter))
    {
      existingCenter.removeLocation(this);
    }
    sport_center.addLocation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    SportCenter placeholderCenter = sport_center;
    this.sport_center = null;
    if(placeholderCenter != null)
    {
      placeholderCenter.removeLocation(this);
    }
  }

  public String toString()
  {
    return super.toString() + "["+
            "floor" + ":" + getFloor()+ "," +
            "room" + ":" + getRoom()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "center = "+(getCenter()!=null?Integer.toHexString(System.identityHashCode(getCenter())):"null");
  }
}