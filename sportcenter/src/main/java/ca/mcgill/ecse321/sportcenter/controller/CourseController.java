package ca.mcgill.ecse321.sportcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import ca.mcgill.ecse321.sportcenter.dto.CourseListDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseRequestDTO;
import ca.mcgill.ecse321.sportcenter.dto.CourseResponseDTO;
import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.service.CourseService;

/**
 * <p>Controller class in charge of managing courses. It implements following use cases: </p>
 * <p>Create, update, read and delete a course </p>
 * @author Sahar
*/
@CrossOrigin(origins = "*")
@RestController
public class CourseController {
    @Autowired
    CourseService courseService;


    //--------------------------// Create Course //--------------------------//

    @PostMapping(value={"/courses", "/courses/"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO course) { 
        try {
            Course createdCourse = courseService.createCourse(course.getName(), course.getDescription(), course.getDifficulty().toString(), course.getStatus().toString());
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(createdCourse), HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Update Course //--------------------------//

    @PutMapping(value={"/courses/{id}", "/courses/{id}/"})
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Integer id, @RequestBody CourseResponseDTO course) {
        try {
            Course updatedCourse = courseService.updateCourse(course.getId(), course.getName(), course.getDescription(), course.getDifficulty().toString(), course.getStatus().toString());
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(updatedCourse), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }


    //--------------------------// Getters //--------------------------//

    @GetMapping(value={"/courses/{id}", "/courses/{id}/"})
    public ResponseEntity<CourseResponseDTO> findCourseById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(courseService.findCourseById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(), HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping(value={"/courses", "/courses/"})
    public ResponseEntity<?> findCourses(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "difficulty", required = false) String difficulty,
        @RequestParam(name = "status", required = false) String status) {

        if (name != null) {
            return findCourseByName(name);
        } else if (difficulty != null) {
            return findCoursesByDifficulty(difficulty);
        } else if (status != null) {
            return findCoursesByStatus(status);
        } else {
            return findAllCourses();
        }
    }

    public ResponseEntity<CourseResponseDTO> findCourseByName(@RequestParam(name = "name", required = false) String name){
        try {
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(courseService.findCourseByName(name)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(), HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<CourseListDTO> findAllCourses() {
        if (courseService.findAllCourses().size() > 0)
            return new ResponseEntity<CourseListDTO>(new CourseListDTO(CourseListDTO.courseListToCourseResponseDTOList(courseService.findAllCourses())), HttpStatus.OK);
        else
            return new ResponseEntity<CourseListDTO>(new CourseListDTO(CourseListDTO.courseListToCourseResponseDTOList(courseService.findAllCourses())), HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<CourseListDTO> findCoursesByDifficulty(@RequestParam(name = "difficulty", required = false) String difficulty) {
        try {
            List<Course> list = courseService.findCoursesByDifficulty(Course.Difficulty.valueOf(difficulty));
            return new ResponseEntity<CourseListDTO>(new CourseListDTO(CourseListDTO.courseListToCourseResponseDTOList(list)), HttpStatus.OK);
        } catch(IllegalArgumentException e){
            return new ResponseEntity<CourseListDTO>(new CourseListDTO(), HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<CourseListDTO> findCoursesByStatus(@RequestParam(name = "status", required = false) String status) {
        try {
            List<Course> list = courseService.findCoursesByStatus(Course.Status.valueOf(status));
            return new ResponseEntity<CourseListDTO>(new CourseListDTO(CourseListDTO.courseListToCourseResponseDTOList(list)), HttpStatus.OK);
        } catch(IllegalArgumentException e){
            return new ResponseEntity<CourseListDTO>(new CourseListDTO(), HttpStatus.NO_CONTENT);
        }
    }


    //--------------------------// Setters //--------------------------//

    @PutMapping(value={"/course-approval/{id}", "/course-approve/{id}/"})
    public ResponseEntity<CourseResponseDTO> approveCourse(@PathVariable Integer id) {
        try {
            Course course = courseService.findCourseById(id);
            courseService.approveCourse(course);
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(course), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(), HttpStatus.BAD_REQUEST);
        }
        
    }

    @PutMapping(value={"/course-disapproval/{id}", "/course-disapprove/{id}/"})
    public ResponseEntity<CourseResponseDTO> disapproveCourse(@PathVariable Integer id) {
        try{
            Course course = courseService.findCourseById(id);
            courseService.disapproveCourse(course);
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(course), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value={"/course-closing/{id}", "/course-close/{id}/"})
    public ResponseEntity<CourseResponseDTO> closeCourse(@PathVariable Integer id) {
        try {
            Course course = courseService.findCourseById(id);
            courseService.closeCourse(course);
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(course), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<CourseResponseDTO>(new CourseResponseDTO(), HttpStatus.BAD_REQUEST);
        }
    }


    //--------------------------// Delete Course //--------------------------//

    @DeleteMapping(value={"/courses/{id}", "/courses/{id}/"})
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id) {
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
