package divacademy.homemate.repository;

import divacademy.homemate.model.entity.Token;
import divacademy.homemate.model.entity.User;
import divacademy.homemate.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenTypeAndNameAndAvailable(TokenType tokenType,String name, boolean available);

    Optional<Token> findByUserAndTokenType(User user, TokenType tokenType);
}