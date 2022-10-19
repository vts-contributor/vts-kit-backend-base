package vn.com.viettel.utils.validates;

import vn.com.viettel.core.config.I18n;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = EmailFormatValidator.class
)
public @interface EmailFormat {

    String message() default "Email is not valid!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
