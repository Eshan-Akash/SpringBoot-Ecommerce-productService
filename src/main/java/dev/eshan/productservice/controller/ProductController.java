package dev.eshan.productservice.controller;

import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;
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
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<GenericProductDto> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@PathVariable("id") int id) throws NotFoundException {
        // Get product by id
        return productService.getProductById(id);
    }

    @PostMapping("/")
    public GenericProductDto createProduct(@RequestBody GenericProductDto product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public GenericProductDto updateProductById(@PathVariable("id") int id, @RequestBody GenericProductDto product) throws NotFoundException {
        // Update a product
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable Long id) throws NotFoundException {
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
