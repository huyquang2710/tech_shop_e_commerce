package com.techshop.admin.user.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techshop.admin.exception.UserNotFoundException;
import com.techshop.admin.user.repositories.RoleRepository;
import com.techshop.admin.user.repositories.UserRepository;
import com.techshop.admin.user.services.UserService;
import com.techshop.common.entity.Role;
import com.techshop.common.entity.User;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	public static final int USERS_PER_PAGE = 4;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}
	public Page<User> findAllPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
		if(keyword != null) {
			return userRepository.findAllByKeyword(keyword, pageable);
		}
		return userRepository.findAll(pageable);
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
	public User getUserById(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not found any user with ID: " + id);
		}
	}
	//delete
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if(countById == null || countById == 0) {
			throw new UserNotFoundException("Count not found any user");
		}
		userRepository.deleteById(id);
	}
	public void updateEnabled(Integer id, boolean enabled) {
		userRepository.updateEnabledStatus(id, enabled);
	}
	@Override
	public User getByEmail(String email) {
		return userRepository.getUserbyEmail(email);
	}
	@Override
	public User updateAccount(User userInform) {
		User userInDB = userRepository.findById(userInform.getId()).get();
		
		if(!userInform.getPassword().isEmpty()) {
			userInDB.setPassword(userInform.getPassword());
			encodePass(userInDB);
		}
		if(userInform.getPhotos() != null) {
			userInDB.setPhotos(userInform.getPhotos());
		}
		userInDB.setFirstName(userInform.getFirstName());
		userInDB.setLastName(userInform.getLastName());
		
		return userRepository.save(userInDB);
	}
}
