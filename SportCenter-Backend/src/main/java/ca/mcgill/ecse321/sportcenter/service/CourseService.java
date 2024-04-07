package ca.mcgill.ecse321.sportcenter.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.sportcenter.model.Course;
import ca.mcgill.ecse321.sportcenter.model.Instructor;
import ca.mcgill.ecse321.sportcenter.model.Session;
import ca.mcgill.ecse321.sportcenter.model.SessionPackage;
import ca.mcgill.ecse321.sportcenter.repository.CourseRepository;
import ca.mcgill.ecse321.sportcenter.repository.SessionPackageRepository;

/*
* <p> Service class in charge of managing courses. It implements following use cases: </p>
* <p> Create and update a course</p>
* <p> Getting courses according to their difficuly and their status.</p>
* <p> Propose new type of courses </p>
* <p> Approve a new course </p>
* <p> Disapprove a pending course </p>
* <p> Close an approved ourse </p>
* <p> User views course detail </p>
* @author Sahar
*/

@Service
public class CourseService {
    @Autowired
	CourseRepository courseRepository;

    @Autowired
    SessionPackageRepository sessionPackageRepository;

    @Autowired
    SessionService sessionService;

    //--------------------------// Create Course //--------------------------//

    @Transactional
    public Course createCourse(String name, String description, String diff, String status, Integer pricePerHour, String category, String url) {
        // Accumulate error messages
        StringBuilder errorMessage = new StringBuilder();
    
        //keep all course names unique
        try {
            findCourseByName(name);
            // If no exception is thrown, it means a course with the given name already exists
            throw new IllegalArgumentException("Course with the name '" + name + "' already exists!");
        } catch (IllegalArgumentException e){
            // Do nothing, as this means the course with the given name doesn't exist
        }
    
        // Input validation checks
        if (name == null || name.trim().isEmpty()) {
            errorMessage.append("Course name cannot be empty! ");
        }
        if (description == null || description.trim().isEmpty()) {
            errorMessage.append("Course description cannot be empty! ");
        }
        if (diff == null) {
            errorMessage.append("Course difficulty cannot be null! ");
        }
        if (status == null) {
            errorMessage.append("Course status cannot be null! ");
        }
        if (pricePerHour == null || pricePerHour <= 0) {
            errorMessage.append("Price per hour must be provided and greater than zero! ");
        }
    
        // Ensure the uniqueness of each course
        if (courseRepository.existsByName(name)) {
            errorMessage.append("Course already exists! ");
        }
    
        // If there are any errors, throw an exception
        if (errorMessage.length() > 0) {
            throw new IllegalArgumentException(errorMessage.toString().trim());
        }
    
        // If no errors, create and save the course
        Course course = new Course();
        course.setName(name.toLowerCase());
        course.setDescription(description);
        course.setDifficulty(Course.Difficulty.valueOf(diff));
        course.setStatus(Course.Status.valueOf(status));
        course.setPricePerHour(pricePerHour);
        course.setCategory(category);
        course.setUrl(url != null ? url : "none");
        courseRepository.save(course);
        return course;
    }
    
    @Transactional
    public Course proposeCourse(String name, String description, String diff, Integer pricePerHour, String category, String url) {
        // Accumulate error messages
        StringBuilder errorMessage = new StringBuilder();
    
        //keep all course names unique
        try {
            findCourseByName(name);
            // If no exception is thrown, it means a course with the given name already exists
            throw new IllegalArgumentException("Course with the name '" + name + "' already exists!");
        } catch (IllegalArgumentException e){
            // Do nothing, as this means the course with the given name doesn't exist
        }
    
        // Input validation checks
        if (name == null || name.trim().isEmpty()) {
            errorMessage.append("Course name cannot be empty! ");
        }
        if (description == null || description.trim().isEmpty()) {
            errorMessage.append("Course description cannot be empty! ");
        }
        if (diff == null) {
            errorMessage.append("Course difficulty cannot be null! ");
        }
        if (pricePerHour == null || pricePerHour <= 0) {
            errorMessage.append("Price per hour must be provided and greater than zero! ");
        }
    
        // Ensure the uniqueness of each course
        if (courseRepository.existsByName(name)) {
            errorMessage.append("Course already exists! ");
        }
    
        // If there are any errors, throw an exception
        if (errorMessage.length() > 0) {
            throw new IllegalArgumentException(errorMessage.toString().trim());
        }
    
        // If no errors, create and save the course
        Course course = new Course();
        course.setName(name.toLowerCase());
        course.setDescription(description);
        course.setDifficulty(Course.Difficulty.valueOf(diff));
        course.setStatus(Course.Status.valueOf("Pending"));
        course.setPricePerHour(pricePerHour);
        course.setCategory(category);
        course.setUrl(url != null ? url : "none");
        courseRepository.save(course);
        return course;
    }    
    
    //--------------------------// Update Course //--------------------------//

    @Transactional
    public Course updateCourse(Integer id, String name, String description, String diff, String status, Integer pricePerHour, String category, String url) {
        // Accumulate error messages
        StringBuilder errorMessage = new StringBuilder();
    
        // Input validation checks
        if (name == null || name.trim().isEmpty()) {
            errorMessage.append("Course name cannot be empty! ");
        }
        if (description == null || description.trim().isEmpty()) {
            errorMessage.append("Course description cannot be empty! ");
        }
        if (diff == null) {
            errorMessage.append("Course difficulty cannot be null! ");
        }
        if (status == null) {
            errorMessage.append("Course status cannot be null! ");
        }
    
        // If there are any errors, throw an exception
        if (errorMessage.length() > 0) {
            throw new IllegalArgumentException(errorMessage.toString().trim());
        }
    
        // Check if the course with the given ID exists
        Course existingCourse = findCourseById(id);
    
        // Check if the name has been changed and if so, ensure it's unique
        if (!existingCourse.getName().equalsIgnoreCase(name)) {
            Course courseWithNewName = courseRepository.findCourseByName(name);
            if (courseWithNewName != null) {
                throw new IllegalArgumentException("Course with the name '" + name + "' already exists!");
            }
        }
    
        // Update the existing course with the new information
        existingCourse.setName(name.toLowerCase());
        existingCourse.setDescription(description);
        existingCourse.setDifficulty(Course.Difficulty.valueOf(diff));
        existingCourse.setStatus(Course.Status.valueOf(status));
        existingCourse.setPricePerHour(pricePerHour);
        existingCourse.setCategory(category);
        existingCourse.setUrl(url != null ? url : "none");
        courseRepository.save(existingCourse);
        return existingCourse;
    }
    

    //--------------------------// Getters //--------------------------//  

    @Transactional 
    public Course findCourseById(Integer id){
        Course course = courseRepository.findCourseById(id);
        if (course == null){
            throw new IllegalArgumentException("There is no course with ID " + id +".");
        }
        return course;
    }

    @Transactional 
    public Course findCourseByName(String name){
        if (name != null){
            Course course = courseRepository.findCourseByName(name.toLowerCase());
            if (course == null){
                throw new IllegalArgumentException("There is no course with name " + name +".");
            }
            return course;
        } else {
            throw new IllegalArgumentException("Name can't be null.");
        }
    }

    @Transactional
    public List<Course> findAllCourses() {
        return toList(courseRepository.findAll());
    }

    @Transactional
    public List<Course> findCoursesByDifficulty(Course.Difficulty difficulty){
        List<Course> coursesByDifficulty = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (course.getDifficulty().equals(difficulty)){
                coursesByDifficulty.add(course);
            }
        }

        //If there are no courses of that difficulty existing, then stop the query. 
        if (coursesByDifficulty.size() == 0){
            throw new IllegalArgumentException("No courses of that difficulty exist!");
        }
        return coursesByDifficulty;
    }

    @Transactional
    public List<Course> findCoursesByStatus(Course.Status status){
        List<Course> coursesByStatus = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (course.getStatus().equals(status)){
                coursesByStatus.add(course);
            }
        }
        //If there are no courses of that status existing, then stop the query. 
        if (coursesByStatus.size() == 0){
            throw new IllegalArgumentException("No courses of that status exist!");
        }
        return coursesByStatus;
    }

    @Transactional
    public List<Course> findCoursesByInstructor(Instructor instructor){
        List<Course> coursesByInstructor = new ArrayList<>();
        List<Session> sessions = sessionService.findSessionsByInstructor(instructor);
        for (Session session : sessions) {
            coursesByInstructor.add(session.getCourseType());
        }
        return coursesByInstructor;
    }

    //--------------------------// Propose course //--------------------------//

    @Transactional
    public void approveCourse(Course course, int pricePerHour){
        if (course.getStatus() == Course.Status.Pending){
            course.setStatus(Course.Status.Approved);
            course.setPricePerHour(pricePerHour);
            courseRepository.save(course);
        } 
        else {
            throw new IllegalArgumentException("You can only approve a course which has a status of pending.");
        }
    }

    //--------------------------// Disapprove course //--------------------------//

    @Transactional
    public void disapproveCourse(Course course) {
        if (course.getStatus() == Course.Status.Pending){
            course.setStatus(Course.Status.Disapproved);
            courseRepository.save(course);
        } 
        else {
            throw new IllegalArgumentException("You can only disapprove a course which has a status of pending.");
        }
    }

    //--------------------------// Close course //--------------------------//

    @Transactional
    public void closeCourse(Course course){
        if (course.getStatus() == Course.Status.Approved){
            course.setStatus(Course.Status.Closed);
            courseRepository.save(course);
        }
        else {
            throw new IllegalArgumentException("You can only close a course which has a status of approved.");
        }
    }

     //--------------------------// Delete course //--------------------------//

     @Transactional
     public void deleteCourse(Integer id) {
        try {
            Course course = findCourseById(id);
            List<SessionPackage> list =  sessionPackageRepository.findSessionPackageByCourse(course);
            for(SessionPackage sessionPackage : list){
                sessionPackageRepository.delete(sessionPackage);
            }
            if (course != null){
                courseRepository.delete(course);
            }

        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("There are courses with id " + id);
        }
     }

    //--------------------------// Helper functions //--------------------------//

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}