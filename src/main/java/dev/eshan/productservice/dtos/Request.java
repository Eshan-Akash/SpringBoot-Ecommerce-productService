package dev.eshan.productservice.dtos;

import dev.eshan.productservice.security.JwtObject;
import lombok.Data;

@Data
public class Request<T> {
    T usrPayload;
    JwtObject authPayload;
}
