package dev.eshan.productservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.eshan.productservice.model.Product;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericCategoryDto {
    private String name;
    private List<GenericProductDto> products;
}
