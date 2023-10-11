package com.akash.productservice.controllers;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundException;
import com.akash.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Qualifier("selfProductServiceImpl")
    @MockBean
    private ProductService productService;

    @Test
    void getProductById() throws NotFoundException {

        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setTitle("MI 11x");
        genericProductDto.setCategory("Mobile");
        genericProductDto.setPrice(21999);

        when(
                productService.getProductById("123")
        ).thenReturn(genericProductDto);

        assertEquals(genericProductDto, productController.getProductById("123"));
    }
}