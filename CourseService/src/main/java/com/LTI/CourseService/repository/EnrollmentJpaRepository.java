package com.LTI.CourseService.repository;

import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentJpaRepository extends JpaRepository<Enrollment, Long> {
    public List<Enrollment> findByCourseWileyCourseId(long courseId);
}
