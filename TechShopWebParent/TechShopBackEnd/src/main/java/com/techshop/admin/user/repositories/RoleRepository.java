package com.techshop.admin.user.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techshop.common.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{

}
