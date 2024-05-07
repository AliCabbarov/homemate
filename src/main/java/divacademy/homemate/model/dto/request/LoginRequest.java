package divacademy.homemate.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import static divacademy.homemate.model.constant.ValidationExceptions.NOT_BLANK_EXCEPTION;

@Getter
public class LoginRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private final String emailOrPhone;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private final String password;

    public LoginRequest(String emailOrPhone, String password) {
        this.emailOrPhone = emailOrPhone;
        this.password = password;
    }
}
