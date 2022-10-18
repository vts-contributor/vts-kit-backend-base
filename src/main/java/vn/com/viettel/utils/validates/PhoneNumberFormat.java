package vn.com.viettel.utils.validates;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = PhoneNumberFormatValidator.class
)
public @interface PhoneNumberFormat {
    String message() default "Số điện thoại không đúng định dạng ( Bắt đầu là 84 và tối đa 11 số) hoặc ( Bắt đầu là 0 và tối đa 10 số)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
