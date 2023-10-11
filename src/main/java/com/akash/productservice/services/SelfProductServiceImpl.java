package com.akash.productservice.services;

import com.akash.productservice.dtos.GenericProductDto;
import com.akash.productservice.exceptions.NotFoundException;
import com.akash.productservice.exceptions.UnableToCreateProductException;
import com.akash.productservice.models.Category;
import com.akash.productservice.models.Price;
import com.akash.productservice.models.Product;
import com.akash.productservice.repositories.CategoryRepository;
import com.akash.productservice.repositories.PriceRepository;
import com.akash.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("selfProductServiceImpl")
public class SelfProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private PriceRepository priceRepository;

    public SelfProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            PriceRepository priceRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) throws UnableToCreateProductException {
        if(genericProductDto.getTitle().isBlank()) {
            throw new UnableToCreateProductException("Unable to create product without title");
        }

        Category category = new Category();
        category.setName(genericProductDto.getCategory());

        Price price = new Price();
        price.setCurrency(genericProductDto.getCurrency());
        price.setPrice(genericProductDto.getPrice());

        Product product = new Product();
        product.setTitle(genericProductDto.getTitle());
        product.setDescription(genericProductDto.getDescription());
        product.setImage(genericProductDto.getImage());
        product.setCategory(category);
        product.setPrice(price);

        Product resultProduct = productRepository.save(product);

        return convertProductToGenericProductDto(resultProduct);
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> resultProducts = productRepository.findAll();

        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for(Product product: resultProducts) {
            genericProductDtos.add(convertProductToGenericProductDto(product));
        }

        return genericProductDtos;
    }

    @Override
    public GenericProductDto getProductById(String id) throws NotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));
        if(optionalProduct.isEmpty()) {
            throw new NotFoundException("Product with Id:" + id + " is not found");
        }
        
        Product resultProduct = optionalProduct.get();
        
        return convertProductToGenericProductDto(resultProduct);
    }

    @Override
    public GenericProductDto deleteProductById(String id) throws NotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));
        if(optionalProduct.isEmpty()) {
            throw new NotFoundException("Product with Id:" + id + " is not found");
        }

        productRepository.deleteById(UUID.fromString(id));

        Product deletedProduct = optionalProduct.get();

        return convertProductToGenericProductDto(deletedProduct);
    }

    @Override
    public GenericProductDto updateProductById(String id, GenericProductDto genericProductDto) throws NotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));
        if(optionalProduct.isEmpty()) {
            throw new NotFoundException("Product with Id:" + id + " is not found");
        }

        Product product = optionalProduct.get();

        if(genericProductDto.getTitle() != null) {
            product.setTitle(genericProductDto.getTitle());
        }
        if(genericProductDto.getDescription() != null) {
            product.setDescription(genericProductDto.getDescription());
        }
        if(genericProductDto.getImage() != null) {
            product.setImage(genericProductDto.getImage());
        }
        if(genericProductDto.getCategory() != null) {
            product.getCategory().setName(genericProductDto.getCategory());
        }
        if(genericProductDto.getCurrency() != null) {
            product.getPrice().setCurrency(genericProductDto.getCurrency());
        }
        if(genericProductDto.getPrice() != 0) {
            product.getPrice().setPrice(genericProductDto.getPrice());
        }

        Product resultProduct = productRepository.save(product);

        return convertProductToGenericProductDto(resultProduct);
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
