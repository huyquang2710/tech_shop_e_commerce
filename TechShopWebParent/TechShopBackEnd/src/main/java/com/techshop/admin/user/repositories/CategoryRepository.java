package com.techshop.admin.user.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.techshop.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>{

}
