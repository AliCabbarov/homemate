package divacademy.homemate.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static divacademy.homemate.model.constant.ValidationExceptions.NOT_BLANK_EXCEPTION;

@Getter
@Setter
public class AdvertTypeRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String name;
    private String description;
}
