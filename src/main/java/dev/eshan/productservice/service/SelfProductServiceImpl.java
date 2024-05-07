package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericCategoryDto;
import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.model.Category;
import dev.eshan.productservice.model.Product;
import dev.eshan.productservice.repositories.CategoryRepository;
import dev.eshan.productservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfProductServiceImpl")
public class SelfProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    SelfProductServiceImpl(ProductRepository productRepository,
                           CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<GenericProductDto> getProducts() {
        Optional<List<Product>> products = Optional.of(productRepository.findAll());
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        if (products.isPresent()) {
            for (Product product : products.get()) {
                GenericProductDto genericProductDto = new GenericProductDto();
                genericProductDto.setId(product.getId());
                genericProductDto.setTitle(product.getTitle());
                genericProductDto.setDescription(product.getDescription());
                genericProductDto.setImage(product.getImage());
                genericProductDto.setPrice(product.getPrice());
                genericProductDtos.add(genericProductDto);
                genericProductDto.setCategoryDto(GenericCategoryDto.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build());
            }
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto getProductById(String id) throws NotFoundException {
        return doSomething(id);
    }

    @Override
    public List<GenericProductDto> getProductsInCategory(String categoryId) throws NotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("Category not found by id: " + categoryId);
        }
        Category category = categoryOptional.get();
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        category.getProducts().forEach(product -> {
            GenericProductDto genericProductDto = new GenericProductDto();
            genericProductDto.setId(product.getId());
            genericProductDto.setTitle(product.getTitle());
            genericProductDto.setDescription(product.getDescription());
            genericProductDto.setImage(product.getImage());
            genericProductDto.setPrice(product.getPrice());
            genericProductDto.setCategoryDto(GenericCategoryDto.builder()
                    .id(product.getCategory().getId())
                    .name(product.getCategory().getName())
                    .build());
            genericProductDtos.add(genericProductDto);
        });
        return genericProductDtos;
    }

    @Transactional
    public GenericProductDto doSomething(String id) throws NotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.orElseThrow(() -> new NotFoundException("Product not found by id: " + id));

        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(product.getId());
        genericProductDto.setTitle(product.getTitle());
        genericProductDto.setDescription(product.getDescription());
        genericProductDto.setImage(product.getImage());
        genericProductDto.setPrice(product.getPrice());

        Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
        if (category.isPresent()) {
            // don't set products here to avoid infinite recursion // Since the List of products are fetched lazily by default in JPA repositories
            genericProductDto.setCategoryDto(GenericCategoryDto.builder()
                    .id(category.get().getId())
                    .name(category.get().getName())
                    .build());
        }
        return genericProductDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        Product product = new Product();
        product.setTitle(genericProductDto.getTitle());
        product.setDescription(genericProductDto.getDescription());
        product.setImage(genericProductDto.getImage());
        product.setPrice(genericProductDto.getPrice());
        Category category = new Category();
        category.setName(genericProductDto.getCategoryDto().getName());
        product.setCategory(category);
        Product savedproduct = productRepository.save(product);
        genericProductDto.setId(savedproduct.getId());
        return genericProductDto;
    }

    @Override
    public GenericProductDto updateProduct(String id, GenericProductDto genericProductDto) throws NotFoundException {
        Optional<Product> exitingProduct = productRepository.findById(id);
        if (exitingProduct.isEmpty()) {
            throw new NotFoundException("Product not found by id: " + id);
        }
        Product product = new Product();
        product.setTitle(genericProductDto.getTitle());
        product.setDescription(genericProductDto.getDescription());
        product.setImage(genericProductDto.getImage());
        product.setPrice(genericProductDto.getPrice());
        Category category = new Category();
        category.setName(genericProductDto.getCategoryDto().getName());
        product.setCategory(category);
        Product savedproduct = productRepository.save(product);
        genericProductDto.setId(savedproduct.getId());
        return genericProductDto;
    }

    @Override
    public GenericProductDto deleteProduct(String id) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            GenericProductDto genericProductDto = new GenericProductDto();
            genericProductDto.setId(product.get().getId());
            genericProductDto.setTitle(product.get().getTitle());
            genericProductDto.setDescription(product.get().getDescription());
            genericProductDto.setImage(product.get().getImage());
            genericProductDto.setPrice(product.get().getPrice());
            genericProductDto.setCategoryDto(GenericCategoryDto.builder()
                    .id(product.get().getCategory().getId())
                    .name(product.get().getCategory().getName())
                    .build());
            return genericProductDto;
        } else {
            throw new NotFoundException("Product not found by id: " + id);
        }
    }
}