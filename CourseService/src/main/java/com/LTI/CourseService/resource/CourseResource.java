package com.LTI.CourseService.resource;

import com.LTI.CourseService.exception.ApiRequestException;
import com.LTI.CourseService.exception.NotFoundException;
import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Response;
import com.LTI.CourseService.service.CourseService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseResource {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all the available courses")
    @PreAuthorize("hasAnyRole('ROLE_admin', 'ROLE_user')")
    public List<Course> getAllCourses() {
            return courseService.getAllCourses();
    }

    @GetMapping(value = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get course by course id")
    @PreAuthorize("hasAnyRole('ROLE_admin', 'ROLE_user')")
    public Course searchCourseById(@PathVariable final long courseId) {
        Course courseObj = courseService.searchCourseById(courseId);
        if (courseObj == null) {
            throw new NotFoundException(String.format("course not found with given id: %d", courseId));
        }
        return courseObj;
    }

    @RequestMapping(value = "/", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert new course")
    @PreAuthorize("hasAuthority('create_course')")
    public Response addNewCourse(@RequestBody final Course course) {
        courseService.saveCourse(course);
        return new Response(String.format("Created new course with id: %d", course.getWileyCourseId()), Boolean.TRUE);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.PUT,
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update the course")
    @PreAuthorize("hasRole('ROLE_admin')")
    public Course updateExistingCourse(@RequestBody final Course course, @PathVariable final long courseId) {
        Course courseObj = null;
        courseObj = courseService.updateCourse(courseId, course);
        return courseObj;
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "remove the course")
    @PreAuthorize("hasRole('ROLE_admin')")
    public Response removeCourse(@PathVariable final long courseId) {
        courseService.deleteCourse(courseId);
        return new Response(String.format("Removed course with id: %d", courseId), Boolean.TRUE);
    }
}
