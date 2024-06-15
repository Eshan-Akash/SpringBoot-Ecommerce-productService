package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.model.Product;
import dev.eshan.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<GenericProductDto> searchProducts(String query, Pageable pageable) {
        Page<Product> productsPage = productRepository.findAllByTitleContaining(query, pageable);
        List<Product> products = productsPage.getContent();

        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for (Product product : products) {
            GenericProductDto genericProductDto = new GenericProductDto();
            genericProductDto.setId(product.getId());
            genericProductDto.setTitle(product.getTitle());
            genericProductDto.setDescription(product.getDescription());
            genericProductDto.setImage(product.getImage());
            genericProductDto.setPrice(product.getPrice());
            genericProductDtos.add(genericProductDto);
        }

        Page<GenericProductDto> genericProductDtoPage = new PageImpl<>(genericProductDtos, productsPage.getPageable(), productsPage.getTotalElements());

        return genericProductDtoPage;
    }
}
