package ru.vtb.message;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagesKeys {

    public static final String ERROR_BAD_REQUEST = "error.bad_request";
    public static final String ERROR_INTERNAL_SERVER = "error.internal_server_error";

    public static final String MSG_USER_IS_ALREADY_EXISTED = "exception.user_is_already_existed";

    public static final String MSG_DEFAULT = "exception.default";

}
