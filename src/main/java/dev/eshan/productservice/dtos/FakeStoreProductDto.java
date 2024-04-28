package dev.eshan.productservice.dtos;

import lombok.Data;

@Data
public class FakeStoreProductDto {
    private long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
}
