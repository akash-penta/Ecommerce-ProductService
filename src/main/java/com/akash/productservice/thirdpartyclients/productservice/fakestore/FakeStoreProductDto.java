package com.akash.productservice.thirdpartyclients.productservice.fakestore;

import com.akash.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FakeStoreProductDto {
    private UUID id;
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
}
