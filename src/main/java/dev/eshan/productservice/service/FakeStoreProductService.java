package dev.eshan.productservice.service;

import dev.eshan.productservice.dtos.FakeStoreProductDto;
import dev.eshan.productservice.dtos.GenericCategoryDto;
import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.model.Price;
import dev.eshan.productservice.thirdpartyclients.productservice.fakestore.FakeStoreProductServiceClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Primary
@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private FakeStoreProductServiceClient fakeStoreProductServiceClient;

    private RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(FakeStoreProductServiceClient fakeStoreProductServiceClient, RedisTemplate<String, Object> redisTemplate) {
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<GenericProductDto> getProducts() {
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        fakeStoreProductServiceClient.getProducts().forEach(fakeStoreProductDto -> {
            genericProductDtos.add(convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto));
        });
        return genericProductDtos;
    }

    @Override
    public GenericProductDto getProductById(String id, Long userIdTryingToAccess) throws NotFoundException {
        return getProductById(id);
    }

    private GenericProductDto getProductById(String id) throws NotFoundException {
        GenericProductDto genericProductDto = (GenericProductDto) redisTemplate.opsForHash().get("PRODUCT", id);
        if (genericProductDto != null) {
            return genericProductDto;
        }
        GenericProductDto genericProductDto1 = convertFakeStoreProductDtoToGenericProductDto(
                fakeStoreProductServiceClient.getProductById(Integer.parseInt(id)));
        redisTemplate.opsForHash().put("PRODUCT", id, genericProductDto1);
        return genericProductDto1;
    }

    @Override
    public List<GenericProductDto> getProductsInCategory(String categoryId) {
        return null;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductServiceClient.createProduct(genericProductDto));
    }

    @Override
    public GenericProductDto updateProduct(String id, GenericProductDto genericProductDto) throws NotFoundException {
        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductServiceClient.updateProduct(Integer.parseInt(id), genericProductDto));
    }

    @Override
    public GenericProductDto deleteProduct(String id) throws NotFoundException {
        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductServiceClient.deleteProduct(Long.valueOf(id)));
    }

    private GenericProductDto convertFakeStoreProductDtoToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto genericProductDto = new GenericProductDto();
        if (fakeStoreProductDto != null) {
            genericProductDto.setId(String.valueOf(fakeStoreProductDto.getId()));
            genericProductDto.setTitle(fakeStoreProductDto.getTitle());
            Price price = new Price();
            price.setPrice(fakeStoreProductDto.getPrice());
            genericProductDto.setPrice(price);
            genericProductDto.setDescription(fakeStoreProductDto.getDescription());
            genericProductDto.setCategory(GenericCategoryDto.builder().name(fakeStoreProductDto.getCategory()).build());
            genericProductDto.setImage(fakeStoreProductDto.getImage());
        }
        return genericProductDto;
    }
}
