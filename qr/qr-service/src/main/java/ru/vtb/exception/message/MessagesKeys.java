package ru.vtb.exception.message;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author Anastasiia_Fakhrieva
 */

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagesKeys {

    public static final String ERROR_BAD_REQUEST = "error.bad_request";
    public static final String ERROR_INTERNAL_SERVER = "error.internal_server_error";

    public static final String MSG_DATA_NOT_FOUND = "exception.data_not_found";

    public static final String MSG_VALIDATION_ERROR="exception.validation_error";
    public static final String MSG_DEFAULT = "exception.default";

    public static final String PREFIX_VALIDATION = "exception.validation.";
}
