package divacademy.homemate.model.dto.request;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static divacademy.homemate.model.constant.ValidationExceptions.NOT_BLANK_EXCEPTION;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubsRequest {
    private long subsTypeId;
}
