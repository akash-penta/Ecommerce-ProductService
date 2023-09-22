package com.akash.productservice.repositories;

import com.akash.productservice.models.Category;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Lazy
public interface CategoryRepository
    extends JpaRepository<Category, UUID> {
    @Override
    List<Category> findAll();

    List<Category> findAllByName(String name);
}
