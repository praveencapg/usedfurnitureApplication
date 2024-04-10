package com.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.category.dto.CategoryDTO;
import com.category.entity.Category;
import com.category.exception.CategoryNotFoundException;
import com.category.serviceimpl.CategoryServiceImpl;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*") // Front-end Connection
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    //  to add a new category
    @PostMapping("/addCategory")
    public CategoryDTO addNewProduct(@RequestBody Category category) {
        // Calls the service layer to add a new category and returns the DTO representation of the added category
        return categoryServiceImpl.addCategory(category);
    }

    //  to update an existing category
    @PutMapping("/updateCategory/{category_id}")
    public String updateCategory(@PathVariable(value = "category_id") int id, @RequestBody CategoryDTO categoryDTO) {
        // Calls the service layer to update the category with the given ID using the provided DTO
        categoryServiceImpl.updateCategory(id, categoryDTO);
        return "Updated Successfully";
    }

    //  to delete a category by ID
    @DeleteMapping("/deleteCategory/{category_id}")
    public String deleteCategory(@PathVariable(value = "category_id") int id) throws CategoryNotFoundException {
        // Calls the service layer to remove the category with the given ID
        categoryServiceImpl.removeCategory(id);
        return " Deleted Successfully";
    }

    //  to get a category by name
    @GetMapping("/categoryUsingName/{categoryName}")
    public CategoryDTO getCategoryByName(@PathVariable(value = "categoryName") String name) throws CategoryNotFoundException {
        // Calls the service layer to search for a category by name
        return categoryServiceImpl.seachCategoryByName(name);
    }

    //  to get a category by ID
    @GetMapping("/categoryUsingId/{category_id}")
    public CategoryDTO getCategoryById(@PathVariable(value = "category_id") int id) throws CategoryNotFoundException {
        // Calls the service layer to search for a category by ID
        return categoryServiceImpl.searchCategoryById(id);
    }

    //  to get all categories
    @GetMapping("/findAll")
    public List<CategoryDTO> getAllCategory() throws CategoryNotFoundException {
        // Calls the service layer to retrieve all categories
        return categoryServiceImpl.findAllCategories();
    }
}
