package dev.eshan.productservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.eshan.productservice.model.Category;
import dev.eshan.productservice.model.Price;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericProductDto {
    String id;
    String title;
    String description;
    Category category;
    String image;
    Price price;
}
