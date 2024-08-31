package com.bhavesh;
import java.util.Map;
import java.time.Year;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.bhavesh.exception.UserExistException;
import com.bhavesh.exception.BookNotFoundException;
import com.bhavesh.exception.PermissionDeniedException;
import com.bhavesh.exception.BookAlreadyBorrowdException;

public class LibraryTest {

    Library library = new Library("Drishti");
    @Test
    public void testShouldFailWithoutProperConstructor() {
        assertNotNull(library);
    }

    @Test
    public void testLibraryNameShouldNotbeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Library(null));
    }

    @Test
    public void testLibraryNameShouldNotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Library(""));
    }

    @Test
    public void testLibraryNameShouldBeGreaterThan4Characters() {
        assertThrows(IllegalArgumentException.class, () -> new Library("Dris"));
    }

    @Test
    public void testShouldThrowExceptionIfUserIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addUser(null));
        assertEquals("User should not be null", exception.getMessage());
    }


}
