package ru.vtb.controller.exception;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import ru.vtb.exception.AbstractLocalizedException;
import ru.vtb.message.Messages;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static ru.vtb.message.MessagesKeys.ERROR_INTERNAL_SERVER;
import static ru.vtb.message.MessagesKeys.MSG_DEFAULT;

@ControllerAdvice
@Slf4j
public class Handler {

    @ExceptionHandler(AbstractLocalizedException.class)
    public ResponseEntity<Object> handleLocalizedExceptionException(AbstractLocalizedException ex,
                                                                    ServerWebExchange exchange) {
        return buildResponse(
                ex,
                ex.getStatus(),
                exchange
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception ex, ServerWebExchange exchange) {
        return buildResponse(ex, INTERNAL_SERVER_ERROR, exchange);
    }

    protected ResponseEntity<Object> buildResponse(@NonNull Exception ex,
                                                   @NonNull HttpStatus status,
                                                   @NonNull ServerWebExchange exchange) {
        log.error(ex.getMessage(), ex);

        Locale locale = exchange.getLocaleContext().getLocale();
        ApiError error = ApiError.builder()
                .error(Messages.getMessageForLocale(ERROR_INTERNAL_SERVER, locale))
                .message(Messages.getMessageForLocale(MSG_DEFAULT, locale))
                .rawMessage(ex.getMessage())
                .status(INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(
                error,
                exchange.getRequest().getHeaders(),
                status
        );
    }
}
