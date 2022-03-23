package com.techshop.admin.user.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techshop.admin.exception.UserNotFoundException;
import com.techshop.common.entity.Role;
import com.techshop.common.entity.User;


public interface UserService {
	List<User> findAll();
	Page<User> findAllPage(int pageNum, String sortField, String sortDir, String keyword);
	User saveUser(User user);
	boolean isEmailUnique(Integer id, String email);
	User getUserById(Integer id) throws UserNotFoundException;
	void delete(Integer id) throws UserNotFoundException;
	void updateEnabled(Integer id, boolean enabled);
	List<Role> listRole();
	User getByEmail(String email);
	User updateAccount(User userInform);
}
