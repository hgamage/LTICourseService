package com.LTI.CourseService.resource;

import com.LTI.CourseService.exception.ApiRequestException;
import com.LTI.CourseService.exception.NotFoundException;
import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Enrollment;
import com.LTI.CourseService.model.Response;
import com.LTI.CourseService.service.CourseService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public List<Course> getAllCourses() throws Exception {
            return courseService.getAllCourses();
    }

    @GetMapping(value = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Get course by course id")
    public Course searchCourseById(@PathVariable long courseId) throws Exception {
        Course courseObj = courseService.searchCourseById(courseId);
        if (courseObj == null) {
            throw new NotFoundException(String.format("course not found with given id: %d", courseId));
        }
        return courseObj;
    }

    @RequestMapping(value="/", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Insert new course")
    public Response addNewCourse(@RequestBody Course course) {
        try {
            courseService.saveCourse(course);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Cannot create new Course with given object please check the request"));
        }
        return new Response(String.format("Created new course with id: %d", course.getWileyCourseId()), Boolean.TRUE);
    }

    @RequestMapping(value = "/{courseId}/enrollments", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Enroll user to a course")
    public Response courseEnrollment(@RequestBody Enrollment enrollment, @PathVariable long courseId) {
        try{
            enrollment.setCourse(new Course(courseId, "","","","","",""));
            courseService.courseEnrollment(enrollment);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Cannot enroll user to the course: %d", courseId));
        }
        return new Response(String.format("Enrolled new user: %d to course: %d", enrollment.getEnrollmentId(), courseId),
                Boolean.TRUE);
    }

    @GetMapping(value = "/{courseId}/enrollments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="get all the user enrollments for a course")
    public List<Enrollment> getAllEnrollments(@PathVariable long courseId) throws Exception {
        List<Enrollment> enrollments = courseService.getAllEnrollments(courseId);
        if (enrollments.isEmpty()) {
            throw new NotFoundException(String.format("There are no user enrollments for the give course id: %d", courseId));
        }
        return enrollments;
    }

    @RequestMapping(value="/{courseId}", method=RequestMethod.PUT,
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Update the course")
    public Course updateExistingCourse(@RequestBody Course course, @PathVariable long courseId) {
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

    @RequestMapping(value="/{courseId}", method = RequestMethod.DELETE)
    @ApiOperation(value="remove the course")
    public Response removeCourse(@PathVariable long courseId) {
        try {
            courseService.deleteCourse(courseId);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Unable to delete course with given course id: %d", courseId));
        }
        return new Response(String.format("Removed course with id: %d", courseId), Boolean.TRUE);
    }
}
