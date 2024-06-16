package dev.eshan.productservice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequestDto {
    private String query;
    private int pageNumber;
    private int sizeOfEachPage;
    private List<SortParameter> sortByParameters;
}
