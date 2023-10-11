package com.akash.productservice.controllers;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundException;
import com.akash.productservice.exceptions.UnableToCreateProductException;
import com.akash.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(@Qualifier("selfProductServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto genericProductDto) throws UnableToCreateProductException {
        return productService.createProduct(genericProductDto);
    }

    @GetMapping
    public List<GenericProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@PathVariable("id") String id) throws NotFoundException {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public GenericProductDto deleteProductById(@PathVariable("id") String id) throws NotFoundException {
        return productService.deleteProductById(id);
    }

    @PatchMapping("/{id}")
    public GenericProductDto updateProductById(
            @PathVariable("id") String id,
            @RequestBody GenericProductDto genericProductDto
    ) throws NotFoundException {
        return productService.updateProductById(id, genericProductDto);
    }
}
