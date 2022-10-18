package vn.com.viettel.utils.exceptions;


import vn.com.viettel.utils.ErrorApp;

public class CustomException extends RuntimeException {

    private ErrorApp errorApp;
    private Integer codeError;

    public CustomException(ErrorApp errorApp) {
        super(errorApp.getDescription());
        this.errorApp = errorApp;
    }

    public CustomException(int code, String mess) {
        super(mess);
        codeError = code;
    }

    public CustomException(String mess) {
        super(mess);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorApp getErrorApp() {
        return errorApp;
    }

    public Integer getCodeError() {
        return codeError;
    }
}
