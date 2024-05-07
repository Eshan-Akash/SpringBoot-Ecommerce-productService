package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericCategoryDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.model.Category;

import java.util.List;

public interface CategoryService {
    GenericCategoryDto getCategory(String id) throws NotFoundException;

    List<GenericCategoryDto> getAllCategories();
    List<String> getProductTitles(List<String> categoryIDs);
}
