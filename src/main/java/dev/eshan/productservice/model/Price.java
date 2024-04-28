package dev.eshan.productservice.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Price extends BaseModel {
    private String currency;
    private double price = 0;
}
