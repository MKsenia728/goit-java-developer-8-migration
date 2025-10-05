package org.example;

import org.example.exeption.ValidationException;

public class ClientValidation {

    public static boolean nameValidation(String name) {
        int length = name.length();
        if (length < 2 || length > 1000) {
            throw new ValidationException("The name must be no more than 1000 and no less than 2 characters. ");
        }
        return true;
    }

    public static boolean idValidation(long id) {
        if (id < 1) {
            throw new ValidationException("Id must be > 0");
        }
        return true;
    }
}
