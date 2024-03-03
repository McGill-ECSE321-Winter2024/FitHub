package ca.mcgill.ecse321.sportcenter.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Course.Difficulty;
import ca.mcgill.ecse321.sportcenter.model.Course.Status;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;

public class CourseService {
    @Autowired
	CourseRepository courseRepository;

    @Transactional
    public Course createCourse(String name, String description, Difficulty diff, Status status){
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDifficulty(diff);
        course.setStatus(status);
        return courseRepository.save(course);
    }

    @Transactional 
    public Course getCourse(Integer id){
        Course course = courseRepository.findCourseById(id);
        return course;
    }

    @Transactional
    public List<Course> getAllCourses() {
        return toList(courseRepository.findAll());
    }

    @Transactional
    public List<Course> findCoursesByDifficulty(Course.Difficulty difficulty){
        List<Course> courses = courseRepository.findCoursesByDifficulty(difficulty);

        if (courses == null){

        }

        return courses;
    }

    @Transactional
    public List<Course> findCoursesByStatus(Course.Status status){
        List<Course> courses = courseRepository.findCoursesByStatus(status);

        if (courses == null){

        }

        return courses;
    }

    @Transactional
    public Course findCourseByName(String name){
        Course course = courseRepository.findCourseByName(name);

        if (course == null){

        }

        return course;
    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
