package dev.eshan.productservice.controller;

import dev.eshan.productservice.dtos.GenericCategoryDto;
import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.model.Category;
import dev.eshan.productservice.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/{id}")
    public GenericCategoryDto getCategory(@PathVariable String id) throws NotFoundException {
        return categoryService.getCategory(id);
    }

    @GetMapping("/")
    public List<GenericCategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
