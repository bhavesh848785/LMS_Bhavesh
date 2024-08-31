package com.bhavesh;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import com.bhavesh.exception.UserExistException;
import com.bhavesh.exception.BookNotFoundException;
import com.bhavesh.exception.PermissionDeniedException;
import com.bhavesh.exception.BookAlreadyBorrowdException;


import static com.bhavesh.utils.UserValidator.validateUser;
import static com.bhavesh.utils.StringValidator.validateString;
import static com.bhavesh.utils.BookValidator.validateBookNotNull;

public class Library {
    String name;
    private final Map<String, Book> bookInventory;
    private final Map<String, User> userCatalog;
    private final Map<String, String> borrowedBooks;
    private final Map<String, Book> borrowedBookDetails;

    public Library(String name) {
        validateString(name, "Library Name Should not be null or empty");
        if(name.length() <= 4) {
            throw new IllegalArgumentException("Library Name Should have at least 4 characters");
        }
        this.name = name;
        this.bookInventory = new HashMap<String, Book>();
        this.userCatalog = new HashMap<String, User>();
        this.borrowedBooks = new HashMap<String, String>();
        this.borrowedBookDetails = new HashMap<String, Book>();
    }

    public void addUser(User user) {
        validateUser(user, "User should not be null");
        if(userCatalog.containsKey(user.getUserName())){
            throw new UserExistException("User already exists in catalog");
        }
        userCatalog.put(user.getUserName(), user);
    }

    public User getUserByName(String userName) {
        return userCatalog.get(userName);
    }

    public void addBook(User user, Book book) {
        validateUser(user, "User should not be null");
        validateBookNotNull(book,"Book not found");
        if(user.isPermittedToAddBook()){
            bookInventory.put(book.getISBN(), book);
        } else {
            throw new PermissionDeniedException("You are not authorized to add book");
        }
    }

    private boolean isBookBorrowedBySomeUser(String isbn) {
        return borrowedBooks.containsKey(isbn);
    }

    public void borrowBook(User user, String isbn) {
        validateUser(user, "User should not be null");
        Book book = bookInventory.get(isbn);

        if(isBookBorrowedBySomeUser(isbn)) {
            throw new BookAlreadyBorrowdException("Book is already borrowed");
        }

        validateBookNotNull(book,"Book not found");

        borrowedBooks.put(isbn, user.getUserName());
        borrowedBookDetails.put(isbn, book);
        bookInventory.remove(isbn);
    }

    public void returnBook(User user, String isbn) {
        validateUser(user, "User should not be null");
        if(!borrowedBooks.containsKey(isbn)) {
            throw new BookNotFoundException("Book was not borrowed by any user");
        }
        if( !user.getUserName().equals(borrowedBooks.get(isbn))){
            throw new IllegalArgumentException("book was not borrowed by this user");
        }
        Book book = getBookByISBNFromBorrowedBook(isbn);
        bookInventory.put(isbn, book);
        borrowedBooks.remove(isbn);
    }

    public String getBorrowerNameByISBN(String isbn) {
        return borrowedBooks.get(isbn);
    }

    public Map<String, Book> viewAvailableBooks() {
        return Collections.unmodifiableMap(new HashMap<>(bookInventory));
    }

    public Book getBookByISBNFromBorrowedBook(String isbn) {
        return borrowedBookDetails.get(isbn);
    }

    public Book getBookByISBN(String isbn) {
        return bookInventory.get(isbn);
    }
}
