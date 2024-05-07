package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericCategoryDto;
import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.model.Category;
import dev.eshan.productservice.model.Product;
import dev.eshan.productservice.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public GenericCategoryDto getCategory(String id) throws NotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.orElseThrow(() -> new NotFoundException("Category not found by id: " + id));
        GenericCategoryDto genericCategoryDto = new GenericCategoryDto();
        genericCategoryDto.setName(category.getName());
        List<GenericProductDto> products = new ArrayList<>();
        for (Product product : category.getProducts()) {
            GenericProductDto p = new GenericProductDto();
            p.setTitle(product.getTitle());
            p.setDescription(product.getDescription());
            products.add(p);
        }
        genericCategoryDto.setProducts(products);
        return genericCategoryDto;
    }
}
