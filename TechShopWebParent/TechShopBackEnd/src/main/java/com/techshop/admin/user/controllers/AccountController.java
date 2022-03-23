package com.techshop.admin.user.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techshop.admin.security.TechShopUserDetails;
import com.techshop.admin.user.services.UserService;
import com.techshop.admin.utils.FileUploadUtil;
import com.techshop.common.entity.User;

@Controller
public class AccountController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/account")
	public String viewDetail(@AuthenticationPrincipal TechShopUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		User user = userService.getByEmail(email);
		model.addAttribute("user", user);
		
		return "/authentication/account_form";
	}
	
	@PostMapping("/account/update")
	public String saveAccountDetail(User user, RedirectAttributes redirectAttributes, 
			@RequestParam("image") MultipartFile multipartFile,
			@AuthenticationPrincipal TechShopUserDetails loggedUser) throws IOException {
		
		System.out.println(user);
		System.out.println("image: " + multipartFile.getOriginalFilename());
		
		if(!multipartFile.isEmpty()) {

			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			
			user.setPhotos(fileName);
			User saveUser = userService.updateAccount(user);	
			String uploadDir = "user-photos/" + saveUser.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			if(user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}
			userService.updateAccount(user);
		}
		
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		redirectAttributes.addFlashAttribute("message", "Your account has been updated!!!");
		
		//return ch�nh c�i user create or update
		return "redỉrect:/account";
	}
}
