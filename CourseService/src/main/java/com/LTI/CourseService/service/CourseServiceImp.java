package com.LTI.CourseService.service;

import com.LTI.CourseService.exception.NotFoundException;
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
    public List<Course> getAllCourses() throws Exception {
        List<Course> courses = new ArrayList<>();
        courseJpaRepository.findAll().forEach(courses::add);
        return courses;
    }

    @Override
    public Course searchCourseById(long courseId) throws Exception {
        return courseJpaRepository.findByWileyCourseId(courseId);
    }

    @Override
    public void saveCourse(Course course) throws Exception {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date currentDate = new Date();
        course.setCreateDate(df.format(currentDate));
        courseJpaRepository.save(course);
    }

    @Override
    public Course updateCourse(long courseId, Course course)throws Exception {
        Course courseObj = courseJpaRepository.findByWileyCourseId(courseId);
        if (courseObj == null) {
            throw new NotFoundException(String.format("There are no course available for course id: %d", courseId));
        } else {
            return courseJpaRepository.save(course);
        }
    }

    @Override
    public void deleteCourse(long courseId) throws Exception {
        courseJpaRepository.deleteById(courseId);
    }

    @Override
    public void courseEnrollment(Enrollment enrollment) throws Exception {
        enrollmentJpaRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> getAllEnrollments(long courseId) throws Exception {
        List<Enrollment> enrollments = new ArrayList<>();
        enrollmentJpaRepository.findByCourseWileyCourseId(courseId)
                .forEach(enrollments::add);
        return enrollments;
    }
}
