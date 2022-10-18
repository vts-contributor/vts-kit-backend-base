package vn.com.viettel.utils.validates;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailFormatValidator implements ConstraintValidator<EmailFormat, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
//        return StringUtils.isEmpty(email) || email.matches("[A-Za-z][A-Za-z0-9._-]*@[A-Za-z0-9._-]+\\.[a-z]*");
        /**
         * Sua validate theo yeu cau giai phap
         * 1.Phai co ki tu @ o giua
         * 2. Cho phep nhap chu, so va .-_+
         * 3. email khong chua ki tu khoang trang
         */
        return StringUtils.isEmpty(email) || email.matches("[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+");
    }
}
