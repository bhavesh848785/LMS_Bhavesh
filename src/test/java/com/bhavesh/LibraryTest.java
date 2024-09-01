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
    public void Library_Name_Should_Not_be_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Library(null));
    }

    @Test
    public void Library_Name_Should_Not_BeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Library(""));
    }

    @Test
    public void Library_Name_Should_Be_Greater_Than_4_Characters() {
        assertThrows(IllegalArgumentException.class, () -> new Library("Dris"));
    }

    @Test
    public void If_User_Is_Null() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addUser(null));
        assertEquals("User should not be null", exception.getMessage());
    }

    @Test
    public void Allow_Only_Permitted_User_To_AddBook() {
        User user = new User("Bhavesh", User.Role.LIBRARIAN);

        Book book = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));
        library.addBook(user, book);

        Book storedBook = library.getBookByISBN("9780132350884");

        assertNotNull(storedBook);
        assertEquals(book, storedBook);
    }

    @Test
    public void If_Unauthorized_User_AddBook() {
        User user = new User("Bhavesh", User.Role.USER);

        Book book = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));
        PermissionDeniedException exception = assertThrows(PermissionDeniedException.class, () -> library.addBook(user, book));
        assertEquals("You are not authorized to add book", exception.getMessage());
    }

    @Test
    public void Add_User_ToLibrary() {
        User librarian = new User("Bhavesh", User.Role.LIBRARIAN);

        library.addUser(librarian);

        User user = library.getUserByName("Bhavesh");
        assertEquals(librarian, user);
    }

    @Test
    public void Not_Allow_DuplicateUsers() {
        User primaryLibrarian = new User("Bhavesh", User.Role.LIBRARIAN);
        User secondaryLibrarian = new User("Bhavesh", User.Role.LIBRARIAN);

        library.addUser(primaryLibrarian);
        UserExistException exception = assertThrows(UserExistException.class, () -> library.addUser(secondaryLibrarian));
        assertEquals("User already exists in catalog", exception.getMessage());
    }

    @Test
    public void Fetch_User_By_Username() {
        User primaryLibrarian = new User("Bhavesh", User.Role.LIBRARIAN);
        library.addUser(primaryLibrarian);
        User fetchedUser = library.getUserByName("Bhavesh");
        assertEquals(primaryLibrarian, fetchedUser);
    }

    @Test
    public void Retrieve_All_Available_Books(){
        User librarian = new User("Bhavesh", User.Role.LIBRARIAN);
        Book book1 = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));
        Book book2 = new Book("9780134685991", "Effective Java", "Joshua Bloch", Year.of(2018));

        library.addUser(librarian);
        library.addBook(librarian, book1);
        library.addBook(librarian, book2);

        Map<String, Book> availableBooks = library.viewAvailableBooks();

        assertEquals(2, availableBooks.size());
        assertTrue(availableBooks.containsKey("9780132350884"));
        assertTrue(availableBooks.containsKey("9780134685991"));
    }

    @Test
    public void Allow_To_Borrow_Book_From_Library() {
        User librarian = new User("Bhavesh", User.Role.LIBRARIAN);
        User user = new User("harsh", User.Role.USER);
        Book book = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));

        library.addUser(librarian);
        library.addUser(user);
        library.addBook(librarian, book);

        library.borrowBook(user, "9780132350884");

        Book borrowedBook = library.getBookByISBN("9780132350884");
        assertNull(borrowedBook, "borrowedBook should be null as it has been borrowed earlier.");
    }

    @Test
    public void When_Book_Not_Found_During_Borrow_Request() {

        User user = new User("Bhavesh", User.Role.USER);

        library.addUser(user);

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> library.borrowBook(user, "9780132350884"));
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void When_Book_Is_Already_Borrowed() {

        User librarian = new User("Bhavesh", User.Role.LIBRARIAN);
        User user1 = new User("harsh", User.Role.USER);
        User user2 = new User("dev", User.Role.USER);
        Book book = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));

        library.addUser(librarian);
        library.addUser(user1);
        library.addUser(user2);
        library.addBook(librarian, book);

        library.borrowBook(user1, "9780132350884");

        BookAlreadyBorrowdException exception = assertThrows(BookAlreadyBorrowdException.class, () -> library.borrowBook(user2, "9780132350884"));
        assertEquals("Book is already borrowed", exception.getMessage());
    }

    @Test
    public void Return_Borrower_Name_Who_Borrowed_Book() {
        User librarian = new User("Bhavesh", User.Role.LIBRARIAN);
        User user = new User("harsh", User.Role.USER);
        Book book = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));

        library.addUser(librarian);
        library.addUser(user);
        library.addBook(librarian, book);

        library.borrowBook(user, "9780132350884");

        String borrowerName = library.getBorrowerNameByISBN("9780132350884");

        assertEquals(user.getUserName(), borrowerName);
    }

    @Test
    public void Allow_User_To_Return_Book_To_Library() {
        User librarian = new User("Bhavesh", User.Role.LIBRARIAN);
        User user = new User("harsh", User.Role.USER);
        Book book = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));

        library.addUser(librarian);
        library.addUser(user);
        library.addBook(librarian, book);

        library.borrowBook(user, "9780132350884");
        library.returnBook(user, "9780132350884");

        Book returnedBook = library.getBookByISBN("9780132350884");
        assertNotNull(returnedBook, "Returned book have be available in the books catalog.");
    }

    @Test
    public void When_User_Returns_Book_That_IsNot_Borrowed_ByHim() {
        User librarian = new User("Bhavesh", User.Role.LIBRARIAN);
        User user1 = new User("harsh", User.Role.USER);
        User user2 = new User("dev", User.Role.USER);
        Book book = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));

        library.addUser(librarian);
        library.addUser(user1);
        library.addUser(user2);
        library.addBook(librarian, book);

        library.borrowBook(user1, "9780132350884");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.returnBook(user2, "9780132350884"));
        assertEquals("book was not borrowed by this user", exception.getMessage());
    }

    @Test
    public void When_No_One_Borrowed_Book() {
        User librarian = new User("Bhavesh", User.Role.LIBRARIAN);
        User user1 = new User("harsh", User.Role.USER);
        Book book = new Book("9780132350884", "Clean Code", "Robert Cecil Martin", Year.of(2012));

        library.addUser(librarian);
        library.addUser(user1);
        library.addBook(librarian, book);

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> library.returnBook(user1, "9780132350884"));
        assertEquals("Book was not borrowed by any user", exception.getMessage());
    }
}
