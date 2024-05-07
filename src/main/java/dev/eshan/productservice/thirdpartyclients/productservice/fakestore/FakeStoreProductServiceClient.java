package dev.eshan.productservice.thirdpartyclients.productservice.fakestore;

import dev.eshan.productservice.dtos.FakeStoreProductDto;
import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.exceptions.NotFoundException;
import dev.eshan.productservice.thirdpartyclients.productservice.ThirdPartyProductServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FakeStoreProductServiceClient implements ThirdPartyProductServiceClient {
    private RestTemplateBuilder restTemplateBuilder;
    private static String FAKE_STORE_API_URL;
    private static String FAKE_STORE_PRODUCT_API_PATH;
    private String SPECIFIC_PRODUCT_REQUEST_URL;
    private String PRODUCT_REQUEST_BASE_URL;
    FakeStoreProductServiceClient(RestTemplateBuilder restTemplateBuilder,
                                  @Value("${fakestore.api.url}") String FAKE_STORE_API_URL,
                                  @Value("${fakestore.api.path.products}") String FAKE_STORE_PRODUCT_API_PATH){
        this.restTemplateBuilder = restTemplateBuilder;
        this.SPECIFIC_PRODUCT_REQUEST_URL = FAKE_STORE_API_URL + FAKE_STORE_PRODUCT_API_PATH + "/{id}";
        this.PRODUCT_REQUEST_BASE_URL = FAKE_STORE_API_URL + FAKE_STORE_PRODUCT_API_PATH;
    }

    @Override
    public List<FakeStoreProductDto> getProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        return Arrays.stream(response.getBody()).toList();
    }

    @Override
    public FakeStoreProductDto getProductById(int id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        SPECIFIC_PRODUCT_REQUEST_URL = FAKE_STORE_API_URL + FAKE_STORE_PRODUCT_API_PATH + "/{id}";
//        System.out.println(SPECIFIC_PRODUCT_REQUEST_URL);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(SPECIFIC_PRODUCT_REQUEST_URL, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProduceDto = response.getBody();
        if (fakeStoreProduceDto == null) {
            throw new NotFoundException("Product with id: " + id + " not found");
        }
        return fakeStoreProduceDto;
    }

    @Override
    public FakeStoreProductDto createProduct(GenericProductDto genericProductDto) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(genericProductDto.getTitle());
        fakeStoreProductDto.setPrice(genericProductDto.getPrice().getPrice());
        fakeStoreProductDto.setDescription(genericProductDto.getDescription());
        fakeStoreProductDto.setCategory(genericProductDto.getCategoryDto().getName());
        fakeStoreProductDto.setImage(genericProductDto.getImage());

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(
                PRODUCT_REQUEST_BASE_URL, fakeStoreProductDto, FakeStoreProductDto.class);
        return response.getBody();
    }

    @Override
    public FakeStoreProductDto updateProduct(int id, GenericProductDto genericProductDto) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(genericProductDto, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                restTemplate.execute(SPECIFIC_PRODUCT_REQUEST_URL, HttpMethod.PUT, requestCallback, responseExtractor, id);
        if (fakeStoreProductDtoResponseEntity == null) {
            throw new NotFoundException("Product with id: " + id + " not found");
        }
        return fakeStoreProductDtoResponseEntity.getBody();
    }

    @Override
    public FakeStoreProductDto deleteProduct(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                restTemplate.execute(SPECIFIC_PRODUCT_REQUEST_URL, HttpMethod.GET, requestCallback, responseExtractor, id);
        if (fakeStoreProductDtoResponseEntity == null) {
            throw new NotFoundException("Product with id: " + id + " not found");
        }
        return fakeStoreProductDtoResponseEntity.getBody();
    }
}
