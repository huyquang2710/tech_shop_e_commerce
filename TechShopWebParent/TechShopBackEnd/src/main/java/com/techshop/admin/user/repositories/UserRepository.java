package com.techshop.admin.user.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techshop.common.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	//get email use HQL
	@Query("SELECT u FROM User u WHERE u.email =:email")
	public User getUserbyEmail(@Param("email") String email);
	
	public Long countById(Integer id);
	
	//filter từng column
	//@Query("SELECT u FROM User u WHERE u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.email LIKE %?1%")
	//public Page<User> findAllByKeyword(String keyword, Pageable pageable);
	
	//filter hết
	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', " + " u.lastName) LIKE %?1%")
	public Page<User> findAllByKeyword(String keyword, Pageable pageable);
	
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}
