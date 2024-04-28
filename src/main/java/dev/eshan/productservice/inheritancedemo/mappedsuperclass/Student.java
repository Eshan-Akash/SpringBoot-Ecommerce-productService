package dev.eshan.productservice.inheritancedemo.mappedsuperclass;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "ms_student")
public class Student extends User {
    private double psp;
    private double attendancePercentage;
}
