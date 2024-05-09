package divacademy.homemate.model.dto.request;

import divacademy.homemate.validator.GraterThan;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

import static divacademy.homemate.model.constant.ValidationExceptions.*;

@Getter
@AllArgsConstructor
@ToString
public class UserDetailRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String firstname;
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String lastname;
    @GraterThan(message = GRATER_THAN_EXCEPTION, years = 17)
    private LocalDate birthdate;
}
