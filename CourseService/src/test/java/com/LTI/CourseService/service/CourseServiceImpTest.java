package com.LTI.CourseService.service;

import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;
import com.LTI.CourseService.repository.CourseJpaRepository;
import com.LTI.CourseService.repository.EnrollmentJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CourseServiceImpTest {

    @InjectMocks
    CourseServiceImp courseService;

    @Mock
    CourseJpaRepository courseJpaRepository;

    @Mock
    EnrollmentJpaRepository enrollmentJpaRepository;

    @Test
    void testGetAllCourses() throws Exception {
        when(courseJpaRepository.findAll()).thenReturn(Stream.of(
                new Course(1,"testTitle1", "testType","13/05/20 12:25:32","testInst", "13/05/20 12:25:32", "13/05/20 12:25:32"),
                new Course(2, "testTitle2", "testType2", "13/05/20 12:25:32", "testInst2", "13/05/20 12:25:32", "13/05/20 12:25:32")
        ).collect(Collectors.toList()));
        assertEquals(2, courseService.getAllCourses().size());
    }

    @Test
    void testSearchCourseById() throws Exception {
        long courseId = 55;
        Course course = new Course(55, "Testing", "test", "14/05/20 16:28:48", "inst2", "13/05/20 12:25:32", "13/05/20 12:25:32");
        when(courseJpaRepository.findByWileyCourseId(courseId)).thenReturn(
                new Course(55, "Testing", "test", "14/05/20 16:28:48", "inst2", "13/05/20 12:25:32", "13/05/20 12:25:32")
        );
        assertEquals(course, courseService.searchCourseById(courseId));
    }

    @Test
    void testSaveCourse() throws Exception {
        Course course = new Course(55, "Testing", "test", "14/05/20 16:28:48", "inst2", "13/05/20 12:25:32", "13/05/20 12:25:32");
        when(courseJpaRepository.save(course)).thenReturn(null);
        assertEquals(null, courseJpaRepository.save(course));
    }

    @Test
    void testUpdateCourse() throws Exception {
        Course course = new Course(55, "Testing1", "test", "14/05/20 16:28:48", "inst2", "13/05/20 12:25:32", "13/05/20 12:25:32");
        when(courseJpaRepository.save(course)).thenReturn(course);
        assertEquals(course, courseJpaRepository.save(course));
    }

    @Test
    void testDeleteCourse() throws Exception {
        long courseId = 55;
        courseService.deleteCourse(courseId);
        verify(courseJpaRepository, times(1)).deleteById(courseId);
    }

    @Test
    void courseEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment(0, 3, "tesNewtUser1", 55);
        when(enrollmentJpaRepository.save(enrollment)).thenReturn(null);
        assertEquals(null, enrollmentJpaRepository.save(enrollment));
    }

    @Test
    void getAllEnrollments() throws Exception {
        long courseId = 55;
        when(enrollmentJpaRepository.findByCourseWileyCourseId(courseId)).thenReturn(Stream.of(
                new Enrollment(0, 3, "tesNewtUser1", 55)
        ).collect(Collectors.toList()));
        assertEquals(1, enrollmentJpaRepository.findByCourseWileyCourseId(courseId).size());
    }
}