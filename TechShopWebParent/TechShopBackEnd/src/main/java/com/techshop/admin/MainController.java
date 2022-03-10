package com.techshop.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("")
	public String view() {
		return "index";
	}
	@GetMapping("/login")
	public String loginPage() {
		return "authentication/login";
	}
}
