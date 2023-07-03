package ua.foxminded.lezhenin.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ua.foxminded.lezhenin.dto.GroupDto;
import ua.foxminded.lezhenin.model.Course;
import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.service.impl.CourseServiceImpl;
import ua.foxminded.lezhenin.service.impl.GroupServiceImpl;

@WebMvcTest(GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
class GroupControllerTest {

	private MockMvc mockMvc;

	@Autowired
	public GroupControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@MockBean
	private GroupServiceImpl groupService;

	@MockBean
	private CourseServiceImpl courseService;

	@Test
	void testGetAllGroups() throws Exception {
		Group group1 = new Group("TestGroup01");
		Group group2 = new Group("TestGroup02");
		List<Group> groups = Arrays.asList(group1, group2);
		when(groupService.getAll()).thenReturn(groups);
		mockMvc.perform(get("/group/all")).andExpect(status().isOk()).andExpect(view().name("group_all"))
				.andExpect(model().attribute("groups", hasSize(2))).andExpect(model().attribute("groups", is(groups)));
	}

	@Test
	void testShowGroupCreationForm() throws Exception {
		mockMvc.perform(get("/group/create")).andExpect(status().isOk())
				.andExpect(view().name("group/group_create_form")).andExpect(model().attributeExists("groupDto"));
	}

	@Test
	void testCreateGroupWithValidInput() throws Exception {
		GroupDto groupDto = new GroupDto();
		groupDto.setName("Group 1");
		Mockito.doNothing().when(groupService).createGroup(groupDto);
		mockMvc.perform(MockMvcRequestBuilders.post("/group/group_creation").param("name", "Group 1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/group/creation_success"));
	}

	@Test
	void testCreateGroupWithInvalidInput() throws Exception {
		GroupDto groupDto = new GroupDto();
		groupDto.setName("");
		Mockito.doNothing().when(groupService).createGroup(groupDto);
		mockMvc.perform(MockMvcRequestBuilders.post("/group/group_creation").param("name", ""))
				.andExpect(status().isOk()).andExpect(view().name("group/group_create_form"))
				.andExpect(model().attributeExists("groupDto"))
				.andExpect(model().attributeHasFieldErrors("groupDto", "name"));
	}

	@Test
	void testDeleteGroup() throws Exception {
		Integer id = 1;
		Mockito.doNothing().when(groupService).delete(id);
		mockMvc.perform(get("/group/delete/{id}", id)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/group/all"));
	}

	@Test
	void testEditGroup() throws Exception {
		Integer id = 1;
		Group group = new Group("Group 1");
		group.setId(id);
		Set<Course> courses = new HashSet<>();
		Course course1 = new Course("Course 1");
		course1.setId(1);
		Course course2 = new Course("Course 2");
		course2.setId(2);
		courses.add(course1);
		courses.add(course2);
		Mockito.when(groupService.get(id)).thenReturn(group);
		Mockito.when(courseService.getAll()).thenReturn(new ArrayList<>(courses));
		mockMvc.perform(get("/group/edit/{id}", id)).andExpect(status().isOk())
				.andExpect(view().name("group/group_form")).andExpect(model().attribute("group", group))
				.andExpect(model().attribute("setCourses", courses));
	}

	@Test
	void testSaveGroup() throws Exception {
		Group group = new Group("Group 1");
		group.setId(1);
		Mockito.when((groupService).save(group)).thenReturn(group);
		mockMvc.perform(MockMvcRequestBuilders.post("/group/save").param("id", "1").param("name", "Group 1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/group/all"));
	}
}
