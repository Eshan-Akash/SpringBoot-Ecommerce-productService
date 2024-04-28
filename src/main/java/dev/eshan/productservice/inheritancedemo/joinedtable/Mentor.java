package dev.eshan.productservice.inheritancedemo.joinedtable;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "jt_mentor")
public class Mentor extends User {
    private double avgRating;
}
