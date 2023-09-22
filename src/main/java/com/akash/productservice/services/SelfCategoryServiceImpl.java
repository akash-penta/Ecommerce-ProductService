package com.akash.productservice.services;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.models.Category;
import com.akash.productservice.models.Product;
import com.akash.productservice.repositories.CategoryRepository;
import com.akash.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SelfCategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public SelfCategoryServiceImpl(
            CategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<String> categoryNames = new ArrayList<>();

        categories.forEach(
                category -> categoryNames.add(category.getName())
        );

        return categoryNames;
    }

    @Override
    public List<GenericProductDto> getInCategory(String categoryName) {
        List<Category> categories = categoryRepository.findAllByName(categoryName);

        List<Product> products = productRepository.findAllByCategoryIn(categories);

        List<GenericProductDto> genericProductDtos = new ArrayList<>();

        products.forEach(
                product -> genericProductDtos.add(convertProductToGenericProductDto(product))
        );

        return genericProductDtos;
    }

    private static GenericProductDto convertProductToGenericProductDto(Product resultProduct) {
        GenericProductDto result = new GenericProductDto();
        result.setId(resultProduct.getId());
        result.setTitle(resultProduct.getTitle());
        result.setDescription(resultProduct.getDescription());
        result.setImage(resultProduct.getImage());
        result.setCategory(resultProduct.getCategory().getName());
        result.setCurrency(resultProduct.getPrice().getCurrency());
        result.setPrice(resultProduct.getPrice().getPrice());
        return result;
    }
}
