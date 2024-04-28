package dev.eshan.productservice.inheritancedemo.singletable;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "st_student")
public class Student extends User {
    private double psp;
    private double attendancePercentage;
}
