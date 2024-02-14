/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Time;
import java.sql.Date;

// line 34 "model.ump"
// line 127 "model.ump"
public class Session
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Session Attributes
  private Time startTime;
  private Time endTime;
  private Date date;
  private int capacity;
  private int id;

  //Session Associations
  private Instructor supervisor;
  private Course courseType;
  private Location location;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Session(Time aStartTime, Time aEndTime, Date aDate, int aCapacity, int aId, Instructor aSupervisor, Course aCourseType, Location aLocation)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    capacity = aCapacity;
    id = aId;
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