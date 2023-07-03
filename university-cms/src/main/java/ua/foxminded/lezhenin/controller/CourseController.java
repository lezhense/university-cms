package ua.foxminded.lezhenin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ua.foxminded.lezhenin.dto.CourseDto;
import ua.foxminded.lezhenin.model.Course;
import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.model.Teacher;
import ua.foxminded.lezhenin.service.impl.CourseServiceImpl;
import ua.foxminded.lezhenin.service.impl.GroupServiceImpl;
import ua.foxminded.lezhenin.service.impl.TeacherServiceImpl;

@Controller
@RequestMapping("/course")
public class CourseController {
	private final CourseServiceImpl courseServiceImpl;
	private final GroupServiceImpl groupServiceImpl;
	private final TeacherServiceImpl teacherServiceImpl;

	@Autowired
	public CourseController(CourseServiceImpl courseServiceImpl, GroupServiceImpl groupServiceImpl,
			TeacherServiceImpl teacherServiceImpl) {
		this.courseServiceImpl = courseServiceImpl;
		this.groupServiceImpl = groupServiceImpl;
		this.teacherServiceImpl = teacherServiceImpl;
	}

	@GetMapping("/all")
	public String getAllCourses(Model model) {
		List<Course> courses = courseServiceImpl.getAll();
		model.addAttribute("courses", courses);
		return "course_all";
	}

	@GetMapping("/create")
	public String showCourseCreationForm(Model model) {
		CourseDto courseDto = new CourseDto();
		model.addAttribute("courseDto", courseDto);
		return "course/course_create_form";
	}

	@GetMapping("/creation_success")
	public String showCourseCreationSuccess() {
		return "done_success";
	}

	@PostMapping("/course_creation")
	public String createCourse(final @Valid CourseDto courseDto, final BindingResult bindingResult, final Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("courseDto", courseDto);
			return "course/course_create_form";
		}
		courseServiceImpl.createCourse(courseDto);
		return "redirect:/course/creation_success";
	}

	@GetMapping("/delete/{id}")
	public String deleteCourse(@PathVariable("id") Integer id, Model model) {
		courseServiceImpl.delete(id);
		return "redirect:/course/all";
	}

	@GetMapping("/edit/{id}")
	public String editCourse(@PathVariable("id") Integer id, Model model) {
		Course course = courseServiceImpl.get(id);
		List<Teacher> listTeachers = teacherServiceImpl.getAll();
		List<Group> listGroups = groupServiceImpl.getAll();
		model.addAttribute("course", course);
		model.addAttribute("listTeachers", listTeachers);
		model.addAttribute("listGroups", listGroups);
		return "course/course_form";
	}

	@PostMapping("/save")
	public String saveCourse(Course course) {
		courseServiceImpl.save(course);
		return "redirect:/course/all";
	}

}
