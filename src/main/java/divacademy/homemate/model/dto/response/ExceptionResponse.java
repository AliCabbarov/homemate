package divacademy.homemate.model.dto.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@ToString
public class ExceptionResponse {
    private final String message;
    private final HttpStatus status;

    public ExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public static ExceptionResponse of(String message, HttpStatus status) {
        return new ExceptionResponse(message, status);
    }
}
