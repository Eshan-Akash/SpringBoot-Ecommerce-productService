package dev.eshan.productservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@Document(indexName = "blog")
public class Product extends BaseModel {
    String title;
    String description;
    // P : C
    // L to R: 1 : 1
    // R to L: m : 1
    //ans => m : 1
    @ManyToOne(cascade = {CascadeType.PERSIST})
    Category category;
    String image;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    Price price;
}
