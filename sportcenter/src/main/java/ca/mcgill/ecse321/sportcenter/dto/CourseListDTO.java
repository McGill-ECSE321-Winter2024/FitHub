package ca.mcgill.ecse321.sportcenter.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.Course;

public class CourseListDTO {
    
    private List<CourseResponseDTO> courses;

    public CourseListDTO() {
        this.courses = new ArrayList<>();
    }

    public CourseListDTO(List<CourseResponseDTO> courses) {
        this.courses = courses;
    }

    public List<CourseResponseDTO> getCourses() {
        return courses;
    }

    public static <T> List<CourseResponseDTO> courseListToCourseResponseDTOList(List<T> courses) {
    List<CourseResponseDTO> list = new ArrayList<>();
    for (T course : courses) {
        CourseResponseDTO dto = new CourseResponseDTO((Course) course);
        list.add(dto);
    }
    return list;
    }
}
