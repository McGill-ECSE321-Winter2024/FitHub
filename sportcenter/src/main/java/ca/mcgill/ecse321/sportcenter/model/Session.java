package ca.mcgill.ecse321.sportcenter.model;

import java.sql.Time;
import java.sql.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Session
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Session Attributes
  @Id
  @GeneratedValue
  private int id;
  private Time startTime;
  private Time endTime;
  private Date date;
  private int capacity;

  //Session Associations
  private Instructor supervisor;
  private Course courseType;
  private Location location;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Session(Time aStartTime, Time aEndTime, Date aDate, int aCapacity, Instructor aSupervisor, Course aCourseType, Location aLocation)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    capacity = aCapacity;
    if (!setSupervisor(aSupervisor))
    {
      throw new RuntimeException("Unable to create Session due to aSupervisor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCourseType(aCourseType))
    {
      throw new RuntimeException("Unable to create Session due to aCourseType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setLocation(aLocation))
    {
      throw new RuntimeException("Unable to create Session due to aLocation. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCapacity(int aCapacity)
  {
    boolean wasSet = false;
    capacity = aCapacity;
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

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Date getDate()
  {
    return date;
  }

  public int getCapacity()
  {
    return capacity;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Instructor getSupervisor()
  {
    return supervisor;
  }
  /* Code from template association_GetOne */
  public Course getCourseType()
  {
    return courseType;
  }
  /* Code from template association_GetOne */
  public Location getLocation()
  {
    return location;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSupervisor(Instructor aNewSupervisor)
  {
    boolean wasSet = false;
    if (aNewSupervisor != null)
    {
      supervisor = aNewSupervisor;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCourseType(Course aNewCourseType)
  {
    boolean wasSet = false;
    if (aNewCourseType != null)
    {
      courseType = aNewCourseType;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setLocation(Location aNewLocation)
  {
    boolean wasSet = false;
    if (aNewLocation != null)
    {
      location = aNewLocation;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    supervisor = null;
    courseType = null;
    location = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "capacity" + ":" + getCapacity()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "supervisor = "+(getSupervisor()!=null?Integer.toHexString(System.identityHashCode(getSupervisor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "courseType = "+(getCourseType()!=null?Integer.toHexString(System.identityHashCode(getCourseType())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "location = "+(getLocation()!=null?Integer.toHexString(System.identityHashCode(getLocation())):"null");
  }
}