package ca.mcgill.ecse321.sportcenter.dto;

import java.sql.Date;
import java.sql.Time;


public class SessionRequestDTO {

    private Time startTime;
    private Time endTime;
    private Date date;
    private int capacity;
    private CourseDTO course;
    private AccountRequestDTO supervisor;
    private LocationDTO location;

    public SessionRequestDTO(){

    }

    public SessionRequestDTO(Time aStartTime, Time aEndTime, Date aDate, int aCapacity, AccountRequestDTO aSupervisor, CourseDTO aCourseType, LocationDTO aLocation){
        this.startTime = aStartTime;
        this.endTime = aEndTime;
        this.date = aDate;
        this.capacity = aCapacity;
        this.course = aCourseType;
        this.supervisor = aSupervisor;
        this.location = aLocation;
    }


    //--------------------- Getters -------------------//

    public Date getDate(){
        return date;
    }

    public Time getStartTime(){
        return startTime;
    }

    public Time getEndTime(){
        return endTime;
    }

    public int getCapacity(){
        return capacity;
    }

    public CourseDTO getCourse(){
        return course;
    }

    public AccountRequestDTO getSupervisor(){
        return supervisor;
    }

    public LocationDTO getLocation(){
        return location;
    }

    //--------------------- Setters -------------------//

    public void setDate(Date date){
        this.date = date;
    }

    public void setStartTime(Time startTime){
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime){
        this.endTime = endTime;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public void setCourse(CourseDTO course){
        this.course = course;
    }

    public void setSupervisor(AccountRequestDTO supervisor){
        this.supervisor = supervisor;
    }

    public void setLocation(LocationDTO locationDTO){
        this.location = locationDTO;
    }
    
}
