package dev.eshan.productservice.service;

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
                genericProductDto.setCategory(product.getCategory());
                genericProductDto.setImage(product.getImage());
                genericProductDto.setPrice(product.getPrice());
                genericProductDtos.add(genericProductDto);
            }
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto getProductById(String id) throws NotFoundException {
        return doSomething(id);
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
            Category categoryDto = new Category();
            categoryDto.setId(category.get().getId());
            categoryDto.setName(category.get().getName());
            // don't set products here to avoid infinite recursion // Since the List of products are fetched lazily by default in JPA repositories
            genericProductDto.setCategory(categoryDto);
        }
        return genericProductDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        Product product = new Product();
        product.setTitle(genericProductDto.getTitle());
        product.setDescription(genericProductDto.getDescription());
        product.setCategory(genericProductDto.getCategory());
        product.setImage(genericProductDto.getImage());
        product.setPrice(genericProductDto.getPrice());
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
        product.setCategory(genericProductDto.getCategory());
        product.setImage(genericProductDto.getImage());
        product.setPrice(genericProductDto.getPrice());
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
            genericProductDto.setCategory(product.get().getCategory());
            genericProductDto.setImage(product.get().getImage());
            genericProductDto.setPrice(product.get().getPrice());
            return genericProductDto;
        } else {
            throw new NotFoundException("Product not found by id: " + id);
        }
    }
}