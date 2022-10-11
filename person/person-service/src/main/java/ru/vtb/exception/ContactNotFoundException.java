package ru.vtb.exception;

import org.springframework.http.HttpStatus;

import static ru.vtb.message.MessagesKeys.ERROR_BAD_REQUEST;
import static ru.vtb.message.MessagesKeys.MSG_CONTACT_NOT_FOUND;

public class ContactNotFoundException extends AbstractLocalizedException {
    public ContactNotFoundException() {
        super(ERROR_BAD_REQUEST, MSG_CONTACT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
