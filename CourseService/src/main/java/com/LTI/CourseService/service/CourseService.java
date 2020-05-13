package com.LTI.CourseService.service;

import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAllCourses();

    Optional<Course> searchCourseById(long courseId);

    void saveCourse(Course course);

    Course updateCourse(long courseId, Course course);

    void deleteCourse(long courseId);

    void enrollToCourse(Enrollment enrollment);

    List<Enrollment> getAllEnrollments(long courseId);
}
