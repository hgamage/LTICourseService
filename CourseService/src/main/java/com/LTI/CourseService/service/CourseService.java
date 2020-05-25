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
    List<Course> getAllCourses();

    /**
     * method updateCourse will update the course data.
     * @param courseId id of the course.
     * @return updated course object
     */
    Course searchCourseById(long courseId);

    /**
     * method saveCourse will save the new course.
     * @param course contain the object to be persist
     */
    void saveCourse(Course course);

    /**
     * method updateCourse will update the course data.
     * @param course contain the update body of course object
     * @param courseId which object going to update.
     * @return updated course object
     */
    Course updateCourse(long courseId, Course course);

    /**
     * method deleteCourse will delete the course with given.
     * id in the course parameter.
     * @param courseId contain the object id to be delete
     */
    void deleteCourse(long courseId);

    /**
     *  method courseEnrollment will enroll the user
     *  to the course.
     * @param enrollment contain the object to be save
     */
    void courseEnrollment(Enrollment enrollment);

    /**
     * method getAllEnrollments will all the
     * users enroll for the given course id.
     * @param courseId contain the id of the course
     * @return List of all the enrollments
     */
    List<Enrollment> getAllEnrollments(long courseId);

    /**
     * method updateCourseEnrollment will update the
     * user enrollemnet for the course.
     * @param enrollmentId contain the id of the enrollment
     * @param enrollment contain the update enrollment details
     * @return updated enrollment object.
     */
    Enrollment updateCourseEnrollment(long enrollmentId, Enrollment enrollment);

    /**
     * method deleteCourseEnrollment will delete the
     * course enrollment.
     * @param enrollmentId contain the id of the enrollment
     * @param courseId contain the id of the course
     */
    void deleteCourseEnrollment(long enrollmentId, long courseId);

    /**
     * method searchCourseById will retrive the course object
     * with given course id in the parameters.
     * @return Course object
     * @param courseId course id of the enrollment
     * @param enrollmentId id of the enrollment
     */
    Enrollment searchCourseEnrollmentById(long courseId, long enrollmentId);
}
