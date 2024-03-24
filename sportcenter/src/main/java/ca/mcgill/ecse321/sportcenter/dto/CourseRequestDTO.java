package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

public class CourseRequestDTO {

  public enum Difficulty { Beginner, Intermediate, Advanced }
  public enum Status { Approved, Pending, Closed, Disaproved }

  private int id;
  private String name;
  private Difficulty difficulty;
  private Status status;
  private String description;
  private SportCenter center;
  
  public CourseRequestDTO() {
    
  }

  public CourseRequestDTO(String aName, Difficulty aDifficulty, Status aStatus, String aDescription)
  {
    name = aName;
    difficulty = aDifficulty;
    status = aStatus;
    description = aDescription;
  }

    public CourseRequestDTO(Course course)
  {
    name = course.getName();
    difficulty = Difficulty.valueOf(course.getDifficulty().toString());
    status = Status.valueOf(course.getStatus().toString());
    description = course.getDescription();
  }

//--------------------- Getters -------------------//

  public void setName(String aName)
  {
    this.name = aName;
  }

  public void setDifficulty(Difficulty aDifficulty)
  {
    this.difficulty = aDifficulty;
  }

  public void setStatus(Status aStatus)
  {
    this.status = aStatus;
  }

  public void setDescription(String aDescription)
  {
    this.description = aDescription;
  }

  public void setId(int aId)
  {
    this.id = aId;
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

  public SportCenter getCenter()
  {
    return center;
  }
  
}