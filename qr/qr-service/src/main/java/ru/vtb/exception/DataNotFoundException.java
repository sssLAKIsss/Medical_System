package ru.vtb.exception;

import org.springframework.http.HttpStatus;

import static ru.vtb.exception.message.MessagesKeys.ERROR_BAD_REQUEST;
import static ru.vtb.exception.message.MessagesKeys.MSG_DATA_NOT_FOUND;

public class DataNotFoundException extends AbstractLocalizedException {
    public DataNotFoundException() {
        super(ERROR_BAD_REQUEST, MSG_DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
