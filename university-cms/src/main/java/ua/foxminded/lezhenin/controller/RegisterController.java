package ua.foxminded.lezhenin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ua.foxminded.lezhenin.dto.UserDto;
import ua.foxminded.lezhenin.service.impl.StudentServiceImpl;
import ua.foxminded.lezhenin.service.impl.TeacherServiceImpl;

@Controller
@RequestMapping("/register")
public class RegisterController {
	private final StudentServiceImpl studentServiceImpl;
	private final TeacherServiceImpl teacherServiceImpl;

	@Autowired
	public RegisterController(StudentServiceImpl studentServiceImpl, TeacherServiceImpl teacherServiceImpl) {
		this.studentServiceImpl = studentServiceImpl;
		this.teacherServiceImpl = teacherServiceImpl;
	}

	@GetMapping("/student_register")
	public String showStudentRegistrationForm(Model model) {
		model.addAttribute("user", new UserDto());
		return "register/student_signup_form";
	}

	@GetMapping("/teacher_register")
	public String showTeacherRegistrationForm(Model model) {
		model.addAttribute("user", new UserDto());
		return "register/teacher_signup_form";
	}

	@GetMapping("/register_success")
	public String showRegisterSuccess() {
		return "done_success";
	}

	@PostMapping("/process_student_register")
	public String registerStudent(final @Valid UserDto userDto, final BindingResult bindingResult, final Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("user", userDto);
			return "register/student_signup_form";
		}
		studentServiceImpl.register(userDto);
		return "redirect:/register/register_success";
	}

	@PostMapping("/process_teacher_register")
	public String registerTeacher(final @Valid UserDto userDto, final BindingResult bindingResult, final Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", userDto);
			return "register/teacher_signup_form";
		}
		teacherServiceImpl.register(userDto);
		return "redirect:/register/register_success";
	}

}
