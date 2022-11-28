package vn.com.viettel.utils.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.com.viettel.core.config.I18n;
import vn.com.viettel.utils.ResponseUtils;
import vn.com.viettel.utils.Utils;
import vn.com.viettel.utils.ErrorApp;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidateException(ConstraintViolationException e) {
        LOG.error("Has ERROR", e);
        String mess = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).findFirst().orElse("");
        return ResponseUtils.getResponseEntity(HttpStatus.BAD_REQUEST.value(), I18n.getMessage(mess), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest req, Authentication authentication) {
        LOG.error("Has Access is denied ERROR user: {} in: {}", authentication.getPrincipal(), req.getRequestURI());
        return new ResponseEntity<>(ErrorApp.FORBIDDEN, HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error("Has ERROR", ex);
        String mess = "";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            mess = error.getDefaultMessage();
            switch (Objects.requireNonNull(error.getCode())) {
                case "NotBlank":
                case "NotEmpty":
                case "NotNull":
                    mess = String.format(I18n.getMessage("msg.common.validate.not.null"), Utils.getErrorField(error.getField()));
                    break;
                case "Pattern":
                    mess = String.format(I18n.getMessage("msg.common.validate.type.invalid"), Utils.getErrorField(error.getField()));
                    break;
                case "Size":
                    if (error.getArguments() != null && error.getArguments().length == 3) {
                        int min = (int) error.getArguments()[2];
                        int max = (int) error.getArguments()[1];
                        mess = String.format(I18n.getMessage("msg.common.validate.size.invalid"), Utils.getErrorField(error.getField()), min, max);
                    }
                    break;
                case "Max":
                    if (error.getArguments() != null && error.getArguments().length == 2) {
                        long max = (long) error.getArguments()[1];
                        mess = String.format(I18n.getMessage("msg.common.validate.max.invalid"), Utils.getErrorField(error.getField()), max);
                    }
                    break;
                case "Min":
                    if (error.getArguments() != null && error.getArguments().length == 2) {
                        long min = (long) error.getArguments()[1];
                        mess = String.format(I18n.getMessage("msg.common.validate.min.invalid"), Utils.getErrorField(error.getField()), min);
                    }
                    break;
                case "Digits":
                    mess = String.format(I18n.getMessage("msg.common.method.argument.not.valid"), Utils.getErrorField(error.getField()));
            }
        }
        return ResponseUtils.getResponseEntity(HttpStatus.BAD_REQUEST.value(), I18n.getMessage(mess), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error("Has ERROR", ex);
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            for (JsonMappingException.Reference path : cause.getPath()) {
                String mess = path.getFieldName() + ": Invalid format";
                return ResponseUtils.getResponseEntity(HttpStatus.BAD_REQUEST.value(), I18n.getMessage(mess), HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseUtils.getResponseEntity(ErrorApp.INTERNAL_SERVER, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleETCException(CustomException ex) {
        if (!Utils.isNullOrEmpty(ex.getStackTrace())) {
            LOG.error("Has ERROR EtcException with message = {}, at = {}",
                    ex.getMessage(), ex.getStackTrace()[0].toString());
        } else {
            LOG.error("Has ERROR EtcException with code = {}, message = {}", ex.getMessage(), ex.getMessage());
        }
        if (Objects.isNull(ex.getErrorApp())) {
            if (Objects.nonNull(ex.getCodeError())) {
                return ResponseUtils.getResponseEntity(ex.getCodeError(), ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return ResponseUtils.getResponseEntity(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseUtils.getResponseEntity(500, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        LOG.error("Has ERROR", ex);
        return ResponseUtils.getResponseEntity(500, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error("No handler found exception", ex);
        return handleExceptionInternal(ex, null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.error("Handle type mismatch", ex);
        return handleExceptionInternal(ex, null, headers, status, request);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)   //(1)
    public Object exceptionHandler(IOException e, HttpServletRequest request) {
        if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "Broken pipe")) {   //(2)
            return null;        //(2)	socket is closed, cannot return any response
        } else {
            return new HttpEntity<>(e.getMessage());  //(3)
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleException(MethodArgumentTypeMismatchException ex) {
        if (ex.getStackTrace() != null) {
            LOG.error("Has ERROR MethodArgumentTypeMismatchException with message = {}, at = {}",
                    ex.getMessage(), ex.getStackTrace()[0].toString());
        } else {
            LOG.error("Has ERROR MethodArgumentTypeMismatchException with message = {}", ex.getMessage());
        }
        return ResponseUtils.getResponseEntity(ErrorApp.BAD_REQUEST_PATH, HttpStatus.BAD_REQUEST);
    }
}
