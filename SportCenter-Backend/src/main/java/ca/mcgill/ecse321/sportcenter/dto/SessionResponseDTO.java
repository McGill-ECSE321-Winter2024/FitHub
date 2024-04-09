package ca.mcgill.ecse321.sportcenter.dto;

import java.time.LocalDate;
import java.sql.Time;

import ca.mcgill.ecse321.sportcenter.model.Session;

public class SessionResponseDTO {

    private Integer id;
    private Time startTime;
    private Time endTime;
    private LocalDate date;
    private int capacity;
    private CourseRequestDTO course;
    private AccountResponseDTO supervisor;
    private LocationRequestDTO location;

    @SuppressWarnings("unused")
    public SessionResponseDTO(){
    }

    public SessionResponseDTO(Session session){
        this.id = session.getId();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.date = session.getDate();
        this.capacity = session.getCapacity();
        this.course = new CourseRequestDTO(session.getCourseType());
        this.supervisor = new AccountResponseDTO(session.getSupervisor());
        this.location = new LocationRequestDTO(session.getLocation().getFloor(), session.getLocation().getRoom());
    }

    //--------------------- Getters -------------------//

    public int getId(){
        return id;
    }

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

    public AccountResponseDTO getSupervisor(){
        return supervisor;
    }

    public LocationRequestDTO getLocation(){
        return location;
    }

    //--------------------- Setters -------------------//

    public void setId(int id){
        this.id = id;
    }
    
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

    public void setSupervisor(AccountResponseDTO supervisor){
        this.supervisor = supervisor;
    }

    public void setLocation(LocationRequestDTO locationDTO){
        this.location = locationDTO;
    }

    
}
