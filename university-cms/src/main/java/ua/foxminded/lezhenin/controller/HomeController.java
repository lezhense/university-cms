package ua.foxminded.lezhenin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping("/profile")
	public String profile() {
		return "profile";
	}

}
