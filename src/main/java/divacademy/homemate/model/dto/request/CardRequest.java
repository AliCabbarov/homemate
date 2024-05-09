package divacademy.homemate.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

import static divacademy.homemate.model.constant.ValidationExceptions.CARD_NUMBER_PATTERN_EXCEPTION;
import static divacademy.homemate.model.constant.ValidationExceptions.NOT_BLANK_EXCEPTION;

@AllArgsConstructor
@Getter
@ToString
public class CardRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    @Pattern(regexp = "\\d{16}",message = CARD_NUMBER_PATTERN_EXCEPTION)
    private String cardNumber;
    private LocalDate expiredDate;
    private int cvv;
}
