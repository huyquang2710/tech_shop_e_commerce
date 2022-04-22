package com.techshop.admin.user.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.techshop.common.entity.Brand;

@Repository
public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer>{

}
