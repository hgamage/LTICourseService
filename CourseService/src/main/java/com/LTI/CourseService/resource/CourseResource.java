package com.LTI.CourseService.resource;

import com.LTI.CourseService.exception.ApiRequestException;
import com.LTI.CourseService.exception.NotFoundException;
import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;
import com.LTI.CourseService.service.CourseService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseResource {

    @Autowired
    CourseService courseService;

    @GetMapping("/")
    @ApiOperation(value="Get all the available courses")
    public List<Course> getAllCourses() {
            return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    @ApiOperation(value="Get course by course id")
    public Optional<Course> searchCourseById(@PathVariable long courseId) {
        Optional<Course> courseObj = courseService.searchCourseById(courseId);
        if (courseObj.isEmpty()) {
            throw new NotFoundException(String.format("course not found with given id: %d", courseId));
        }
        return courseObj;
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    @ApiOperation(value="Insert new course")
    public void addNewCourse(@RequestBody Course course) {
        try {
            courseService.saveCourse(course);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Cannot create new Course with given object please check the request"));
        }
    }

    @RequestMapping(value = "/{courseId}/enrollments", method = RequestMethod.POST)
    @ApiOperation(value="Enroll user to a course")
    public void courseEnrollment(@RequestBody Enrollment enrollment, @PathVariable long courseId) {
        try{
            enrollment.setCourse(new Course(courseId, "","","","","",""));
            courseService.courseEnrollment(enrollment);
        } catch (Exception e) {
            throw new ApiRequestException(String.format(""));
        }
    }

    @GetMapping("/{courseId}/enrollments")
    @ApiOperation(value="get all the user enrollments for a course")
    public List<Enrollment> getAllEnrollments(@PathVariable long courseId) {
        List<Enrollment> enrollments = courseService.getAllEnrollments(courseId);
        if (enrollments.isEmpty()) {
            throw new NotFoundException(String.format("There are no user enrollments for the give course id: %d", courseId));
        }
        return enrollments;
    }

    @RequestMapping(value="/{courseId}", method=RequestMethod.PUT)
    @ApiOperation(value="Update the course")
    public Course updateExistingCourse(@RequestBody Course course, @PathVariable long courseId) {
        Course courseObj = null;
        try {
            courseObj = courseService.updateCourse(courseId, course);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Unable to update record for given course id : %d", courseId));
        }
        return courseObj;
    }

    @RequestMapping(value="/{courseId}", method = RequestMethod.DELETE)
    @ApiOperation(value="remove the course")
    public void removeCourse(@PathVariable long courseId) {
        try {
            courseService.deleteCourse(courseId);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Unable to delete course with given course id: %d", courseId));
        }

    }
}
