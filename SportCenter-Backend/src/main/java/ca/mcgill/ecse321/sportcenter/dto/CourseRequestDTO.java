package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.SportCenter;

public class CourseRequestDTO {

  public enum Difficulty { Beginner, Intermediate, Advanced }
  public enum Status { Approved, Pending, Closed, Disapproved }

  private String name;
  private String difficulty;
  private String status;
  private String description;
  private int pricePerHour;
  private String icon1;
  private String icon2;
  private String url;
  private SportCenter center;
  
  public CourseRequestDTO() {
    
  }

  public CourseRequestDTO(String aName, String aDescription, String aDifficulty, String aStatus, int aPricePerHour, String aIcon1, String aIcon2, String aUrl)
  {
    name = aName;
    difficulty = aDifficulty.trim();
    status = aStatus.trim();
    description = aDescription;
    pricePerHour = aPricePerHour;
    icon1 = aIcon1.trim();
    icon2 = aIcon2.trim();
    url = aUrl.trim();
  }

    public CourseRequestDTO(Course course)
  {
    name = course.getName();
    difficulty = course.getDifficulty().toString();
    status = course.getStatus().toString();
    description = course.getDescription();
    pricePerHour = course.getPricePerHour();
    icon1 = course.getIcon1();
    icon2 = course.getIcon2();
    url = course.getUrl();
  }

//--------------------- Getters -------------------//

  public void setName(String aName)
  {
    this.name = aName;
  }

  public void setDifficulty(String aDifficulty)
  {
    this.difficulty = aDifficulty.trim();
  }

  public void setStatus(String aStatus)
  {
    this.status = aStatus.trim();
  }

  public void setDescription(String aDescription)
  {
    this.description = aDescription;
  }

  public void setPricePerHour(int aPricePerHour)
  {
    pricePerHour = aPricePerHour;
  }

  public void setIcon1(String aIcon1)
  {
    icon1 = aIcon1;
  }

  public void setIcon2(String aIcon2)
  {
    icon2 = aIcon2;
  }

  public void setUrl(String aUrl)
  {
    url = aUrl;
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

  public SportCenter getCenter()
  {
    return center;
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
  
}