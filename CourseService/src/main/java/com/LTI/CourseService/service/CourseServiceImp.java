package com.LTI.CourseService.service;

import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;
import com.LTI.CourseService.repository.CourseJpaRepository;
import com.LTI.CourseService.repository.EnrollmentJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CourseServiceImp implements CourseService {

    @Autowired
    CourseJpaRepository courseJpaRepository;

    @Autowired
    EnrollmentJpaRepository enrollmentJpaRepository;

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        courseJpaRepository.findAll().forEach(courses::add);
        return courses;
    }

    @Override
    public Optional<Course> searchCourseById(long courseId) {
        return courseJpaRepository.findById(courseId);
    }

    @Override
    public void saveCourse(Course course) {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date currentDate = new Date();
        course.setCreateDate(df.format(currentDate));
        courseJpaRepository.save(course);
    }

    @Override
    public Course updateCourse(long courseId, Course course) {
        return courseJpaRepository.save(course);
    }

    @Override
    public void deleteCourse(long courseId) {
        courseJpaRepository.deleteById(courseId);
    }

    @Override
    public void courseEnrollment(Enrollment enrollment) {
        enrollmentJpaRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> getAllEnrollments(long courseId) {
        List<Enrollment> enrollments = new ArrayList<>();
        enrollmentJpaRepository.findByCourseWileyCourseId(courseId)
                .forEach(enrollments::add);
        return enrollments;
    }
}
