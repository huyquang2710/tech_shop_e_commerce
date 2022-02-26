package com.techshop.admin.user.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techshop.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	//get email use HQL
	@Query("SELECT u FROM User u WHERE u.email =:email")
	public User getUserbyEmail(@Param("email") String email);
}
