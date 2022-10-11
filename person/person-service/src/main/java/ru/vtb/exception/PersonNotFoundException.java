package ru.vtb.exception;


import org.springframework.http.HttpStatus;

import static ru.vtb.message.MessagesKeys.ERROR_BAD_REQUEST;
import static ru.vtb.message.MessagesKeys.MSG_PERSON_NOT_FOUND;

public class PersonNotFoundException extends AbstractLocalizedException {
    public PersonNotFoundException() {
        super(ERROR_BAD_REQUEST, MSG_PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
