package com.akash.productservice.controllers;

import com.akash.productservice.dtos.ExceptionDto;
import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundExcpetion;
import com.akash.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(@Qualifier("fakeStoreProductServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto genericProductDto) {
        return productService.createProduct(genericProductDto);
    }

    @GetMapping
    public List<GenericProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@PathVariable("id") Long id) throws NotFoundExcpetion {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public GenericProductDto deleteProductById(@PathVariable("id") Long id) throws NotFoundExcpetion {
        return productService.deleteProductById(id);
    }

    @PatchMapping("/{id}")
    public GenericProductDto updateProductById(
            @PathVariable("id") Long id,
            @RequestBody GenericProductDto genericProductDto
    ) throws NotFoundExcpetion {
        return productService.updateProductById(id, genericProductDto);
    }
}
