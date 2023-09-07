package com.akash.productservice.services;

import com.akash.productservice.dtos.GenericProductDto;

import java.util.List;

public interface ProductService {

    GenericProductDto createProduct(GenericProductDto genericProductDto);
    List<GenericProductDto> getAllProducts();
    GenericProductDto getProductById(Long id);
    GenericProductDto deleteProductById(Long id);
    GenericProductDto updateProductById(Long id, GenericProductDto genericProductDto);
}
