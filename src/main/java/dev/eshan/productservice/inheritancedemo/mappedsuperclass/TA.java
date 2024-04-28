package dev.eshan.productservice.inheritancedemo.mappedsuperclass;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "ms_ta")
public class TA extends User {
    private double avgRating;
}
