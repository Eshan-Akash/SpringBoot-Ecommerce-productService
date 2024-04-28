package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.model.Product;
import dev.eshan.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductServiceImpl")
public class SelfProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    SelfProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<GenericProductDto> getProducts() {
        return null;
    }

    @Override
    public GenericProductDto getProductById(int id) {
        return null;
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
    public GenericProductDto updateProduct(int id, GenericProductDto genericProductDto) {
        return null;
    }

    @Override
    public GenericProductDto deleteProduct(Long id) {
        return null;
    }
}