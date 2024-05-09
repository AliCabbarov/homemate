package divacademy.homemate.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import static divacademy.homemate.model.constant.ValidationExceptions.NOT_BLANK_EXCEPTION;

@Getter
@AllArgsConstructor
@ToString
public class AdvertPropertyTypeRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    private String name;
    private String description;
    private boolean available;
}
