package com.techshop.admin.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techshop.admin.user.repositories.RoleRepository;
import com.techshop.admin.user.repositories.UserRepository;
import com.techshop.common.entity.Role;
import com.techshop.common.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}
	public List<Role> listRole() {
		return (List<Role>) roleRepository.findAll();
	}
	public User saveUser(User user) {
		encodePass(user);
		return userRepository.save(user);
	}
	
	private void encodePass(User user) {
		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
	}
}
