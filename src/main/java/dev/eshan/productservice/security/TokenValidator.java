package dev.eshan.productservice.security;

import dev.eshan.productservice.dtos.SessionStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TokenValidator {
    private RestTemplateBuilder restTemplateBuilder;
    public TokenValidator(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    /**
     * Calls user service to validate token
     * @param token
     * @return
     */
    public Optional<JwtObject> validateToken(String token, Long userId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<JwtObject> response =
                restTemplate.postForEntity("http://localhost:8080/api/v1/auth/jwtDetails",
                        ValidateTokenRequestDto.builder().token(token).userId(userId).build(), JwtObject.class);
        // call to user service to validate token
        JwtObject jwtObject = response.getBody();
        assert jwtObject != null;
        return Optional.of(jwtObject);
    }

    @Data
    @Builder
    public class ValidateTokenRequestDto {
        private Long userId;
        private String token;
    }
}
