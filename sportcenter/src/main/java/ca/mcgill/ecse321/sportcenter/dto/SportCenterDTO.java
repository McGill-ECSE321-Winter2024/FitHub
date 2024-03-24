package ca.mcgill.ecse321.sportcenter.dto;

import java.sql.Time;

import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse321.sportcenter.model.SportCenter;

public class SportCenterDTO {
    
  private String name;
  private Time openingTime;
  private Time closingTime;
  private String address;
  private String email;
  private String phoneNumber;
  private CourseListDTO courses;
  private LocationListDTO locations;
  private AccountListDTO accounts;

  private String error;

  public SportCenterDTO() {
  }

  public SportCenterDTO(String error) {
    this.error = error;
  }
  
  public SportCenterDTO(SportCenterDTO sportCenter) {
    this.name = sportCenter.getName();
    this.openingTime = sportCenter.getOpeningTime();
    this.closingTime = sportCenter.getClosingTime();
    this.address = sportCenter.getAddress();
    this.email = sportCenter.getEmail();
    this.phoneNumber = sportCenter.getPhoneNumber();
    this.courses = sportCenter.getCourses();
    this.locations = sportCenter.getLocations();
    this.accounts = sportCenter.getAccounts();

    this.error = "";
  }

  public SportCenterDTO(SportCenter sportCenter) {
    this.name = sportCenter.getName();
    this.openingTime = sportCenter.getOpeningTime();
    this.closingTime = sportCenter.getClosingTime();
    this.address = sportCenter.getAddress();
    this.email = sportCenter.getEmail();
    this.phoneNumber = sportCenter.getPhoneNumber();



    List<CourseResponseDTO> courseResponseList = CourseListDTO.courseListToCourseResponseDTOList(sportCenter.getCourses());
    CourseListDTO courses = new CourseListDTO(courseResponseList);

    // TO DOOOOOO CONVERT LOCATION INTO LOCATIONLIST
    List<LocationResponseDTO> locationResponseList = LocationListDTO.locationListToLocationResponseDTOList(sportCenter.getLocations());
    LocationListDTO locations = new LocationListDTO(locationResponseList);

    List<AccountResponseDTO> accountResponseList = AccountListDTO.accountListToAccountResponseDTOList(sportCenter.getAccounts());
    AccountListDTO accounts = new AccountListDTO(accountResponseList);

    this.courses = courses;
    this.locations = locations;
    this.accounts = accounts;

    this.error = "";
  }

  public SportCenterDTO(String name, Time openingTime, Time closingTime, String address, String email, String phoneNumber, CourseListDTO courses, LocationListDTO locations, AccountListDTO accounts) {
    this.name = name;
    this.openingTime = openingTime;
    this.closingTime = closingTime;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.courses = courses;
    this.locations = locations;
    this.accounts = accounts;

    this.error = "";
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

  public CourseListDTO getCourses() {
    return courses;
  }

  public void setCourses(CourseListDTO courses) {
    this.courses = courses;
  }

  public LocationListDTO getLocations() {
    return locations;
  }

  public void setLocations(LocationListDTO locations) {
    this.locations = locations;
  }

  public AccountListDTO getAccounts() {
    return accounts;
  }

  public void setAccounts(AccountListDTO accounts) {
    this.accounts = accounts;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
