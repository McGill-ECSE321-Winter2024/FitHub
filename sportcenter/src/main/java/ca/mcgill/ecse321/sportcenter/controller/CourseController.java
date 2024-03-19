package ca.mcgill.ecse321.sportcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321.sportcenter.dto.CourseListDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.service.CourseService;

@RestController
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping(value={"/courses", "/courses/"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO course) { 
        Course createdCourse = courseService.createCourse(course.getName(), course.getDescription(), Course.Difficulty.valueOf(course.getDifficulty().toString()), Course.Status.valueOf(course.getStatus().toString()));
        return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(createdCourse), HttpStatus.CREATED);
    }

    @PutMapping(value={"/courses/{id}", "/courses/{id}/"})
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Integer id, @RequestBody CourseResponseDTO course) {
        Course updatedCourse = courseService.updateCourse(course.getId(), course.getName(), course.getDescription(), Course.Difficulty.valueOf(course.getDifficulty().toString()), Course.Status.valueOf(course.getStatus().toString()));
        return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(updatedCourse), HttpStatus.ACCEPTED);
    }

    @GetMapping(value={"/courses/{id}", "/courses/{id}/"})
    public ResponseEntity<CourseResponseDTO> findCourseById(@PathVariable Integer id) {
        return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(courseService.findCourseById(id)), HttpStatus.FOUND);
    }

    @GetMapping(value={"/courses/name", "/courses/name/"})
    public ResponseEntity<CourseResponseDTO> findCourseByName(@RequestParam("name") String name){
        return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(courseService.findCourseByName(name)), HttpStatus.FOUND);
    }

    @GetMapping(value={"/courses", "/courses/"})
    public ResponseEntity<CourseListDTO> findAllCourses() {
        if (courseService.findAllCourses().size() > 0)
            return new ResponseEntity<CourseListDTO>(new CourseListDTO(CourseListDTO.courseListToCourseResponseDTOList(courseService.findAllCourses())), HttpStatus.OK);
        else
            return new ResponseEntity<CourseListDTO>(new CourseListDTO(CourseListDTO.courseListToCourseResponseDTOList(courseService.findAllCourses())), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value={"/courses/difficulty", "/courses/difficulty/"})
    public ResponseEntity<CourseListDTO> findCoursesByDifficulty(@RequestParam("difficulty") String difficulty) {
        if (courseService.findCoursesByDifficulty(Course.Difficulty.valueOf(difficulty)).size() > 0)
        return new ResponseEntity<CourseListDTO>(new CourseListDTO(
            CourseListDTO.courseListToCourseResponseDTOList(
                courseService.findCoursesByDifficulty(Course.Difficulty.valueOf(difficulty)))), 
                HttpStatus.OK);
    else
        return new ResponseEntity<CourseListDTO>(new CourseListDTO(
            CourseListDTO.courseListToCourseResponseDTOList(
                courseService.findCoursesByDifficulty(Course.Difficulty.valueOf(difficulty)))), 
                HttpStatus.NO_CONTENT);
    }

    @GetMapping(value={"/courses/status", "/courses/status/"})
    public ResponseEntity<CourseListDTO> findCoursesByStatus(@RequestParam("status") String status) {
        if (courseService.findCoursesByStatus(Course.Status.valueOf(status)).size() > 0)
        return new ResponseEntity<CourseListDTO>(new CourseListDTO(
            CourseListDTO.courseListToCourseResponseDTOList(
                courseService.findCoursesByStatus(Course.Status.valueOf(status)))), 
                HttpStatus.OK);
    else
        return new ResponseEntity<CourseListDTO>(new CourseListDTO(
            CourseListDTO.courseListToCourseResponseDTOList(
                courseService.findCoursesByStatus(Course.Status.valueOf(status)))), 
                HttpStatus.NO_CONTENT);
    }

    @PutMapping(value={"/course-approve/{id}", "/course-approve/{id}/"})
    public ResponseEntity<CourseResponseDTO> approveCourse(@PathVariable Integer id) {
        Course course = courseService.findCourseById(id);
        courseService.approveCourse(course);
        return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(course), HttpStatus.ACCEPTED);
    }

    @PutMapping(value={"/course-disapprove/{id}", "/course-disapprove/{id}/"})
    public ResponseEntity<CourseResponseDTO> disapproveCourse(@PathVariable Integer id) {
        Course course = courseService.findCourseById(id);
        courseService.disapproveCourse(course);
        return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(course), HttpStatus.ACCEPTED);
    }

    @PutMapping(value={"/course-close/{id}", "/course-close/{id}/"})
    public ResponseEntity<CourseResponseDTO> closeCourse(@PathVariable Integer id) {
        Course course = courseService.findCourseById(id);
        courseService.closeCourse(course);
        return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(course), HttpStatus.ACCEPTED);
    }


}
