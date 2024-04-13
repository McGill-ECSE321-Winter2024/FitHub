package ca.mcgill.ecse321.sportcenter.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.SessionPackage;

public class SessionPackageResponseDTO {

    private int id;
    private int priceReduction;
    private int duration;
    private LocalDate date;
    private Course course;
    
    @SuppressWarnings("unused")
    public SessionPackageResponseDTO(){

    }

    public SessionPackageResponseDTO(SessionPackage sessionPackage)
    {
        priceReduction = sessionPackage.getPriceReduction();
        duration = sessionPackage.getDuration();
        date = sessionPackage.getDate();
        course = sessionPackage.getCourse();
        id = sessionPackage.getId();
    }

    //------------------------------Setters---------------------------//

    public void setPriceReduction(int aPriceReduction)
    {
        priceReduction = aPriceReduction;
    }

    public void setDuration(int aDuration)
    {
        duration = aDuration;
    }

    public void setDate(LocalDate aDate)
    {
        date = aDate;
    }

    public void setId(int aId)
    {
        id = aId;
    }

    public void setCourse(Course aNewCourse)
    {
        course = aNewCourse;
    }

    //-------------------------------Getters-----------------------------//

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

    public Course getCourse(int index)
    {
        return course;
    }

    
    
}
