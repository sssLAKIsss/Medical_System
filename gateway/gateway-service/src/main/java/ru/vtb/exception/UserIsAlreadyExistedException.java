package ru.vtb.exception;

import org.springframework.http.HttpStatus;

import static ru.vtb.message.MessagesKeys.ERROR_BAD_REQUEST;
import static ru.vtb.message.MessagesKeys.MSG_USER_IS_ALREADY_EXISTED;

public class UserIsAlreadyExistedException extends AbstractLocalizedException {
    public UserIsAlreadyExistedException() {
        super(ERROR_BAD_REQUEST, MSG_USER_IS_ALREADY_EXISTED , HttpStatus.NOT_FOUND);
    }
}
