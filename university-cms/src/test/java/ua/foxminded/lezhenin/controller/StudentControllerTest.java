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

import ua.foxminded.lezhenin.model.Student;
import ua.foxminded.lezhenin.service.impl.GroupServiceImpl;
import ua.foxminded.lezhenin.service.impl.RoleServiceImpl;
import ua.foxminded.lezhenin.service.impl.StudentServiceImpl;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {
	private MockMvc mockMvc;

	@Autowired
	public StudentControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@MockBean
	private StudentServiceImpl studentService;

	@MockBean
	private GroupServiceImpl groupService;

	@MockBean
	private RoleServiceImpl roleService;

	@Test
	void testStudents_ShouldGetAllStudents() throws Exception {
		Student student1 = new Student("John1", "Doe1", "John1Doe1", "123");
		Student student2 = new Student("Jane2", "Doe2", "Jane2Doe2", "123");
		List<Student> students = Arrays.asList(student1, student2);
		when(studentService.getAll()).thenReturn(students);
		mockMvc.perform(get("/student/all")).andExpect(status().isOk()).andExpect(view().name("student/student_all"))
				.andExpect(model().attribute("students", hasSize(2)))
				.andExpect(model().attribute("students", is(students)));
	}

}
