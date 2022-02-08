package com.techshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.techshop.admin.user.repositories.UserRepository;
import com.techshop.common.entity.Role;
import com.techshop.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = testEntityManager.find(Role.class, 1);
		User createUser = new User("quang@gmail.com", "123456", "Quang", "Huy");
		createUser.addRole(roleAdmin);
		
		User savedUser = userRepository.save(createUser);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateUserManyRole() {
		User createUser = new User("huyquang@gmail.com", "123456", "Tao", "Thao");
		
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		createUser.addRole(roleEditor);
		createUser.addRole(roleAssistant);
		
		User saveUser = userRepository.save(createUser);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	@Test
	public void findAllUser() {
		Iterable<User> listUser = userRepository.findAll();
		listUser.forEach(user -> System.out.println(user));
	}
	@Test
	public void findById() {
		User findUser = userRepository.findById(1).get();
		System.out.println(findUser);
		assertThat(findUser).isNotNull();
	}
	@Test
	public void updateUser() {
		User findById = userRepository.findById(1).get();
		findById.setEnabled(true);
		findById.setEmail("huqyuang@gmail.com");
		
		userRepository.save(findById);
		
	}
	@Test
	public void deleteUser() {
		Integer userId = 2;
		userRepository.deleteById(userId);
	}
}
