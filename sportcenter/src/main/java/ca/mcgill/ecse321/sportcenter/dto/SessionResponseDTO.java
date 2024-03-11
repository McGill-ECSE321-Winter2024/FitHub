package ca.mcgill.ecse321.sportcenter.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.sportcenter.model.Session;

public class SessionResponseDTO {

    private Integer id;
    private Time startTime;
    private Time endTime;
    private Date date;
    private int capacity;
    private CourseDTO course;
    private AccountResponseDTO supervisor;
    private LocationDTO location;

    @SuppressWarnings("unused")
    public SessionResponseDTO(){

    }

    public SessionResponseDTO(Session session){
        this.id = session.getId();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.date = session.getDate();
        this.capacity = session.getCapacity();
        this.course = new CourseDTO(session.getCourseType());
        this.supervisor = new AccountResponseDTO(session.getSupervisor());
        this.location = new LocationDTO(session.getLocation().getFloor(), session.getLocation().getRoom());
    }

    //--------------------- Getters -------------------//

    public int getId(){
        return id;
    }

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

    public AccountResponseDTO getSupervisor(){
        return supervisor;
    }

    public LocationDTO getLocationDTO(){
        return location;
    }

    //--------------------- Setters -------------------//

    public void setId(int id){
        this.id = id;
    }
    
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

    public void setSupervisor(AccountResponseDTO supervisor){
        this.supervisor = supervisor;
    }

    public void setLocationDTO(LocationDTO locationDTO){
        this.location = locationDTO;
    }

    
}
