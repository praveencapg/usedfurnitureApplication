package com.category.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.category.dto.CategoryDTO;
import com.category.entity.Category;
import com.category.exception.CategoryNotFoundException;
import com.category.repository.CategoryRepository;
import com.category.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Method to add a new category
    public CategoryDTO addCategory(Category category) {
        // Save the category using the repository
        categoryRepository.save(category);

        // Create a new CategoryDTO and set its attributes
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());

        return categoryDTO;
    }

    // Method to update an existing category
    public String updateCategory(int categoryId, CategoryDTO categoryDTO) {
        try {
            // Find the category by ID
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

            // Update the category name
            category.setCategoryName(categoryDTO.getCategoryName());

            // Save the updated category
            categoryRepository.save(category);
        } catch (CategoryNotFoundException e) {
            // Handle exception if category not found
            System.out.println(e);
            return "Category not updated";
        }
        return "Updated Successfully";
    }

    // Method to remove a category by ID
    public String removeCategory(int categoryId) throws CategoryNotFoundException {
        // Find the category by ID
        Category category = categoryRepository.findById(categoryId).orElse(null);

        // Throw exception if category not found
        if (category == null) {
            throw new CategoryNotFoundException("Category does not exist with the given Id");
        }

        // Delete the category
        categoryRepository.delete(category);
        return "Deleted Successfully";
    }

    // Method to search for a category by name
    public CategoryDTO seachCategoryByName(String name) throws CategoryNotFoundException {
        // Find the category by name
        Category category = categoryRepository.findByCategoryName(name);

        // Throw exception if category not found
        if (category == null) {
            throw new CategoryNotFoundException("Category Not Found by Name");
        }

        // Create a new CategoryDTO and set its attributes
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());

        return categoryDTO;
    }

    // Method to search for a category by ID
    public CategoryDTO searchCategoryById(int id) throws CategoryNotFoundException {
        // Find the category by ID
        Category category = categoryRepository.findById(id).orElse(null);

        // Throw exception if category not found
        if (category == null) {
            throw new CategoryNotFoundException("Category Not Found by Id");
        }

        // Create a new CategoryDTO and set its attributes
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());

        return categoryDTO;
    }

    // Method to find all categories
    @Override
    public List<CategoryDTO> findAllCategories() throws CategoryNotFoundException {
        // Retrieve all categories using the repository
        List<Category> categories = categoryRepository.findAll();

        // Throw exception if no categories found
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("There are no categories");
        }

        // Create a list to hold CategoryDTO objects
        List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
        for (Category category : categories) {
            // Create a new CategoryDTO for each category entity and set its attributes
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryId(category.getCategoryId());
            categoryDTO.setCategoryName(category.getCategoryName());
            categoryDTOs.add(categoryDTO);
        }

        return categoryDTOs;
    }
}
