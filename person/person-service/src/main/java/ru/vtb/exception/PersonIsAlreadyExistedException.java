package ru.vtb.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.vtb.message.MessagesKeys.ERROR_BAD_REQUEST;
import static ru.vtb.message.MessagesKeys.MSG_PERSON_IS_ALREADY_EXISTED;

public class PersonIsAlreadyExistedException extends AbstractLocalizedException {
    public PersonIsAlreadyExistedException() {
        super(ERROR_BAD_REQUEST, MSG_PERSON_IS_ALREADY_EXISTED, BAD_REQUEST);
    }
}
