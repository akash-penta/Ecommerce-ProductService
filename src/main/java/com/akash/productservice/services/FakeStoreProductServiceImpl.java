package com.akash.productservice.services;

import com.akash.productservice.thirdpartyclients.productservice.fakestore.FakeStoreProductDto;
import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundException;
import com.akash.productservice.thirdpartyclients.productservice.fakestore.FakeStoreProductServiceClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductServiceImpl")
public class FakeStoreProductServiceImpl implements  ProductService{

    FakeStoreProductServiceClient fakeStoreProductServiceClient;

    public FakeStoreProductServiceImpl(FakeStoreProductServiceClient fakeStoreProductServiceClient) {
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductServiceClient.createProduct(genericProductDto));
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductServiceClient.getAllProducts()) {
            genericProductDtos.add(convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto));
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto getProductById(String id) throws NotFoundException {
        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductServiceClient.getProductById(Long.parseLong(id)));
    }

    @Override
    public GenericProductDto deleteProductById(String id) throws NotFoundException {
        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductServiceClient.deleteProductById(Long.parseLong(id)));
    }

    @Override
    public GenericProductDto updateProductById(String id, GenericProductDto genericProductDto) throws NotFoundException {
        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductServiceClient.updateProductById(Long.parseLong(id), genericProductDto));
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
