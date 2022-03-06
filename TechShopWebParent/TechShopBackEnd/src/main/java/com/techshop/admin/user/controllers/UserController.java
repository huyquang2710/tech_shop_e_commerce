package com.techshop.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techshop.admin.user.services.UserService;
import com.techshop.common.entity.Role;
import com.techshop.common.entity.User;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	// first page
	@GetMapping("/users")
	public String listFirstPage(Model model) {
		return listAllByPage(1, model);
	}
	
	//pageable
	@GetMapping("/users/page/{pageNum}")
	public String listAllByPage(@PathVariable("pageNum") int pageNum, Model model) {
		Page<User> pageUser = userService.findAllPage(pageNum);
		List<User> userList = pageUser.getContent();
		
		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if(endCount > pageUser.getTotalElements()) {
			endCount = pageUser.getTotalElements();
		}
		
		System.out.println("Page Num: " + pageNum);
		System.out.println("Total element: " + pageUser.getTotalElements());
		System.out.println("Total page: " + pageUser.getTotalPages());
		System.out.println("statrt count: " + startCount);
		System.out.println("end count: " + endCount);
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageUser.getTotalElements());
		model.addAttribute("totalPages", pageUser.getTotalPages());
		model.addAttribute("user", userList);
		return "users";
	}
	@GetMapping("/users/new")
	public String user_form(Model model) {
		List<Role> roleList = userService.listRole();
		
		User user = new User();
		
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("rolesList", roleList);
		model.addAttribute("pageTitle", "Create New User.");
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
		userService.saveUser(user);	
		return "redirect:/users";
	}
	
	// edit
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes attributes) {
		try {
			User user = userService.getUserById(id);
			List<Role> roleList = userService.listRole();
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Update User: " + user.getFirstName() + " " + user.getLastName());
			model.addAttribute("rolesList", roleList);
			return "user_form";
		} catch (UsernameNotFoundException e) {
			attributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
		}
		
	}
	// delete
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			userService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The User has been deleted");
		} catch (UsernameNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/users";
	}
	// update enable status
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateEnabledStatus(@PathVariable(name="id") Integer id, @PathVariable("status") boolean enabled, Model model, RedirectAttributes attributes) {
		userService.updateEnabled(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID: " + id + " has been " + status;
		attributes.addFlashAttribute("message", message);
		
		return "redirect:/users";
	}

}
