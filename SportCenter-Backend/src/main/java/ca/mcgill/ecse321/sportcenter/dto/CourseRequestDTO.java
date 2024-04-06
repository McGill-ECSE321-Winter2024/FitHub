package ca.mcgill.ecse321.sportcenter.dto;

import java.util.Locale.Category;

import org.hibernate.boot.archive.scan.spi.ClassDescriptor.Categorization;

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
  private String category;
  private String url;
  private SportCenter center;
  
  public CourseRequestDTO() {
    
  }

  public CourseRequestDTO(String aName, String aDescription, String aDifficulty, String aStatus, int aPricePerHour, String aCategory, String aUrl)
  {
    name = aName;
    difficulty = aDifficulty.trim();
    status = aStatus.trim();
    description = aDescription;
    pricePerHour = aPricePerHour;
    category = aCategory.trim();
    url = aUrl.trim();
  }

    public CourseRequestDTO(Course course)
  {
    name = course.getName();
    difficulty = course.getDifficulty().toString();
    status = course.getStatus().toString();
    description = course.getDescription();
    pricePerHour = course.getPricePerHour();
    category = course.getCategory();
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

  public void setCategory(String aCategory)
  {
    category = aCategory;
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

  public String getCategory()
  {
    return category;
  }

  public String getUrl()
  {
    return url;
  }
  
}