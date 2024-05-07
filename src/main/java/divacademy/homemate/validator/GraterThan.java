package divacademy.homemate.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {GraterThanLocalDateValidator.class}
)
public @interface GraterThan {
    String message() default "age not valid exception";
    int years() default 18;
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}
