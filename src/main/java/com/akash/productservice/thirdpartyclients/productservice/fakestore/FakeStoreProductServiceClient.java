package com.akash.productservice.thirdpartyclients.productservice.fakestore;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductServiceClient {
    private RestTemplateBuilder restTemplateBuilder;
    private String productUrl;
    private String productByIdUrl;

    public FakeStoreProductServiceClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${fakestore.api.url}") String fakeStoreApiUrl,
            @Value("${fakestore.api.path.product}") String fakeStoreProductsApiPath
    ) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.productUrl = fakeStoreApiUrl + fakeStoreProductsApiPath;
        this.productByIdUrl = fakeStoreApiUrl + fakeStoreProductsApiPath + "/{id}";
    }

    public FakeStoreProductDto createProduct(GenericProductDto genericProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(productUrl, genericProductDto, FakeStoreProductDto.class);

        return response.getBody();
    }

    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(productUrl, FakeStoreProductDto[].class);

        return List.of(response.getBody());
    }

    public FakeStoreProductDto getProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(productByIdUrl, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        if(fakeStoreProductDto == null) {
            throw new NotFoundException("Product with Id:" + id + " is not found");
        }

        return fakeStoreProductDto;
    }

    public FakeStoreProductDto deleteProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        restTemplate.delete(productByIdUrl, id);
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(productByIdUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);

        if(!response.hasBody()) {
            throw new NotFoundException("Product with Id:" + id + " is not found");
        }

        return response.getBody();
    }

    public FakeStoreProductDto updateProductById(Long id, GenericProductDto genericProductDto) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(genericProductDto, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(productByIdUrl, HttpMethod.PUT, requestCallback, responseExtractor, id);

        if(!response.hasBody()) {
            throw new NotFoundException("Product with Id:" + id + " is not found");
        }

        return response.getBody();
    }
}
