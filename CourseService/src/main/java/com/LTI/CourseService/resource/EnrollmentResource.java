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

@RestController
public class EnrollmentResource {

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "courses/{courseId}/enrollments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all the user enrollments for a course")
    public List<Enrollment> getAllEnrollments(@PathVariable final long courseId) throws Exception {
        List<Enrollment> enrollments = courseService.getAllEnrollments(courseId);
        if (enrollments.isEmpty()) {
            throw new NotFoundException(String.format("There are no user enrollments for the give course id: %d", courseId));
        }
        return enrollments;
    }

    @GetMapping(value = "courses/{courseId}/enrollments/{enrollmentId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get enrollment by id")
    public Enrollment searchCourseEnrollmentById(@PathVariable final long courseId, @PathVariable final long enrollmentId) throws Exception {
        Enrollment enrollmentObj = courseService.searchCourseEnrollmentById(courseId, enrollmentId);
        if (enrollmentObj == null) {
            throw new NotFoundException(String.format("enrollment not found with given id: %d", courseId));
        }
        return enrollmentObj;
    }

    @RequestMapping(value = "courses/{courseId}/enrollments", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enroll user to a course")
    public Response courseEnrollment(@RequestBody final Enrollment enrollment, @PathVariable final long courseId) {
        try {
            enrollment.setCourse(Course.builder().wileyCourseId(courseId).build());
            courseService.courseEnrollment(enrollment);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Cannot enroll user to the course: %d", courseId));
        }
        return new Response(String.format("Enrolled new user: %d to course: %d", enrollment.getEnrollmentId(), courseId),
                Boolean.TRUE);
    }

    @RequestMapping(value = "courses/{courseId}/enrollments/{enrollmentId}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update enrollment")
    public Enrollment updateExistingEnrollment(@RequestBody final Enrollment enrollment,
                                               @PathVariable final long courseId, @PathVariable final long enrollmentId) {
        Enrollment enrollmentObj = null;
        try {
            enrollment.setCourse(Course.builder().wileyCourseId(courseId).build());
            enrollmentObj = courseService.updateCourseEnrollment(enrollmentId, enrollment);
        } catch (NotFoundException e) {
            throw new NotFoundException(String.format("There are no enrollment available for course id: %d with enrollment id: %d", courseId, enrollmentId));
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Unable to update record for given course id : %d with enrollment id: %d", courseId, enrollmentId));
        }
        return enrollmentObj;
    }

    @RequestMapping(value = "courses/{courseId}/enrollments/{enrollmentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "remove the enrollment")
    public Response removeCourseEnrollment(@PathVariable final long enrollmentId, @PathVariable final long courseId) {
        try {
            courseService.deleteCourseEnrollment(enrollmentId, courseId);
        } catch (Exception e) {
            throw new ApiRequestException(String.format("Unable to delete enrollment with given enrollment id: %d", enrollmentId));
        }
        return new Response(String.format("Removed enrollment with id: %d", enrollmentId), Boolean.TRUE);
    }
}
