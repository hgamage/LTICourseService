package com.LTI.CourseService.resource;

import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;
import com.LTI.CourseService.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseResource {

    @Autowired
    CourseService courseService;

    @GetMapping("/")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    public Optional<Course> getCourse(@PathVariable long courseId) {
        return courseService.searchCourseById(courseId);
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    public void addNewCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
    }

    @RequestMapping(value = "/{courseId}/enrollments", method = RequestMethod.POST)
    public void enrollToCourse(@RequestBody Enrollment enrollment, @PathVariable long courseId) {
        enrollment.setCourse(new Course(courseId, "","","", ""));
        courseService.enrollToCourse(enrollment);
    }

    @GetMapping("/{courseId}/enrollments")
    public List<Enrollment> getAllEnrollments(@PathVariable long courseId) {
        return courseService.getAllEnrollments(courseId);
    }

    @RequestMapping(value="/{courseId}", method=RequestMethod.PUT)
    public Course updateExistingCourse(@RequestBody Course course, @PathVariable long courseId) {
        return courseService.updateCourse(courseId, course);
    }

    @RequestMapping(value="/{courseId}", method = RequestMethod.DELETE)
    public void removeCourse(@PathVariable long courseId) {
        courseService.deleteCourse(courseId);
    }
}
