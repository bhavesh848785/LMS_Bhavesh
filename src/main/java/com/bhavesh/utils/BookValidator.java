package com.bhavesh.utils;
import com.bhavesh.Book;
import com.bhavesh.exception.BookNotFoundException;
public class BookValidator {
    public static void validateBookNotNull(Book book, String message) {
        if (book == null) {
            throw new BookNotFoundException(message);
        }
    }
}
