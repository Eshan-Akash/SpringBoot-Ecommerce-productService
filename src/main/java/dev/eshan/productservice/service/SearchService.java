package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.dtos.SortParameter;
import dev.eshan.productservice.model.Product;
import dev.eshan.productservice.repositories.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<GenericProductDto> searchProducts(String query, int pageNumber, int sizeOfEachPage, List<SortParameter> sortByParameters) {
        Sort sort;
        if (sortByParameters.get(0).getSortType().equalsIgnoreCase("DESC"))
            sort = Sort.by(Sort.Direction.DESC, sortByParameters.toArray(new String[0]));
        else
            sort =  Sort.by(Sort.Direction.ASC, sortByParameters.toArray(new String[0]));

        for (SortParameter sortByParameter : sortByParameters) {
            if (sortByParameter.getSortType().equalsIgnoreCase("DESC"))
                sort = sort.and(Sort.by(sortByParameter.getParameterName()).descending());
            else
                sort = sort.and(Sort.by(sortByParameter.getParameterName()));
        }

        Pageable pageable = PageRequest.of(pageNumber, sizeOfEachPage, sort);
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
