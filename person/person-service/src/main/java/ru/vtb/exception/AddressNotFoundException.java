package ru.vtb.exception;

import org.springframework.http.HttpStatus;

import static ru.vtb.message.MessagesKeys.ERROR_BAD_REQUEST;
import static ru.vtb.message.MessagesKeys.MSG_ADDRESS_NOT_FOUND;

public class AddressNotFoundException extends AbstractLocalizedException {
    public AddressNotFoundException() {
        super(ERROR_BAD_REQUEST, MSG_ADDRESS_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
