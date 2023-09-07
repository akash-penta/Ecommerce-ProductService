package com.akash.productservice.services;

import com.akash.productservice.dtos.FakeStoreProductDto;
import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundExcpetion;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductServiceImpl")
public class FakeStoreProductServiceImpl implements  ProductService{

    private RestTemplateBuilder restTemplateBuilder;
    private String productUrl = "https://fakestoreapi.com/products";
    private String productByIdUrl = "https://fakestoreapi.com/products/{id}";

    public FakeStoreProductServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(productUrl, genericProductDto, FakeStoreProductDto.class);

        return convertFakeStoreProductDtoToGenericProductDto(response.getBody());
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(productUrl, FakeStoreProductDto[].class);

        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: response.getBody()) {
            genericProductDtos.add(convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto));
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundExcpetion {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(productByIdUrl, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        if(fakeStoreProductDto == null) {
            throw new NotFoundExcpetion("Product with Id:" + id + " is not found");
        }

        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto deleteProductById(Long id) throws NotFoundExcpetion {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        restTemplate.delete(productByIdUrl, id);
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(productByIdUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);

        if(!response.hasBody()) {
            throw new NotFoundExcpetion("Product with Id:" + id + " is not found");
        }

        return convertFakeStoreProductDtoToGenericProductDto(response.getBody());
    }

    @Override
    public GenericProductDto updateProductById(Long id, GenericProductDto genericProductDto) throws NotFoundExcpetion {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(genericProductDto, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(productByIdUrl, HttpMethod.PUT, requestCallback, responseExtractor, id);

        if(!response.hasBody()) {
            throw new NotFoundExcpetion("Product with Id:" + id + " is not found");
        }

        return convertFakeStoreProductDtoToGenericProductDto(response.getBody());
    }

    private GenericProductDto convertFakeStoreProductDtoToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(fakeStoreProductDto.getId());
        genericProductDto.setTitle(fakeStoreProductDto.getTitle());
        genericProductDto.setDescription(fakeStoreProductDto.getDescription());
        genericProductDto.setPrice(fakeStoreProductDto.getPrice());
        genericProductDto.setCategory(fakeStoreProductDto.getCategory());
        genericProductDto.setImage(fakeStoreProductDto.getImage());
        return genericProductDto;
    }
}
