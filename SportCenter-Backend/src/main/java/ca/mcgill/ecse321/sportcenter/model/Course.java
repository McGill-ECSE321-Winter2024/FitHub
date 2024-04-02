package ca.mcgill.ecse321.sportcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;

@Entity
public class Course
{
  
  public enum Difficulty { Beginner, Intermediate, Advanced }
  public enum Status { Approved, Pending, Closed, Disapproved }

  @Id
  @GeneratedValue
  private int id;
  private String name;

  @Enumerated(EnumType.ORDINAL)
  private Difficulty difficulty;

  @Enumerated(EnumType.ORDINAL)
  private Status status;
  private String description;
  private int pricePerHour;
  private String icon1;
  private String icon2;
  private String url;
  
  @ManyToOne
  @JoinColumn(name="sport_center_id")
  private SportCenter sport_center;

  
  public Course() {
    
  }

  public Course(String aName, Difficulty aDifficulty, Status aStatus, String aDescription, int aPricePerHour, String aIcon1, String aIcon2, String aUrl, SportCenter aCenter)
  {
    name = aName;
    difficulty = aDifficulty;
    status = aStatus;
    description = aDescription;
    pricePerHour = aPricePerHour;
    icon1 = aIcon1;
    icon2 = aIcon2;
    url = aUrl;
    boolean didAddCenter = setCenter(aCenter);
    if (!didAddCenter)
    {
      throw new RuntimeException("Unable to create course due to center. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDifficulty(Difficulty aDifficulty)
  {
    boolean wasSet = false;
    difficulty = aDifficulty;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }


  public boolean setPricePerHour(int aPricePerHour)
  {
    boolean wasSet = false;
    pricePerHour = aPricePerHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setIcon1(String aIcon1)
  {
    boolean wasSet = false;
    icon1 = aIcon1;
    wasSet = true;
    return wasSet;
  }

  public boolean setIcon2(String aIcon2)
  {
    boolean wasSet = false;
    icon2 = aIcon2;
    wasSet = true;
    return wasSet;
  }

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
      existingCenter.removeCourse(this);
    }
    sport_center.addCourse(this);
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Difficulty getDifficulty()
  {
    return difficulty;
  }

  public Status getStatus()
  {
    return status;
  }

  public String getDescription()
  {
    return description;
  }

  public int getPricePerHour()
  {
    return pricePerHour;
  }

  public String getIcon1()
  {
    return icon1;
  }

  public String getIcon2()
  {
    return icon2;
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
    public SportCenter getCenter()
    {
      return sport_center;
    }

  public void delete()
  {
    SportCenter placeholderCenter = sport_center;
    this.sport_center = null;
    if(placeholderCenter != null)
    {
      placeholderCenter.removeCourse(this);
    }
  }
  
  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "difficulty" + "=" + (getDifficulty() != null ? !getDifficulty().equals(this)  ? getDifficulty().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "center = "+(getCenter()!=null?Integer.toHexString(System.identityHashCode(getCenter())):"null");
  }
}