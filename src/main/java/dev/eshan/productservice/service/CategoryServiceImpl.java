package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericCategoryDto;
import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.model.Category;
import dev.eshan.productservice.model.Product;
import dev.eshan.productservice.repositories.CategoryRepository;
import dev.eshan.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public GenericCategoryDto getCategory(String id) throws NotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.orElseThrow(() -> new NotFoundException("Category not found by id: " + id));
        List<GenericProductDto> products = new ArrayList<>();
        for (Product product : category.getProducts()) {
            GenericProductDto p = new GenericProductDto();
            p.setTitle(product.getTitle());
            p.setDescription(product.getDescription());
            products.add(p);
        }
        return GenericCategoryDto.builder()
                .name(category.getName())
                .products(products)
                .build();
    }

    @Override
    public List<GenericCategoryDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category ->
                GenericCategoryDto.builder()
                        .name(category.getName())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<String> getProductTitles(List<String> categoryIDs) {
//
//        List<Category> categories = categoryRepository.findAllById(uuids);
//
//
//        List<String> titles = new ArrayList<>();
//
//        categories.forEach(
//                category -> {
//                    category.getProducts().forEach(
//                            product -> {
//                                titles.add(product.getTitle());
//                            }
//                    );
//                }
//        );
//
//
//        return titles;

        List<Category> categories = categoryRepository.findAllById(categoryIDs);

        List<Product> products = productRepository.findAllByCategoryIn(categories);

        List<String> titles = new ArrayList<>();

        for (Product p : products) {
            titles.add(p.getTitle());
        }

        return titles;
    }
}
