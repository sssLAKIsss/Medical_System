package ru.vtb.controller.exception;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.hibernate.internal.util.StringHelper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.vtb.dto.ApiError;
import ru.vtb.exception.AbstractLocalizedException;
import ru.vtb.message.Messages;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static ru.vtb.message.MessagesKeys.ERROR_BAD_REQUEST;
import static ru.vtb.message.MessagesKeys.ERROR_INTERNAL_SERVER;
import static ru.vtb.message.MessagesKeys.MSG_DEFAULT;
import static ru.vtb.message.MessagesKeys.MSG_VALIDATION_ERROR;
import static ru.vtb.message.MessagesKeys.PREFIX_VALIDATION;


@ControllerAdvice
@Slf4j
public class Handler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AbstractLocalizedException.class})
    public ResponseEntity<Object> handleLocalizedExceptionException(AbstractLocalizedException ex,
                                                                    WebRequest request) {
        log.error(ex.getMessage(), ex);

        return handleExceptionInternal(
                ex,
                ApiError.builder()
                        .error(ex.getLocalizedError(request.getLocale()))
                        .message(ex.getLocalizedMessage(request.getLocale()))
                        .status(ex.getStatus().value())
                        .rawMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                new HttpHeaders(),
                ex.getStatus(),
                request);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            WebRequest request) {

        Throwable cause = ex.getMostSpecificCause();
        if (cause instanceof PropertyValueException) {
            return handlePropertyValueException((PropertyValueException) cause, request);
        }
        log.error(ex.getMostSpecificCause().getMessage(), ex);
        return handleExceptionInternal(
                ex,
                ApiError.builder()
                        .error(Messages.getMessageForLocale(ERROR_BAD_REQUEST, request.getLocale()))
                        .message(ex.getLocalizedMessage())
                        .rawMessage(ex.getMostSpecificCause().getMessage())
                        .status(BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build(),
                new HttpHeaders(),
                BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = {PropertyValueException.class})
    public ResponseEntity<Object> handlePropertyValueException(PropertyValueException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        Locale locale = request.getLocale();
        String messageKey = PREFIX_VALIDATION
                + StringHelper.unqualify(ex.getEntityName()).toLowerCase() + "_" + ex.getPropertyName();

        ApiError error = ApiError.builder()
                .error(Messages.getMessageForLocale(ERROR_BAD_REQUEST, locale))
                .message(Messages.getMessageForLocale(messageKey, locale))
                .rawMessage(ex.getMessage())
                .status(BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), BAD_REQUEST, request);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  WebRequest request) {
        log.error(ex.getMessage(), ex);

        Locale locale = request.getLocale();
        ApiError error = ApiError.builder()
                .error(Messages.getMessageForLocale(ERROR_BAD_REQUEST, locale))
                .message(Messages.getMessageForLocale(MSG_VALIDATION_ERROR, locale))
                .rawMessage(
                        ex.getBindingResult().getFieldErrors()
                                .stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining(", ")))
                .status(BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                     WebRequest request) {
        log.error(ex.getMessage(), ex);

        Locale locale = request.getLocale();
        ApiError error = ApiError.builder()
                .error(Messages.getMessageForLocale(ERROR_BAD_REQUEST, locale))
                .message(Messages.getMessageForLocale(MSG_VALIDATION_ERROR, locale))
                .rawMessage(ex.getMessage())
                .status(BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        return handleExceptionInternal(
                ex,
                null,
                getHeadersFromRequest(request),
                INTERNAL_SERVER_ERROR,
                request);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        log.error(ex.getMessage(), ex);

        if (body instanceof ApiError) {
            return new ResponseEntity<>(body, headers, status);
        }

        Locale locale = request.getLocale();
        ApiError error = ApiError.builder()
                .error(Messages.getMessageForLocale(ERROR_INTERNAL_SERVER, locale))
                .message(Messages.getMessageForLocale(MSG_DEFAULT, locale))
                .rawMessage(ex.getMessage())
                .status(INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, headers, status);
    }

    private HttpHeaders getHeadersFromRequest(WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        request.getHeaderNames().forEachRemaining(
                headerName -> headers.addIfAbsent(headerName, request.getHeader(headerName))
        );
        return headers;
    }
}
