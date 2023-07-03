package ua.foxminded.lezhenin.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.lezhenin.model.Course;
import ua.foxminded.lezhenin.model.Role;
import ua.foxminded.lezhenin.model.Teacher;
import ua.foxminded.lezhenin.service.impl.RoleServiceImpl;
import ua.foxminded.lezhenin.service.impl.TeacherServiceImpl;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	private final TeacherServiceImpl teacherServiceImpl;
	private final RoleServiceImpl roleServiceImpl;

	@Autowired
	public TeacherController(TeacherServiceImpl teacherService, RoleServiceImpl roleServiceImpl) {
		this.teacherServiceImpl = teacherService;
		this.roleServiceImpl = roleServiceImpl;
	}

	@GetMapping("/index")
	public String index() {
		return "teacher/index";
	}

	@GetMapping("/all")
	public String showAllTeachers(Model model) {
		List<Teacher> teachers = teacherServiceImpl.getAll();
		model.addAttribute("teachers", teachers);
		return "teacher/teacher_all";
	}

	@GetMapping("/assigned_courses")
	public String showAllTeacherCourses(Principal principal, Model model) {
		String userName = principal.getName();
		Teacher teacher = teacherServiceImpl.getByName(userName);
		Set<Course> courses = teacher.getCourses();
		model.addAttribute("courses", courses);
		return "teacher/teacher_courses";
	}

	@GetMapping("/edit/{id}")
	public String editTeacher(@PathVariable("id") Integer id, Model model) {
		Teacher teacher = teacherServiceImpl.get(id);
		List<Role> listRoles = roleServiceImpl.getAll();
		model.addAttribute("teacher", teacher);
		model.addAttribute("listRoles", listRoles);
		return "teacher/teacher_form";
	}

	@PostMapping("/save")
	public String saveTeacher(Teacher teacher) {
		teacherServiceImpl.save(teacher);
		return "redirect:/teacher/all";
	}

	@GetMapping("/delete/{id}")
	public String deleteTeacher(@PathVariable("id") Integer id, Model model) {
		teacherServiceImpl.delete(id);
		return "redirect:/teacher/all";
	}
}
