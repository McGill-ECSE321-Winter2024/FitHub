package ca.mcgill.ecse321.sportcenter.dto;

public class CourseDTO {

  public enum Difficulty { Beginner, Intermediate, Advanced }
  public enum Status { Approved, Pending, Closed, Disapproved }

  private int id;
  private String name;
  private Difficulty difficulty;
  private Status status;
  private String description;
  
  public CourseDTO() {
    
  }

  public CourseDTO(String aName, Difficulty aDifficulty, Status aStatus, String aDescription)
  {
    name = aName;
    difficulty = aDifficulty;
    status = aStatus;
    description = aDescription;
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

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "difficulty" + "=" + (getDifficulty() != null ? !getDifficulty().equals(this)  ? getDifficulty().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator");
  }
}