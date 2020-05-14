package com.LTI.CourseService.repository;

import com.LTI.CourseService.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface CourseJpaRepository extends JpaRepository<Course, Long> {
    String findByTitle(String title);
    Course findByWileyCourseId(long courseId);
}
