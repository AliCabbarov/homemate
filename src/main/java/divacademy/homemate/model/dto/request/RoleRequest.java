package divacademy.homemate.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

import static divacademy.homemate.model.constant.ValidationExceptions.NOT_BLANK_EXCEPTION;

@Getter
@AllArgsConstructor
@ToString
public class RoleRequest {
    @NotBlank(message = NOT_BLANK_EXCEPTION)
    @Pattern(regexp = "^ROLE_.*")
    private String name;
    private List<Long> permissionsId;
}
