package com.akash.productservice.services;

import com.akash.productservice.dtos.GenericProductDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CategoryService {
    List<String> getAllCategories();
    List<GenericProductDto> getInCategory(String categoryName);
}
