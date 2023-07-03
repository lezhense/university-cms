package ua.foxminded.lezhenin.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.lezhenin.model.Course;
import ua.foxminded.lezhenin.service.impl.CourseServiceImpl;
import ua.foxminded.lezhenin.service.impl.GroupServiceImpl;
import ua.foxminded.lezhenin.service.impl.TeacherServiceImpl;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {
	private MockMvc mockMvc;

	@Autowired
	public CourseControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@MockBean
	private CourseServiceImpl courseService;

	@MockBean
	private GroupServiceImpl groupService;

	@MockBean
	private TeacherServiceImpl teacherService;

	@Test
	void testGetAllCourses() throws Exception {
		Course course1 = new Course("TestCourse1");
		Course course2 = new Course("TestCourse2");
		List<Course> courses = Arrays.asList(course1, course2);
		when(courseService.getAll()).thenReturn(courses);
		mockMvc.perform(get("/course/all")).andExpect(status().isOk()).andExpect(view().name("course_all"))
				.andExpect(model().attribute("courses", hasSize(2)))
				.andExpect(model().attribute("courses", is(courses)));
	}

}
