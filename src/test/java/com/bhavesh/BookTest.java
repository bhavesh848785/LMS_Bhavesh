package com.bhavesh;


import java.time.Year;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BookTest {
    @Test
    public void When_ISBN_isNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Book(null, "Clean Code", "Robert Cecil Martin", Year.of(2012)));
        assertEquals("ISBN should not be null or empty", exception.getMessage());
    }

    @Test
    public void When_Title_IsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Book("9780132350884", null, "Robert Cecil Martin", Year.of(2012)));
        assertEquals("title should not be null or empty", exception.getMessage());
    }

    @Test
    public void When_Author_IsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Book("9780132350884", "Clean Code", "", Year.of(2012)));
        assertEquals("author should not be null or empty", exception.getMessage());
    }

    @Test
    public void When_Publication_Year_IsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Book("9780132350884", "Clean Code", "Robert Cecil Martin", null));
        assertEquals("publication year should not be null", exception.getMessage());
    }


}
