package ca.mcgill.ecse321.sportcenter.model;

import java.util.*;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SessionPackage
{

  @Id
  @GeneratedValue
  private int id;
  private int priceReduction;
  private int duration;
  private LocalDate date;

  @ManyToOne
  private Course course;

  public SessionPackage(){

  }

  public SessionPackage(int aPriceReduction, int aDuration, LocalDate aDate, Course aCourse)
  {
    priceReduction = aPriceReduction;
    duration = aDuration;
    date = aDate;
    course = aCourse;
  }


  public boolean setPriceReduction(int aPriceReduction)
  {
    boolean wasSet = false;
    priceReduction = aPriceReduction;
    wasSet = true;
    return wasSet;
  }

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(LocalDate aDate)
  {
    boolean wasSet = false;
    date = aDate;
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

  public boolean setCourse(Course aNewCourse)
  {
    boolean wasSet = false;
    if (aNewCourse != null)
    {
      course = aNewCourse;
      wasSet = true;
    }
    return wasSet;
  }

  public int getPriceReduction()
  {
    return priceReduction;
  }

  public int getDuration()
  {
    return duration;
  }

  public int getId()
  {
    return id;
  }

  public LocalDate getDate()
  {
    return date;
  }

  public Course getCourse()
  {
    return course;
  }

  public void delete()
  {
    course = null;
  }
}