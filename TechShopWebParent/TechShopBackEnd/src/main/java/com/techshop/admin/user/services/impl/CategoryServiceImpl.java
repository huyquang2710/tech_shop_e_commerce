package com.techshop.admin.user.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techshop.admin.exception.CategoryNotFoundException;
import com.techshop.admin.user.repositories.CategoryRepository;
import com.techshop.admin.user.services.CategoryService;
import com.techshop.common.entity.Category;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	private static final int ROOT_CATEGORIES_PER_PAGE = 4;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> listAll(String sortDir) {
		Sort sort = Sort.by("name");
		if (sortDir == null || sortDir.isEmpty()) {
			sort = sort.ascending();
		} else if (sortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (sortDir.equals("desc")) {
			sort = sort.descending();
		}

		List<Category> rootCategories = categoryRepository.findRootCategories(Sort.by("name").ascending());

		return listHierarchicalCategories(rootCategories);
	}
	
	@Override
	public List<Category> listByPage(int pageNum, String sortDir) {
		Sort sort = Sort.by("name");
		if (sortDir == null || sortDir.isEmpty()) {
			sort = sort.ascending();
		} else if (sortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (sortDir.equals("desc")) {
			sort = sort.descending();
		}
		
		Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

		List<Category> rootCategories = categoryRepository.findRootCategories(pageable);

		return listHierarchicalCategories(rootCategories);
	}

	private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
		List<Category> hierarchicalCategories = new ArrayList<>();

		for (Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyAll(rootCategory));

			Set<Category> children = sortubCategories(rootCategory.getChildren());

			for (Category subCategory : children) {
				String name = "--" + subCategory.getName();
				hierarchicalCategories.add(Category.copyAll(subCategory, name));

				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
			}
		}
		return hierarchicalCategories;
	}

	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel) {
		Set<Category> children = parent.getChildren();
		int newSubLevel = subLevel + 1;

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.copyAll(subCategory, name));

			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
		}
	}

	// show category theo cap bac
	// code ao? qua.
	@Override
	public List<Category> listCategoriesUsedInForm() {

		List<Category> categoriesUserdInForm = new ArrayList<>();

		Iterable<Category> categoriesInDB = categoryRepository.findRootCategories(Sort.by("name").ascending());

		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				categoriesUserdInForm.add(Category.copyIdAndName(category));

				Set<Category> children = sortubCategories(category.getChildren());

				for (Category subCategory : children) {
					String name = "--" + subCategory.getName();
					categoriesUserdInForm.add(Category.copyIdAndName(subCategory.getId(), name));

					listSubCategoriesUsedInForm(categoriesUserdInForm, subCategory, 1);
				}
			}
		}

		return categoriesUserdInForm;
	}

	private void listSubCategoriesUsedInForm(List<Category> categoriesUserdInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = sortubCategories(parent.getChildren());

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			categoriesUserdInForm.add(Category.copyIdAndName(subCategory.getId(), name));

			listSubCategoriesUsedInForm(categoriesUserdInForm, subCategory, newSubLevel);
		}
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category findById(Integer id) throws CategoryNotFoundException {
		Category category = new Category();

		try {
			return categoryRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CategoryNotFoundException("Could not find any category with Category: " + category.getName());
		}

	}

	// check unique alias
	@Override
	public String checkUnique(Integer id, String name, String alias) {
		boolean isCreatingNew = (id == null || id == 0);

		Category categoryByName = categoryRepository.findByName(name);

		if (isCreatingNew) {
			if (categoryByName != null) {
				return "Duplicate Name";
			} else {
				Category categoryByAlias = categoryRepository.findByName(alias);
				if (categoryByAlias != null) {
					return "Duplicate Alias";
				}
			}
		} else {

		}
		return "OK";
	}

	private SortedSet<Category> sortubCategories(Set<Category> children) {
		return sortubCategories(children, "asc");
	}

	private SortedSet<Category> sortubCategories(Set<Category> children, String sortDir) {
		SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {
			@Override
			public int compare(Category o1, Category o2) {
				if (sortDir.equals("asc")) {
					return o1.getName().compareTo(o2.getName());
				} else {
					return o2.getName().compareTo(o1.getName());
				}
			}
		});
		sortedChildren.addAll(children);
		return sortedChildren;
	}

	@Override
	public void udpateEnaledStatus(Integer id, boolean enabled) {
		categoryRepository.updatedEnabledStatus(id, enabled);
	}

	@Override
	public void delete(Integer id) throws CategoryNotFoundException {
		Long countById = categoryRepository.countById(id);
		if (countById == null || countById == null) {
			throw new CategoryNotFoundException("Count not find any category with ID " + id);
		}
		categoryRepository.deleteById(id);
	}

}
