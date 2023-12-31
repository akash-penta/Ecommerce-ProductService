package com.akash.productservice.dtos;

import com.akash.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GenericProductDto {
    private UUID id;
    private String title;
    private String description;
    private String image;
    private String category;
    private String currency;
    private double price;
}
