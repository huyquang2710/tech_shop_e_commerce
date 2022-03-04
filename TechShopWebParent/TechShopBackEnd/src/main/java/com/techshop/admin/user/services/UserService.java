package com.techshop.admin.user.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
		boolean isUpdatingUser = ( user.getId() != null );
		if(isUpdatingUser) {
			User existingUser = userRepository.findById(user.getId()).get();
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePass(user);
			}
		} else {
			encodePass(user);
		}
		return userRepository.save(user);
	}
	// encoding pass
	private void encodePass(User user) {
		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
	}
	
	//  check unique pass
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserbyEmail(email);
		
		if(userByEmail == null ) return true;
		
		boolean isCreatingNew = (id == null);
		if(isCreatingNew) {
			if(userByEmail != null) return false;
		} else {
			if(userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
	}
	// find by id
	public User getUserById(Integer id) throws UsernameNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UsernameNotFoundException("Could not found any user with ID: " + id);
		}
	}
	//delete
	public void delete(Integer id) throws UsernameNotFoundException {
		Long countById = userRepository.countById(id);
		if(countById == null || countById == 0) {
			throw new UsernameNotFoundException("Count not found any user");
		}
		userRepository.deleteById(id);
	}
}
