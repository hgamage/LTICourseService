package com.LTI.CourseService.resource;

import com.LTI.CourseService.constants.GlobalConstants;
import com.LTI.CourseService.model.Response;
import com.LTI.CourseService.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class EnrollmentResourceTest {

    private MockMvc mockMvc;

    private GlobalConstants globalConstants;

    ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private EnrollmentResource enrollmentResource;

    @Mock
    CourseService courseService;

    @Test
    public void testGetAllEnrollmentsSuccess() throws Exception {
        long courseId = 55;
        mockMvc.perform(get("/courses/{courseId}/enrollments", courseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void searchCourseEnrollmentByIdSuccess() throws Exception {
        long courseId = 54;
        long enrollmentId = 10;
        mockMvc.perform(get("/courses/{courseId}/enrollments/{enrollmentId}", courseId, enrollmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testCourseEnrollmentSuccess() throws Exception {
        long courseId = 55;
        MvcResult result = mockMvc.perform(post("/courses/{courseId}/enrollments", courseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(globalConstants.ENROLLMENT_OBJECT))
                .andExpect(status().isOk()).andReturn();
        String resultContent  = result.getResponse().getContentAsString();
        Response response = mapper.readValue(resultContent, Response.class);
        Assertions.assertTrue(response.isSuccess() == Boolean.TRUE);
    }

    @Test
    public void updateExistingEnrollmentSuccess() throws Exception {
        long courseId = 55;
        long enrollmentId = 10;
        MvcResult result = mockMvc.perform(put("/courses/{courseId}/enrollments/{enrollmentId}", courseId, enrollmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(globalConstants.UPDATE_COURSE_OBJECT)).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void removeCourseEnrollmentSuccess() throws Exception {
        long courseId = 55;
        long enrollmentId = 10;
        MvcResult result = mockMvc.perform(delete("/courses/{courseId}/enrollments/{enrollmentId}", courseId, enrollmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(globalConstants.UPDATE_COURSE_OBJECT)).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void testGetAllEnrollmentsFail() throws Exception {
        long courseId = 55;
        when(courseService.getAllEnrollments(courseId)).thenReturn(null);
        Assertions.assertEquals(null, courseService.getAllEnrollments(courseId));
    }

}