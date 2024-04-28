package dev.eshan.productservice.inheritancedemo.mappedsuperclass;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class User {
    @Id
    private long id;
    private String name;
    private String email;
}
