package com.akash.productservice.repositories;

import com.akash.productservice.models.Category;
import com.akash.productservice.models.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, UUID> {
    @Override
    <S extends Product> S save(S entity);

    @Override
    void deleteById(UUID uuid);

    List<Product> findAllByCategoryIn(List<Category> categories);
}
