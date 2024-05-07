package divacademy.homemate.service.impl;

import divacademy.homemate.config.AuthenticationDetails;
import divacademy.homemate.model.dto.request.LoginRequest;
import divacademy.homemate.model.dto.response.ExceptionResponse;
import divacademy.homemate.model.dto.response.TokenResponse;
import divacademy.homemate.model.entity.Token;
import divacademy.homemate.model.entity.User;
import divacademy.homemate.model.enums.TokenType;
import divacademy.homemate.model.exception.NotFoundException;
import divacademy.homemate.model.exception.UsernameNotFoundException;
import divacademy.homemate.repository.TokenRepository;
import divacademy.homemate.repository.UserRepository;
import divacademy.homemate.service.AuthenticationService;
import divacademy.homemate.service.TokenService;
import divacademy.homemate.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

import static divacademy.homemate.model.enums.Exceptions.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public ResponseEntity<TokenResponse> login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmailOrPhone(),
                        loginRequest.getPassword()
                )
        );
        AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();

        String jasonWebToken = tokenUtil.generateToken(details.getUserEmailOrPhone(), details.getUserId(), getAuthorities(authentication.getAuthorities()));

        User user = getUserById(details.getUserId());
        user.updateLoginIp();
        userRepository.save(user);

        String refreshToken = tokenService.generateAndSaveRefreshToken(user);

        return new ResponseEntity<>(TokenResponse.of(jasonWebToken, refreshToken), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TokenResponse> tokenByRefreshToken(String token) {
        Token findedToken = tokenRepository.findByTokenTypeAndNameAndAvailable(TokenType.REFRESH, token, true).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), TokenType.REFRESH.name()));

        findedToken.isUsableOrElseThrow();

        User user = findedToken.getUser();
        String refreshToken = tokenService.generateAndSaveRefreshToken(user);
        String jasonWebToken = tokenUtil.generateToken(user.getEmail(), user.getId(), getAuthorities(user.getAuthorities()));

        return new ResponseEntity<>(TokenResponse.of(jasonWebToken, refreshToken), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TokenResponse> oAuth2Login(Authentication authentication) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) authentication;

        AuthenticationDetails details = (AuthenticationDetails) usernamePasswordAuthenticationToken.getDetails();

        User user = getUserById(details.getUserId());
        user.updateLoginIp();
        userRepository.save(user);

        String refreshToken = tokenService.generateAndSaveRefreshToken(user);
        String jasonWebToken = tokenUtil.generateToken(user.getEmail(), user.getId(), getAuthorities(user.getAuthorities()));


        return new ResponseEntity<>(TokenResponse.of(jasonWebToken, refreshToken), HttpStatus.OK);
    }

    private Map<String, Object> getAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {

        Map<String, Object> extraClaims = new HashMap<>();
        List<String> authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        extraClaims.put("authorities", authorities);
        return extraClaims;
    }
        private User getUserById(long id){
            return  userRepository.findById(id).orElseThrow(() -> UsernameNotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus())));

        }
}
