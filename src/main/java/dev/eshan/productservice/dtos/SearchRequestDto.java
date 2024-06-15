package dev.eshan.productservice.dtos;

import lombok.Data;

@Data
public class SearchRequestDto {
    private String query;
    private int pageNumber;
    private int sizeOfEachPage;
}
