package com.akash.productservice.controllers;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundException;
import com.akash.productservice.exceptions.UnableToCreateProductException;
import com.akash.productservice.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebMvcTest(ProductController.class)
public class ProductControllerWebMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Qualifier("selfProductServiceImpl")
    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createProductShouldCreateNewProduct() throws Exception {
        GenericProductDto request = new GenericProductDto();
        request.setTitle("MI 11x");
        request.setCategory("Mobile");
        request.setPrice(21999);

        GenericProductDto response = new GenericProductDto();
        response.setId(UUID.randomUUID());
        response.setTitle("MI 11x");
        response.setCategory("Mobile");
        response.setPrice(21999);

        when(
                productService.createProduct(any())
        ).thenReturn(response);

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                content().string(objectMapper.writeValueAsString(response))
        );
    }

    @Test
    public void throwExceptionWhenCreatingProductWithoutTitle() throws Exception {
        GenericProductDto request = new GenericProductDto();
        request.setCategory("Mobile");
        request.setPrice(21999);

        when(
                productService.createProduct(any())
        ).thenThrow(UnableToCreateProductException.class);

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().is(400)
        );
    }

    @Test
    public void getAllProductsList() throws Exception {
        List<GenericProductDto> genericProductDtoList = new ArrayList<>();
        genericProductDtoList.add(new GenericProductDto());
        genericProductDtoList.add(new GenericProductDto());
        genericProductDtoList.add(new GenericProductDto());

        when(
                productService.getAllProducts()
        ).thenReturn(genericProductDtoList);

        mockMvc.perform(
                get("/products")
        ).andExpect(
                content().string(objectMapper.writeValueAsString(genericProductDtoList))
        );
    }

    @Test
    public void getEmptyListWhenThereIsNoProducts() throws Exception {

        when(
                productService.getAllProducts()
        ).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/products")
        ).andExpect(
                content().string("[]")
        );
    }

    @Test
    public void getProductObjectById() throws Exception {
        UUID uuid = UUID.randomUUID();
        GenericProductDto response = new GenericProductDto();
        response.setId(uuid);
        response.setTitle("MI 11x");
        response.setCategory("Mobile");
        response.setPrice(21999);

        String id = uuid.toString();

        when(
                productService.getProductById(any())
        ).thenReturn(response);

        mockMvc.perform(
                get("/products/id")
        ).andExpect(
                content().string(objectMapper.writeValueAsString(response))
        );
    }

    @Test
    public void throwExceptionWhenThereIsNoProductToGet() throws Exception {
        when(
                productService.getProductById(any())
        ).thenThrow(NotFoundException.class);

        mockMvc.perform(
                get("/products/id")
        ).andExpect(
                status().is(404)
        );
    }

    @Test
    public void deleteProductById() throws Exception {
        UUID uuid = UUID.randomUUID();
        GenericProductDto response = new GenericProductDto();
        response.setId(uuid);
        response.setTitle("MI 11x");
        response.setCategory("Mobile");
        response.setPrice(21999);

        String id = uuid.toString();

        when(
                productService.deleteProductById(any())
        ).thenReturn(response);

        mockMvc.perform(
                delete("/products/id")
        ).andExpect(
                content().string(objectMapper.writeValueAsString(response))
        );
    }

    @Test
    public void throwExceptionWhenThereIsNoProductIdToDelete() throws Exception {
        when(
                productService.deleteProductById(any())
        ).thenThrow(NotFoundException.class);

        mockMvc.perform(
                delete("/products/id")
        ).andExpect(
                status().is(404)
        );
    }

    @Test
    public void updateProductShouldUpdateOldProduct() throws Exception {
        UUID uuid = UUID.randomUUID();
        GenericProductDto request = new GenericProductDto();
        request.setId(UUID.randomUUID());
        request.setTitle("MI 11x");
        request.setCategory("Mobile");
        request.setPrice(21999);

        GenericProductDto response = new GenericProductDto();
        response.setId(UUID.randomUUID());
        response.setTitle("MI 11x");
        response.setCategory("Mobile");
        response.setPrice(19999);

        String id = uuid.toString();

        when(
                productService.updateProductById(any(), any())
        ).thenReturn(response);

        mockMvc.perform(
                patch("/products/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                content().string(objectMapper.writeValueAsString(response))
        );
    }

    @Test
    public void throwExceptionWhenThereIsNoProductIdToUpdate() throws Exception {
        GenericProductDto request = new GenericProductDto();

        when(
                productService.updateProductById(any(), any())
        ).thenThrow(NotFoundException.class);

        mockMvc.perform(
                patch("/products/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().is(404)
        );
    }
}
