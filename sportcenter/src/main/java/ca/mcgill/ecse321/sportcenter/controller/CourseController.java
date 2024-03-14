package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321.sportcenter.dto.AccountListDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.AccountResponseDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseListDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Course;
//import ca.mcgill.ecse321.sportcenter.service.CourseService;
import ca.mcgill.ecse321.sportcenter.model.Customer;

@RestController
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping(value={"/courses", "/courses/"})
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDTO createCourse(@RequestBody CourseRequestDTO course) { 
        Course createdCourse = courseService.createCourse();
        return new CourseResponseDTO(createdCourse);
    }

    @PutMapping(value={"/courses/{id}", "/courses/{id}/"})
    public CourseResponseDTO updateCourse(@PathVariable Integer id, @RequestBody CourseRequestDTO course) {
        Course updatedCourse = courseService.updateCourse();
        return new CourseResponseDTO(updatedCourse);
    }

    @GetMapping(value={"/courses/{id}", "/courses/{id}/"})
    public CourseResponseDTO findCourseById(@PathVariable Integer id) {
        return new CourseResponseDTO(courseService.findCourseById(id));
    }

    @GetMapping(value={"/courses/name", "/courses/name/"})
    public CourseResponseDTO findCourseByName(@RequestParam("name") String name){
        return new CourseResponseDTO(courseService.findCourseByName(name));
    }

    @GetMapping(value={"/courses", "/courses/"})
    public CourseListDTO findAllCourses() {
        return new CourseListDTO(CourseListDTO.courseListToCourseResponseDTOList(courseService.findAllCourses()));
    }



}
