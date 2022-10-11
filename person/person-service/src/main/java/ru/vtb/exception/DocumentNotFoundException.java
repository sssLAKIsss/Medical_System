package ru.vtb.exception;


import org.springframework.http.HttpStatus;

import static ru.vtb.message.MessagesKeys.ERROR_BAD_REQUEST;
import static ru.vtb.message.MessagesKeys.MSG_DOCUMENT_NOT_FOUND;

public class DocumentNotFoundException extends AbstractLocalizedException {
    public DocumentNotFoundException() {
        super(ERROR_BAD_REQUEST, MSG_DOCUMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
