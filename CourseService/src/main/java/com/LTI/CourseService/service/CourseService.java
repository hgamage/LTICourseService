package com.LTI.CourseService.service;

import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses() throws Exception;

    Course searchCourseById(long courseId) throws Exception;

    void saveCourse(Course course) throws Exception;

    Course updateCourse(long courseId, Course course) throws Exception;

    void deleteCourse(long courseId) throws Exception;

    void courseEnrollment(Enrollment enrollment) throws Exception;

    List<Enrollment> getAllEnrollments(long courseId) throws Exception;
}
