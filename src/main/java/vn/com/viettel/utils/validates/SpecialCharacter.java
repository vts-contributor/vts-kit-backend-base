package vn.com.viettel.utils.validates;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SpecialCharacterValidator.class)
public @interface SpecialCharacter {

    String message() default "Value cannot contain special character!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
