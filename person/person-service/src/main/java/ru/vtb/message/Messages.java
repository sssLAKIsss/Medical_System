package ru.vtb.message;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("messages.messages", locale)
                .getString(messageKey);
    }
}
