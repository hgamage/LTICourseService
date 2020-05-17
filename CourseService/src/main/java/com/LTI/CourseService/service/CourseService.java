package com.LTI.CourseService.service;

import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;

import java.util.List;

/**
 * Course Service interface contain all the
 * operation used in the course service.
 *
 * @version 	1.0
 * @author 	heshan gamage
 */
public interface CourseService {

    /**
     * method getAllCourses retrieve all the
     * data relavant to the course.
     * @return List of Courses
     */
    List<Course> getAllCourses() throws Exception;

    /**
     * method searchCourseById will retrive the course object
     * with given course id in the parameters.
     * @return Course object
     * @param courseId of the return course object
     */
    Course searchCourseById(long courseId) throws Exception;

    /**
     * method saveCourse will save the new course.
     * @param course contain the object to be persist
     */
    void saveCourse(Course course) throws Exception;

    /**
     * method updateCourse will update the course data.
     * @param course contain the update body of course object
     * @param courseId which object going to update.
     * @return updated course object
     */
    Course updateCourse(long courseId, Course course) throws Exception;

    /**
     * method deleteCourse will delete the course with given.
     * id in the course parameter.
     * @param courseId contain the object id to be delete
     */
    void deleteCourse(long courseId) throws Exception;

    /**
     *  method courseEnrollment will enroll the user
     *  to the course.
     * @param enrollment contain the object to be save
     */
    void courseEnrollment(Enrollment enrollment) throws Exception;

    /**
     * method getAllEnrollments will all the
     * users enroll for the given course id.
     * @param courseId contain the id of the course
     * @return List of all the enrollments
     */
    List<Enrollment> getAllEnrollments(long courseId) throws Exception;
}
