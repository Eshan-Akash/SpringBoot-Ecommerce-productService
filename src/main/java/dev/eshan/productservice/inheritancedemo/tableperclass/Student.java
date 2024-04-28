package dev.eshan.productservice.inheritancedemo.tableperclass;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "tpc_student")
public class Student extends User {
    private double psp;
    private double attendancePercentage;
}
