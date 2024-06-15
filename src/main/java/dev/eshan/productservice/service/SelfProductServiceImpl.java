package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericCategoryDto;
import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.events.EventName;
import dev.eshan.productservice.events.ProductEvent;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.model.Category;
import dev.eshan.productservice.model.Product;
import dev.eshan.productservice.repositories.CategoryRepository;
import dev.eshan.productservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfProductServiceImpl")
public class SelfProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    SelfProductServiceImpl(ProductRepository productRepository,
                           CategoryRepository categoryRepository,
                           ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<GenericProductDto> getProducts() {
//        Optional<List<Product>> products = Optional.of(productRepository.getAll(
//                Pageable.ofSize(10)
//        ));
//
//        PageRequest pageRequest = PageRequest.of(2, 10);
//        Optional<List<Product>> products2 = Optional.of(productRepository.getAll(pageRequest));
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
                genericProductDto.setCategory(GenericCategoryDto.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build());
            }
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto getProductById(String id, Long userIdTryingToAccess) throws NotFoundException {
        return getProductFromStoreById(id);
    }

    @Transactional
    public GenericProductDto getProductFromStoreById(String id) throws NotFoundException {
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
            genericProductDto.setCategory(GenericCategoryDto.builder()
                    .id(category.get().getId())
                    .name(category.get().getName())
                    .build());
        }
        return genericProductDto;
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
            genericProductDto.setCategory(GenericCategoryDto.builder()
                    .id(product.getCategory().getId())
                    .name(product.getCategory().getName())
                    .build());
            genericProductDtos.add(genericProductDto);
        });
        return genericProductDtos;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        Product product = new Product();
        product.setTitle(genericProductDto.getTitle());
        product.setDescription(genericProductDto.getDescription());
        product.setImage(genericProductDto.getImage());
        product.setPrice(genericProductDto.getPrice());
        Category category = new Category();
        category.setName(genericProductDto.getCategory() != null ? genericProductDto.getCategory().getName() : null);
        product.setCategory(category);
        Product savedproduct = productRepository.save(product);
        genericProductDto.setId(savedproduct.getId());
        // publish event
        eventPublisher.publishEvent(getProductEvent(genericProductDto));
        return genericProductDto;
    }

    private ProductEvent getProductEvent(GenericProductDto genericProductDto) {
        ProductEvent productEvent = new ProductEvent();
        productEvent.setEventName(EventName.PRODUCT_CREATED);
        productEvent.setProduct(genericProductDto);
        return productEvent;
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
        category.setName(genericProductDto.getCategory().getName());
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
            genericProductDto.setCategory(GenericCategoryDto.builder()
                    .id(product.get().getCategory().getId())
                    .name(product.get().getCategory().getName())
                    .build());
            return genericProductDto;
        } else {
            throw new NotFoundException("Product not found by id: " + id);
        }
    }
}