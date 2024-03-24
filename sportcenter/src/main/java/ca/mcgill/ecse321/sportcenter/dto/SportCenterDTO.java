package ca.mcgill.ecse321.sportcenter.dto;

import java.sql.Time;

import java.util.List;

public class SportCenterDTO {
    
  private String name;
  private Time openingTime;
  private Time closingTime;
  private String address;
  private String email;
  private String phoneNumber;
  private List<CourseResponseDTO> courses;
  private List<LocationResponseDTO> locations;
  private AccountListDTO accounts;

  public SportCenterDTO() {
  }

  public SportCenterDTO(String name, Time openingTime, Time closingTime, String address, String email, String phoneNumber, List<CourseResponseDTO> courses, List<LocationResponseDTO> locations, AccountListDTO accounts) {
    this.name = name;
    this.openingTime = openingTime;
    this.closingTime = closingTime;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.courses = courses;
    this.locations = locations;
    this.accounts = accounts;
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

  public List<CourseResponseDTO> getCourses() {
    return courses;
  }

  public void setCourses(List<CourseResponseDTO> courses) {
    this.courses = courses;
  }

  public List<LocationResponseDTO> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationResponseDTO> locations) {
    this.locations = locations;
  }

  public AccountListDTO getAccounts() {
    return accounts;
  }

  public void setAccounts(AccountListDTO accounts) {
    this.accounts = accounts;
  }
}
