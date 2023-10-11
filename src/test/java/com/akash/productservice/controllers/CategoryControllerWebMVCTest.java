package com.akash.productservice.controllers;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.services.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(CategoryController.class)
class CategoryControllerWebMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllCategoriesList() throws Exception {
        List<String> response = new ArrayList<>();
        response.add("Mobiles");
        response.add("Books");
        response.add("Shirts");

        when(
                categoryService.getAllCategories()
        ).thenReturn(response);

        mockMvc.perform(
                get("/category")
        ).andExpect(
                content().string(objectMapper.writeValueAsString(response))
        );
    }

    @Test
    public void getEmptyListWhenThereIsNoCategories() throws Exception {
        when(
                categoryService.getAllCategories()
        ).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/category")
        ).andExpect(
                content().string("[]")
        );
    }

    @Test
    public void getProductsOfGivenCategory() throws Exception{
        List<GenericProductDto> response = new ArrayList<>();
        response.add(new GenericProductDto());
        response.add(new GenericProductDto());

        when(
                categoryService.getInCategory(any())
        ).thenReturn(response);

        mockMvc.perform(
                get("/category/Mobile")
        ).andExpect(
                content().string(objectMapper.writeValueAsString(response))
        );
    }

    @Test
    public void getEmptyProductListWhenThereIsNoGivenCategory() throws Exception {
        when(
                categoryService.getInCategory(any())
        ).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/category/Mobiles")
        ).andExpect(
                content().string("[]")
        );
    }
}