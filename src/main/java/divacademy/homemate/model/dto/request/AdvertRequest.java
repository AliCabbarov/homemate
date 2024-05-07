package divacademy.homemate.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static divacademy.homemate.model.constant.ValidationExceptions.NOT_BLANK_EXCEPTION;

@Getter
@AllArgsConstructor
public class AdvertRequest {
    private String description;
    private double amount;
    private List<Long> advertTypesId;
    @Valid
    private AdvertDetailRequest advertDetail;
    @Valid
    private PropertyDetailRequest propertyDetail;
}
