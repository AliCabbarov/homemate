package divacademy.homemate.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import static divacademy.homemate.model.constant.ValidationExceptions.*;

@Getter
@AllArgsConstructor
@ToString
public class UserRequest {
    @Email(message = EMAIL_EXCEPTION)
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String email;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    @Pattern(regexp = "[0-9]{9,13}",message = PHONE_PATTERN_EXCEPTION)
    private String phoneNumber;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    @Pattern(regexp = "[a-zA-z0-9]{6,20}", message = PASSWORD_PATTERN_EXCEPTION)
    private String password;
}
