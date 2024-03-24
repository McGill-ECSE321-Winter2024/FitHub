package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CourseResponseDTO {

  public enum Difficulty { Beginner, Intermediate, Advanced }
  public enum Status { Approved, Pending, Closed, Disapproved }

  private int id;
  private String name;
  private String difficulty;
  private String status;
  private String description;
  private SportCenter center;
  
  @SuppressWarnings("unused")
  public CourseResponseDTO() {
    
  }
  
  public CourseResponseDTO(String aName, String aDescription, String aDifficulty, String aStatus)
  {
    name = aName;
    difficulty = aDifficulty;
    status = aStatus;
    description = aDescription;
  }

    public CourseResponseDTO(Course course)
  {
    id = course.getId();
    name = course.getName();
    difficulty = course.getDifficulty().toString();
    status = course.getStatus().toString();
    description = course.getDescription();
  }

//--------------------- Getters -------------------//

  public void setName(String aName)
  {
    this.name = aName;
  }

  public void setDifficulty(String aDifficulty)
  {
    this.difficulty = aDifficulty;
  }

  public void setStatus(String aStatus)
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

  public String getDifficulty()
  {
    return difficulty;
  }

  public String getStatus()
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
