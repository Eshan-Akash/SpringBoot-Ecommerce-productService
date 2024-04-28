package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;

import java.util.List;

public interface ProductService {
    List<GenericProductDto> getProducts();
    GenericProductDto getProductById(int id) throws NotFoundException;
    GenericProductDto createProduct(GenericProductDto genericProductDto);
    GenericProductDto updateProduct(int id, GenericProductDto genericProductDto) throws NotFoundException;
    GenericProductDto deleteProduct(Long id) throws NotFoundException;
}
