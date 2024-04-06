package ca.mcgill.ecse321.sportcenter.dto;

import java.time.LocalDate;
import java.sql.Time;


public class SessionRequestDTO {

    private Time startTime;
    private Time endTime;
    private LocalDate date;
    private int capacity;
    private CourseRequestDTO course;
    private AccountRequestDTO supervisor;
    private LocationRequestDTO location;

    public SessionRequestDTO(){

    }

    public SessionRequestDTO(Time aStartTime, Time aEndTime, LocalDate aDate, int aCapacity, AccountRequestDTO aSupervisor, CourseRequestDTO aCourseType, LocationRequestDTO aLocation){
        this.startTime = aStartTime;
        this.endTime = aEndTime;
        this.date = aDate;
        this.capacity = aCapacity;
        this.course = aCourseType;
        this.supervisor = aSupervisor;
        this.location = aLocation;
    }


    //--------------------- Getters -------------------//

    public LocalDate getDate(){
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

    public CourseRequestDTO getCourse(){
        return course;
    }

    public AccountRequestDTO getSupervisor(){
        return supervisor;
    }

    public LocationRequestDTO getLocation(){
        return location;
    }

    //--------------------- Setters -------------------//

    public void setDate(LocalDate date){
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

    public void setCourse(CourseRequestDTO course){
        this.course = course;
    }

    public void setSupervisor(AccountRequestDTO supervisor){
        this.supervisor = supervisor;
    }

    public void setLocation(LocationRequestDTO locationDTO){
        this.location = locationDTO;
    }
    
}
