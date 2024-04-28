package dev.eshan.productservice.inheritancedemo.singletable;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "st_ta")
public class TA extends User {
    private double avgRating;
}
