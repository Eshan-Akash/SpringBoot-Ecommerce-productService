package dev.eshan.productservice.controller;

import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.dtos.GetProductTitlesRequestDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.security.JwtObject;
import dev.eshan.productservice.security.TokenValidator;
import dev.eshan.productservice.service.CategoryService;
import dev.eshan.productservice.service.ProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private TokenValidator tokenValidator;

    public ProductController(@Qualifier("selfProductServiceImpl") ProductService productService,
                             CategoryService categoryService, TokenValidator tokenValidator) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.tokenValidator = tokenValidator;
    }

    @GetMapping("/")
    public List<GenericProductDto> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@Nullable @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable("id") String id) throws NotFoundException {
        if (authToken == null) {
            throw new NotFoundException("Authorization token is required");
        }
        Optional<JwtObject> authTokenObjOptional = Optional.empty();
        JwtObject authTokenObj = new JwtObject();

        if (authToken != null) {
            authTokenObjOptional = tokenValidator.validateToken(authToken, authTokenObj.getUserId());
            if (authTokenObjOptional.isEmpty()) {
                // Ignore
//                throw new NotFoundException("Invalid token");
            }
            authTokenObj = authTokenObjOptional.get();
        }

        GenericProductDto productDto = productService.getProductById(id, authTokenObj.getUserId());
        if (productDto == null) {
            throw new NotFoundException("Product not found");
        }

        // Get product by id
        return productDto;
    }

    @GetMapping("/category/{id}")
    public List<GenericProductDto> getProductsInCategory(@PathVariable("id") String id) throws NotFoundException {
        return productService.getProductsInCategory(id);
    }

    @GetMapping("/titles/")
    public List<String> getProductTitles(@RequestBody GetProductTitlesRequestDto requestDto) {
        List<String> ids = requestDto.getIds();
        return categoryService.getProductTitles(ids);
    }

    @PostMapping("/")
    public GenericProductDto createProduct(@RequestBody GenericProductDto product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public GenericProductDto updateProductById(@PathVariable("id") String id, @RequestBody GenericProductDto product) throws NotFoundException {
        // Update a product
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(productService.deleteProduct(id),
                HttpStatus.OK);
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ExceptionDto> handleNotFoundException(
//            NotFoundException notFoundException
//    ) {
//        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()),
//                HttpStatus.NOT_FOUND);
//    }
}
