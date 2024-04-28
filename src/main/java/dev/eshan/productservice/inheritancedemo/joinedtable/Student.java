package dev.eshan.productservice.inheritancedemo.joinedtable;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "jt_student")
public class Student extends User {
    private double psp;
    private double attendancePercentage;
}
