package ru.vtb.util;

import ru.vtb.model.Person;
import ru.vtb.model.superclass.BaseDateVersionEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class PersonServiceUtil {

    public static <T extends BaseDateVersionEntity> Set<T> setVisibility(Set<T> set, Boolean visibility) {
        return isNull(visibility)
                ? set
                : Optional.ofNullable(set).orElse(Collections.emptySet())
                .stream()
                .filter(T -> visibility.equals(T.isVisibility()))
                .collect(Collectors.toSet());
    }

    public static void setPersonsAbilitiesVisibility(Person person, Boolean visibility) {
        person.setDocuments(setVisibility(person.getDocuments(), visibility));
        person.setAddresses(setVisibility(person.getAddresses(), visibility));
        person.setContacts(setVisibility(person.getContacts(), visibility));
    }
}
