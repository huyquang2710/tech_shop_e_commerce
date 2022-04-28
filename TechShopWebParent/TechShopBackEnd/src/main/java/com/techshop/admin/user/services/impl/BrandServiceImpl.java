package com.techshop.admin.user.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techshop.admin.exception.BrandNotFoundException;
import com.techshop.admin.user.repositories.BrandRepository;
import com.techshop.admin.user.services.BrandService;
import com.techshop.common.entity.Brand;

@Service
public class BrandServiceImpl implements BrandService{
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Override
	public List<Brand> findAll() {
		return (List<Brand>) brandRepository.findAll();
	}

	@Override
	public Brand save(Brand brand) {
		return brandRepository.save(brand);
	}

	@Override
	public Brand findById(Integer id) throws BrandNotFoundException {
		try {
			return brandRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new BrandNotFoundException("Count not found any brand");
		}
	}

	@Override
	public void delete(Integer id) throws BrandNotFoundException {
		Long countId = brandRepository.countById(id);
		
		if(countId == 0 || countId == null ) {
			throw new BrandNotFoundException("Count not found any brand");
		}
		brandRepository.deleteById(id);
	}

	@Override
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Brand brandByName = brandRepository.findByName(name);
		
		if(isCreatingNew) {
			if(brandByName != null) return "Duplicate";
		} else {
			if(brandByName != null && brandByName.getId() != null) {
				return "Duplicate";
			}
		}
		return "OK";
	}
	public static final int BRAND_PER_PAGE = 10;

	@Override
	public Page<Brand> lítByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, BRAND_PER_PAGE, sort);
		
		if(keyword != null) {
			return brandRepository.findAll(keyword, pageable);
		}
		return brandRepository.findAll(pageable);
	}
	
}
