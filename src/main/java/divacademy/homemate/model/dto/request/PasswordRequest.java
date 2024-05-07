package divacademy.homemate.model.dto.request;

import divacademy.homemate.model.dto.response.ExceptionResponse;
import divacademy.homemate.model.exception.ApplicationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static divacademy.homemate.model.constant.ValidationExceptions.NOT_BLANK_EXCEPTION;
import static divacademy.homemate.model.constant.ValidationExceptions.PASSWORD_PATTERN_EXCEPTION;
import static divacademy.homemate.model.enums.Exceptions.NOT_SAME_PASSWORD;

@Getter
public class PasswordRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    @Pattern(regexp = "[a-zA-z0-9]{6,20}", message = PASSWORD_PATTERN_EXCEPTION)
    private final String password;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    @Pattern(regexp = "[a-zA-z0-9]{6,20}", message = PASSWORD_PATTERN_EXCEPTION)
    private final String repeatPassword;

    public PasswordRequest(String password, String repeatPassword) {
        this.password = password;
        this.repeatPassword = repeatPassword;
        elseThrow();
    }

    private void elseThrow() throws ApplicationException{
        if (!this.password.equals(this.repeatPassword)) throw ApplicationException.of(ExceptionResponse.of(NOT_SAME_PASSWORD.getMessage(),NOT_SAME_PASSWORD.getStatus()));
    }
}
