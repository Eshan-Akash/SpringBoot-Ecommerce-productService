package dev.eshan.productservice.inheritancedemo.singletable;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "st_mentor")
public class Mentor extends User {
    private double avgRating;
}
