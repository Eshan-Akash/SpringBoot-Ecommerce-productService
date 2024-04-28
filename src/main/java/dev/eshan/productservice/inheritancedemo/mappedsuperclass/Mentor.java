package dev.eshan.productservice.inheritancedemo.mappedsuperclass;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "ms_mentor")
public class Mentor extends User {
    private double avgRating;
}
