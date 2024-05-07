package divacademy.homemate.service.impl;

import divacademy.homemate.model.dto.response.ExceptionResponse;
import divacademy.homemate.model.entity.Token;
import divacademy.homemate.model.entity.User;
import divacademy.homemate.model.enums.TokenType;
import divacademy.homemate.model.exception.NotFoundException;
import divacademy.homemate.repository.TokenRepository;
import divacademy.homemate.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static divacademy.homemate.model.enums.Exceptions.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    @Value("${app.time.token.confirmation}")
    private long confirmationTime;
    @Value("${app.time.token.refresh}")
    private long refreshTime;
    @Value("${app.time.token.forgotten}")
    private long forgottenTime;


    @Override
    public String generateAndSaveRefreshToken(User user) {
        String refreshToken = getRandomUUID();
        tokenRepository.findByUserAndTokenType(user,TokenType.REFRESH).ifPresentOrElse(token -> {
            token.update(refreshToken,refreshTime);
            tokenRepository.save(token);
        },() -> {
            Token token = new Token(confirmationTime, refreshToken, TokenType.REFRESH, user);
            tokenRepository.save(token);
        });
        return refreshToken;
    }

    @Override
    public String generateAndSaveConfirmationToken(User user) {
        String confirmationToken = getRandomUUID();

        tokenRepository.findByUserAndTokenType(user, TokenType.CONFIRMATION).ifPresentOrElse(token -> {
            token.update(confirmationToken, confirmationTime);
            tokenRepository.save(token);
        }, () -> {
            Token token = new Token(confirmationTime, confirmationToken, TokenType.CONFIRMATION, user);
            tokenRepository.save(token);
        });

        return confirmationToken;
    }

    @Override
    public String generateAndSaveForgottenToken(User user) {
        String forgottenToken = getRandomUUID();

        tokenRepository.findByUserAndTokenType(user, TokenType.FORGOTTEN_PASSWORD).ifPresentOrElse(token -> {
            token.update(forgottenToken, forgottenTime);
            tokenRepository.save(token);
        }, () -> {
            Token token = new Token(forgottenTime, forgottenToken, TokenType.FORGOTTEN_PASSWORD, user);
            tokenRepository.save(token);
        });
        return forgottenToken;
    }


    private String getRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
