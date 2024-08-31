package com.bhavesh.utils;
import com.bhavesh.User;
public class UserValidator {
    public static void validateUser(User user, String message) {
        if (user == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
