package ua.foxminded.lezhenin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.model.Role;
import ua.foxminded.lezhenin.model.Student;
import ua.foxminded.lezhenin.service.impl.GroupServiceImpl;
import ua.foxminded.lezhenin.service.impl.RoleServiceImpl;
import ua.foxminded.lezhenin.service.impl.StudentServiceImpl;

@Controller
@RequestMapping("/student")
public class StudentController {
	private final StudentServiceImpl studentServiceImpl;
	private final GroupServiceImpl groupServiceImpl;
	private final RoleServiceImpl roleServiceImpl;

	@Autowired
	public StudentController(StudentServiceImpl studentService, RoleServiceImpl roleServiceImpl,
			GroupServiceImpl groupServiceImpl) {
		this.studentServiceImpl = studentService;
		this.roleServiceImpl = roleServiceImpl;
		this.groupServiceImpl = groupServiceImpl;
	}

	@GetMapping("/index")
	public String index() {
		return "student/index";
	}

	@GetMapping("/all")
	public String showAllStudents(Model model) {
		List<Student> students = studentServiceImpl.getAll();
		model.addAttribute("students", students);
		return "student/student_all";
	}

	@GetMapping("/edit/{id}")
	public String editStudent(@PathVariable("id") Integer id, Model model) {
		Student student = studentServiceImpl.get(id);
		List<Role> listRoles = roleServiceImpl.getAll();
		List<Group> listGroups = groupServiceImpl.getAll();
		model.addAttribute("student", student);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("listGroups", listGroups);
		return "student/student_form";
	}

	@PostMapping("/save")
	public String saveStudent(Student student) {
		studentServiceImpl.save(student);
		return "redirect:/student/all";
	}

	@GetMapping("/delete/{id}")
	public String deleteStudent(@PathVariable("id") Integer id, Model model) {
		studentServiceImpl.delete(id);
		return "redirect:/student/all";
	}

}
