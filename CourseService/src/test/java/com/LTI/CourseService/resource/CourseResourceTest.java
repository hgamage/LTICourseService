package com.LTI.CourseService.resource;


import com.LTI.CourseService.constants.GlobalConstants;
import com.LTI.CourseService.model.Course;
import com.LTI.CourseService.model.Response;
import com.LTI.CourseService.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class CourseResourceTest {

    private MockMvc mockMvc;

    private GlobalConstants globalConstants;

    @InjectMocks
    private CourseResource courseResource;


    ObjectMapper mapper = new ObjectMapper();

    @Mock
    private CourseService courseService;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(courseResource).build();
    }

    @Test
    public void testGetAllTestCasesSuccess() throws Exception {

        when(courseService.getAllCourses()).thenReturn(Stream.of(
                new Course(1,"testTitle1", "testType","13/05/20 12:25:32","testInst", "13/05/20 12:25:32", "13/05/20 12:25:32"),
                new Course(2, "testTitle2", "testType2", "13/05/20 12:25:32", "testInst2", "13/05/20 12:25:32", "13/05/20 12:25:32")
        ).collect(Collectors.toList()));
        mockMvc.perform(get("/courses/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(globalConstants.ALL_COURSES));
        verify(courseService).getAllCourses();
    }

    @Test
    public void testAddNewCourseSuccess() throws Exception {
        MvcResult result = mockMvc.perform(post("/courses/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(globalConstants.COURSE_OBJECT))
                .andExpect(status().isOk()).andReturn();
        String resultContent  = result.getResponse().getContentAsString();
        Response response = mapper.readValue(resultContent, Response.class);
        Assertions.assertTrue(response.isSuccess() == Boolean.TRUE);
    }

    @Test
    public void testSearchByIdSuccess() throws Exception {
        long courseId = 54;
        mockMvc.perform(get("/courses/{courseId}", courseId)
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
    public void testRemoveCourseSuccess() throws Exception {
        long courseId = 55;
        MvcResult result = mockMvc.perform(delete("/courses/{courseId}", courseId)
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String resultContent  = result.getResponse().getContentAsString();
        Response response = mapper.readValue(resultContent, Response.class);
        Assertions.assertTrue(response.isSuccess() == Boolean.TRUE);
    }

    @Test
    public void testUpdateExistingCourseSuccess() throws Exception {
        long courseId = 55;
        MvcResult result = mockMvc.perform(put("/courses/{courseId}", courseId)
                .contentType(MediaType.APPLICATION_JSON)
        .content(globalConstants.UPDATE_COURSE_OBJECT)).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void testGetAllEnrollmentsSuccess() throws Exception {
        long courseId = 55;
        mockMvc.perform(get("/courses/{courseId}/enrollments", courseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testSearchCourseByIdTestFail() throws Exception {
        String courseId = "0";
        mockMvc.perform(get("/courses/{courseId}", courseId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void testUpdateExistingCourseFail() throws Exception {
        long courseId = 47;
        Course course = new Course(47, "Testing1", "test", "14/05/20 16:28:48", "inst2", "13/05/20 12:25:32", "13/05/20 12:25:32");
        when(courseService.updateCourse(courseId, course)).thenReturn(null);
        Assertions.assertEquals(null, courseService.updateCourse(courseId, course));
    }

    @Test
    public void testGetAllEnrollmentsFail() throws Exception {
        long courseId = 55;
        when(courseService.getAllEnrollments(courseId)).thenReturn(null);
        Assertions.assertEquals(null, courseService.getAllEnrollments(courseId));
    }
}
