package ru.vtb.message;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * @author Anastasiia_Fakhrieva
 */

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagesKeys {

    public static final String ERROR_NOT_FOUND = "error.not_found";
    public static final String ERROR_SERVICE_UNAVAILABLE = "error.service_unavailable";
    public static final String ERROR_BAD_REQUEST = "error.bad_request";
    public static final String ERROR_INTERNAL_SERVER = "error.internal_server_error";

    public static final String MSG_PERSON_IS_ALREADY_EXISTED = "exception.person_is_already_existed";

    public static final String MSG_PERSON_NOT_FOUND = "exception.person_not_found";
    public static final String MSG_CONTACT_NOT_FOUND = "exception.contact_not_found";
    public static final String MSG_DOCUMENT_NOT_FOUND = "exception.document_not_found";
    public static final String MSG_ADDRESS_NOT_FOUND = "exception.address_not_found";

    public static final String MSG_VALIDATION_ERROR="exception.validation_error";
    public static final String MSG_DEFAULT = "exception.default";

    public static final String PREFIX_VALIDATION = "exception.validation.";
}
