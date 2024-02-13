package ca.mcgill.ecse321.sportcenter.model;
// line 49 "model.ump"
// line 137 "model.ump"
public class Course
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Difficulty { Beginner, Intermediate, Advanced }
  public enum Status { Approved, Pending, Closed, Disaproved }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Course Attributes
  private String name;
  private Difficulty difficulty;
  private Status status;
  private String description;
  private int id;

  //Course Associations
  private SportCenter center;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Course(String aName, Difficulty aDifficulty, Status aStatus, String aDescription, int aId, SportCenter aCenter)
  {
    name = aName;
    difficulty = aDifficulty;
    status = aStatus;
    description = aDescription;
    id = aId;
    boolean didAddCenter = setCenter(aCenter);
    if (!didAddCenter)
    {
      throw new RuntimeException("Unable to create course due to center. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
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

  public int getId()
  {
    return id;
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
      existingCenter.removeCourse(this);
    }
    center.addCourse(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    SportCenter placeholderCenter = center;
    this.center = null;
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