package ru.vtb.exception;

import org.springframework.http.HttpStatus;
import ru.vtb.message.Messages;

import java.util.Locale;

public abstract class AbstractLocalizedException extends RuntimeException {

    private final String errorKey;
    private final String messageKey;
    private final HttpStatus status;

    protected AbstractLocalizedException (String errorKey, String messageKey, HttpStatus status, Throwable cause) {
        super(cause);
        this.errorKey = errorKey;
        this.messageKey = messageKey;
        this.status = status;
    }

    protected AbstractLocalizedException (String errorKey, String messageKey, HttpStatus status) {
        this(errorKey, messageKey, status, null);
    }

    public String getLocalizedMessage(Locale locale) {
        return Messages.getMessageForLocale(messageKey, locale);
    }

    @Override
    public String getMessage() {
        return Messages.getMessageForLocale(messageKey, Locale.getDefault());
    }

    public String getLocalizedError(Locale locale) {
        return Messages.getMessageForLocale(errorKey, locale);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
