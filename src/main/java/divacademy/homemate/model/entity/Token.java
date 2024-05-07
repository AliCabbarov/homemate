package divacademy.homemate.model.entity;


import divacademy.homemate.model.dto.response.ExceptionResponse;
import divacademy.homemate.model.enums.Exceptions;
import divacademy.homemate.model.enums.TokenType;
import divacademy.homemate.model.exception.ApplicationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Token {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long expired;
    private boolean available;
    private String name;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;
    @ManyToOne
    private User user;

    public Token(long expired, String name, TokenType tokenType, User user) {
        this.expired = expired;
        this.name = name;
        this.tableDetail = TableDetail.of();
        this.user = user;
        this.tokenType = tokenType;
        this.available = true;
    }

    public void unusable() {
        this.available = false;
    }

    public void update(String name, long expired) {
        this.name = name;
        this.expired = expired;
        this.available = true;
        this.tableDetail.update();
    }

    public void isUsableOrElseThrow() throws ApplicationException{
        boolean orElseThrow = available && !Duration.between(LocalDateTime.now(),tableDetail.getUpdatedAt().plusSeconds(expired)).isNegative();
        if (!orElseThrow){
            throw ApplicationException.of(ExceptionResponse.of(Exceptions.TOKEN_EXPIRED_EXCEPTION.getMessage(),Exceptions.TOKEN_EXPIRED_EXCEPTION.getStatus()));
        }
    }
}
