package ca.mcgill.ecse321.sportcenter.dto;

import java.util.List;

public class CourseListDTO {
    
    private List<CourseResponseDTO> courses;

    public CourseListDTO(List<CourseResponseDTO> courses) {
        this.courses = courses;
    }

    public List<CourseResponseDTO> getCourses() {
        return courses;
    }
}
