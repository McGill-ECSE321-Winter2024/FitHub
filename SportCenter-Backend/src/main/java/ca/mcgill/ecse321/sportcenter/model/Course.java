package ca.mcgill.ecse321.sportcenter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  //JPA ANNOTATION NEEDED
  private List<SessionPackage> packages;
  
  public Course() {
    
  }

  public Course(String aName, Difficulty aDifficulty, Status aStatus, String aDescription, int aId, int aPricePerHour, String aIcon1, String aIcon2, String aUrl, SportCenter aCenter)
  {
    name = aName;
    difficulty = aDifficulty;
    status = aStatus;
    description = aDescription;
    id = aId;
    pricePerHour = aPricePerHour;
    icon1 = aIcon1;
    icon2 = aIcon2;
    url = aUrl;
    packages = new ArrayList<SessionPackage>();
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

  public SportCenter getCenter()
  {
    return sport_center;
  }

  public SessionPackage getPackage(int index)
  {
    SessionPackage aPackage = packages.get(index);
    return aPackage;
  }

  public List<SessionPackage> getPackages()
  {
    List<SessionPackage> newPackages = Collections.unmodifiableList(packages);
    return newPackages;
  }

  public int numberOfPackages()
  {
    int number = packages.size();
    return number;
  }

  public boolean hasPackages()
  {
    boolean has = packages.size() > 0;
    return has;
  }

  public int indexOfPackage(SessionPackage aPackage)
  {
    int index = packages.indexOf(aPackage);
    return index;
  }
  /* Code from template association_GetOne */
  public SportCenter getCenter()
  {
    return center;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPackages()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPackages()
  {
    return 3;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addPackage(SessionPackage aPackage)
  {
    boolean wasAdded = false;
    if (packages.contains(aPackage)) { return false; }
    if (numberOfPackages() >= maximumNumberOfPackages())
    {
      return wasAdded;
    }

    packages.add(aPackage);
    if (aPackage.indexOfCourse(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPackage.addCourse(this);
      if (!wasAdded)
      {
        packages.remove(aPackage);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removePackage(SessionPackage aPackage)
  {
    boolean wasRemoved = false;
    if (!packages.contains(aPackage))
    {
      return wasRemoved;
    }

    int oldIndex = packages.indexOf(aPackage);
    packages.remove(oldIndex);
    if (aPackage.indexOfCourse(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPackage.removeCourse(this);
      if (!wasRemoved)
      {
        packages.add(oldIndex,aPackage);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetOptionalNToMany */
  public boolean setPackages(SessionPackage... newPackages)
  {
    boolean wasSet = false;
    ArrayList<SessionPackage> verifiedPackages = new ArrayList<SessionPackage>();
    for (SessionPackage aPackage : newPackages)
    {
      if (verifiedPackages.contains(aPackage))
      {
        continue;
      }
      verifiedPackages.add(aPackage);
    }

    if (verifiedPackages.size() != newPackages.length || verifiedPackages.size() > maximumNumberOfPackages())
    {
      return wasSet;
    }

    ArrayList<SessionPackage> oldPackages = new ArrayList<SessionPackage>(packages);
    packages.clear();
    for (SessionPackage aNewPackage : verifiedPackages)
    {
      packages.add(aNewPackage);
      if (oldPackages.contains(aNewPackage))
      {
        oldPackages.remove(aNewPackage);
      }
      else
      {
        aNewPackage.addCourse(this);
      }
    }

    for (SessionPackage anOldPackage : oldPackages)
    {
      anOldPackage.removeCourse(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPackageAt(SessionPackage aPackage, int index)
  {  
    boolean wasAdded = false;
    if(addPackage(aPackage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPackages()) { index = numberOfPackages() - 1; }
      packages.remove(aPackage);
      packages.add(index, aPackage);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePackageAt(SessionPackage aPackage, int index)
  {
    boolean wasAdded = false;
    if(packages.contains(aPackage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPackages()) { index = numberOfPackages() - 1; }
      packages.remove(aPackage);
      packages.add(index, aPackage);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPackageAt(aPackage, index);
    }
    return wasAdded;
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

  public void delete()
  {
    ArrayList<SessionPackage> copyOfPackages = new ArrayList<SessionPackage>(packages);
    packages.clear();
    for(SessionPackage aPackage : copyOfPackages)
    {
      aPackage.removeCourse(this);
    }
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