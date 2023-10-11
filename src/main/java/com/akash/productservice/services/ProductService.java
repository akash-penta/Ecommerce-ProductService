package com.akash.productservice.services;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundException;
import com.akash.productservice.exceptions.UnableToCreateProductException;

import java.util.List;

public interface ProductService {

    GenericProductDto createProduct(GenericProductDto genericProductDto) throws UnableToCreateProductException;
    List<GenericProductDto> getAllProducts();
    GenericProductDto getProductById(String id) throws NotFoundException;
    GenericProductDto deleteProductById(String id) throws NotFoundException;
    GenericProductDto updateProductById(String id, GenericProductDto genericProductDto) throws NotFoundException;
}
