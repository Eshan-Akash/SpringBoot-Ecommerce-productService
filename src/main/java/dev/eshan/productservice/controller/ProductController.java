package dev.eshan.productservice.controller;

import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.dtos.GetProductTitlesRequestDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.service.CategoryService;
import dev.eshan.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(@Qualifier("selfProductServiceImpl") ProductService productService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<GenericProductDto> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@PathVariable("id") String id) throws NotFoundException {
        // Get product by id
        return productService.getProductById(id);
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
