package divacademy.homemate.service;

import divacademy.homemate.model.dto.request.LoginRequest;
import divacademy.homemate.model.dto.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    ResponseEntity<TokenResponse> login(LoginRequest loginRequest);

    ResponseEntity<TokenResponse> tokenByRefreshToken( String token);

    ResponseEntity<TokenResponse> oAuth2Login(Authentication authentication);
}
