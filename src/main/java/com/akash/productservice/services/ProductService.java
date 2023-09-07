package com.akash.productservice.services;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundExcpetion;

import java.util.List;

public interface ProductService {

    GenericProductDto createProduct(GenericProductDto genericProductDto);
    List<GenericProductDto> getAllProducts();
    GenericProductDto getProductById(Long id) throws NotFoundExcpetion;
    GenericProductDto deleteProductById(Long id) throws NotFoundExcpetion;
    GenericProductDto updateProductById(Long id, GenericProductDto genericProductDto) throws NotFoundExcpetion;
}
