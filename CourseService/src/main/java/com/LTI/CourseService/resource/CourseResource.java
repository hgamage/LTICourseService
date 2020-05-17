package com.LTI.CourseService.resource;

import com.LTI.CourseService.exception.ApiRequestException;
import com.LTI.CourseService.exception.NotFoundException;
import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Response;
import com.LTI.CourseService.service.CourseService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseResource {

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    @ApiOperation(value = "Get all the available courses")
    public List<Course> getAllCourses() throws Exception {
            return courseService.getAllCourses();
    }

    @GetMapping(value = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get course by course id")
    public Course searchCourseById(@PathVariable final long courseId) throws Exception {
        Course courseObj = courseService.searchCourseById(courseId);
        if (courseObj == null) {
            throw new NotFoundException(String.format("course not found with given id: %d", courseId));
        }
        return courseObj;
    }

    @RequestMapping(value = "/", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Insert new course")
    public Response addNewCourse(@RequestBody final Course course) {
        try {
            courseService.saveCourse(course);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Cannot create new Course with given object please check the request"));
        }
        return new Response(String.format("Created new course with id: %d", course.getWileyCourseId()), Boolean.TRUE);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.PUT,
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update the course")
    public Course updateExistingCourse(@RequestBody final Course course, @PathVariable final long courseId) {
        Course courseObj = null;
        try {
            courseObj = courseService.updateCourse(courseId, course);
        } catch (NotFoundException e) {
            throw new NotFoundException(String.format("There are no course available for course id: %d", courseId));
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Unable to update record for given course id : %d", courseId));
        }
        return courseObj;
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "remove the course")
    public Response removeCourse(@PathVariable final long courseId) {
        try {
            courseService.deleteCourse(courseId);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Unable to delete course with given course id: %d", courseId));
        }
        return new Response(String.format("Removed course with id: %d", courseId), Boolean.TRUE);
    }
}
