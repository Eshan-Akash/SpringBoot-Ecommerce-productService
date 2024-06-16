package dev.eshan.productservice.dtos;

import lombok.Data;

@Data
public class SortParameter {
    private String parameterName;
    // ASC or DESC
    private String sortType;
}
