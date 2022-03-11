package com.techshop.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.techshop.admin.user.repositories.UserRepository;
import com.techshop.common.entity.User;

public class TechShopDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.getUserbyEmail(email);
		if(user != null) {
			return new TechShopUserDetails(user);
		}
		throw new UsernameNotFoundException("Count not find user with email: " + email );
	}

}
