package divacademy.homemate.controller;

import divacademy.homemate.aop.Log;
import divacademy.homemate.model.dto.request.LoginRequest;
import divacademy.homemate.model.dto.response.TokenResponse;
import divacademy.homemate.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Log

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }
    @Log

    @GetMapping
    public ResponseEntity<TokenResponse> tokenByRefreshToken(@RequestHeader String token) {
        return authenticationService.tokenByRefreshToken(token);
    }
    @Log

    @GetMapping("/oauth-login")
    ResponseEntity<TokenResponse> login(HttpServletRequest request) {
        SecurityContext securityContext = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        return authenticationService.oAuth2Login(securityContext.getAuthentication());
    }


}
