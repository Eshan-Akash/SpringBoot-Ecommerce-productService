package dev.eshan.productservice.thirdpartyclients.productservice;

import dev.eshan.productservice.dtos.FakeStoreProductDto;
import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;

import java.util.List;

public interface ThirdPartyProductServiceClient {
    List<FakeStoreProductDto> getProducts();
    FakeStoreProductDto getProductById(int id) throws NotFoundException;
    FakeStoreProductDto createProduct(GenericProductDto genericProductDto);
    FakeStoreProductDto updateProduct(int id, GenericProductDto genericProductDto) throws NotFoundException;
    FakeStoreProductDto deleteProduct(Long id) throws NotFoundException;
}
