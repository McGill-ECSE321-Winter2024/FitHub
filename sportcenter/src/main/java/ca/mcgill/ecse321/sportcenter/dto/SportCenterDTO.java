package ca.mcgill.ecse321.sportcenter.dto;

import java.sql.Time;

import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.Account;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Location;

public class SportCenterDTO {
    
  private String name;
  private Time openingTime;
  private Time closingTime;
  private String address;
  private String email;
  private String phoneNumber;
  private List<CourseDTO> courses;
  private List<LocationDTO> locations;
  //private List<AccountDTO> accounts;

  public SportCenterDTO() {
  }

  public SportCenterDTO(String name, Time openingTime, Time closingTime, String address, String email, String phoneNumber, List<CourseDTO> courses) { //List<LocationDTO> locations, List<AccountDTO> accounts) {
    this.name = name;
    this.openingTime = openingTime;
    this.closingTime = closingTime;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.courses = courses;
    this.locations = locations;
    //this.accounts = accounts;
  }

  public String getName() {
    return name;
  }

  public Time getOpeningTime() {
    return openingTime;
  }

  public void setOpeningTime(Time openingTime) {
    this.openingTime = openingTime;
  }

  public Time getClosingTime() {
    return closingTime;
  }

  public void setClosingTime(Time closingTime) {
    this.closingTime = closingTime;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
 
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<CourseDTO> getCourses() {
    return courses;
  }

  public void setCourses(List<CourseDTO> courses) {
    this.courses = courses;
  }

  public List<LocationDTO> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationDTO> locations) {
    this.locations = locations;
  }

  /*
  public List<AccountDTO> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<AccountDTO> accounts) {
    this.accounts = accounts;
  }
   */
    
}
