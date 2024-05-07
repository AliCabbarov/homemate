package divacademy.homemate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GraterThanLocalDateValidator implements ConstraintValidator<GraterThan, LocalDate> {
    private int years;

    @Override
    public void initialize(GraterThan constraintAnnotation) {
        this.years = constraintAnnotation.years();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusYears(years);

        return value.isBefore(minDate);
    }
}
