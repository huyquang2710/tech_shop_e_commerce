package com.techshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	@Test
	public void testEncoderPass() {
		BCryptPasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();
		String pass = "quang";
		String passEn = passwordEncoder.encode(pass);
		
		System.out.println(passEn);
		
		//check 2 co khop nhau ko
		boolean matches = passwordEncoder.matches(pass, passEn);
		
		assertThat(matches).isTrue();
	}
}
