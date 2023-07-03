package ua.foxminded.lezhenin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ua.foxminded.lezhenin.exeptions.CourseAlreadyExistException;
import ua.foxminded.lezhenin.exeptions.GroupAlreadyExistException;
import ua.foxminded.lezhenin.exeptions.UserAlreadyExistException;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(UserAlreadyExistException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String userAlreadyExistException(final Model model) {
		model.addAttribute("errorMessage", "An account already exists for this username");
		return "error";
	}
	
	@ExceptionHandler(CourseAlreadyExistException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String courseAlreadyExistException(final Model model) {
		model.addAttribute("errorMessage", "Course already exists for this name");
		return "error";
	}
	
	@ExceptionHandler(GroupAlreadyExistException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String groupAlreadyExistException(final Model model) {
		model.addAttribute("errorMessage", "Group already exists for this name");
		return "error";
	}

}
