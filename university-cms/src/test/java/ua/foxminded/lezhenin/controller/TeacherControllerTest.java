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

import ua.foxminded.lezhenin.model.Teacher;
import ua.foxminded.lezhenin.service.impl.RoleServiceImpl;
import ua.foxminded.lezhenin.service.impl.TeacherServiceImpl;

@WebMvcTest(TeacherController.class)
@AutoConfigureMockMvc(addFilters = false)
class TeacherControllerTest {
	private MockMvc mockMvc;

	@Autowired
	public TeacherControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@MockBean
	private TeacherServiceImpl teacherService;

	@MockBean
	private RoleServiceImpl roleService;

	@Test
	void testGetAllTeachers() throws Exception {
		Teacher teacher1 = new Teacher("John", "Doe", "JohnDoe", "123");
		Teacher teacher2 = new Teacher("Jane", "Doe", "JaneDoe", "123");
		List<Teacher> teachers = Arrays.asList(teacher1, teacher2);
		when(teacherService.getAll()).thenReturn(teachers);
		mockMvc.perform(get("/teacher/all")).andExpect(status().isOk())
		.andExpect(view().name("teacher/teacher_all"))
		.andExpect(model().attribute("teachers", hasSize(2)))
		.andExpect(model().attribute("teachers", is(teachers)));
	}

}
