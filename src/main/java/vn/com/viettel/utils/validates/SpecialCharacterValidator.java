package vn.com.viettel.utils.validates;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpecialCharacterValidator implements ConstraintValidator<SpecialCharacter, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^[_A-z0-9]*((-|\\s)*[_A-z0-9])*$");
    }
}
