package vn.com.viettel.utils.validates;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberFormatValidator implements ConstraintValidator<PhoneNumberFormat, String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isEmpty(phoneNumber) || phoneNumber.matches("^(84|0)([0-9]{9})");
    }
}
