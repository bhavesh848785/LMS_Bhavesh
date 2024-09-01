# LMS_Bhavesh

If you used IntelliJ IDEA Community Edition for your project, here’s an updated version of the `README.md` to include setup instructions specific to IntelliJ IDEA:


## Overview

The Library Management System is a Java application that allows users to perform basic library operations, such as adding books, borrowing books, returning books, and viewing available books. The project is developed following the principles of Test-Driven Development (TDD) to ensure the reliability and maintainability of the codebase.

## Features

- **Add Books**: Users can add new books to the library, each with a unique ISBN, title, author, and publication year.
- **Borrow Books**: Users can borrow books from the library, provided they are available.
- **Return Books**: Users can return borrowed books, updating their availability in the library.
- **View Available Books**: Users can view a list of all available books in the library.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- IntelliJ IDEA Community Edition
- Git

## Setup Instructions

### Clone the Repository

1. **Clone the Repository**
   ```sh
   git clone <repository-url>
   cd library-management-system
   ```

### Import the Project into IntelliJ IDEA

1. **Open IntelliJ IDEA** and select `File > New > Project from Existing Sources...`.
2. Navigate to the cloned project directory and select the `pom.xml` file to import the Maven project.
3. Follow the prompts to complete the project setup.

### Build the Project

1. **Build the Project** by selecting `Build > Build Project` from the top menu in IntelliJ IDEA.

### Run the Tests

1. **Run the Tests**:
    - In IntelliJ IDEA, right-click the `src/test/java` directory or any specific test class (like `BookTest` or `LibraryTest`) and select `Run 'All Tests'` or `Run 'ClassName'`.

## Test-Driven Development (TDD)

This project follows TDD practices. The tests are written before the implementation, ensuring that all functionalities are well-tested. Below are the key test cases implemented:

### BookTest

- **testBookCreation**: Ensures that a book can be created with a valid ISBN, title, author, and publication year.
- **testInvalidISBN**: Checks that an invalid ISBN raises an appropriate exception.
- **testTitleValidation**: Verifies that the title of the book is not empty or null.
- **testAuthorValidation**: Ensures that the author's name is valid (not empty or null).
- **testPublicationYearValidation**: Validates that the publication year is a positive integer.

### LibraryTest

- **testAddBook**: Tests the addition of a new book to the library's collection.
- **testBorrowBook**: Ensures that a book can be borrowed if it is available, and raises an error if it is not.
- **testReturnBook**: Verifies that a borrowed book can be returned and is marked as available again.
- **testViewAvailableBooks**: Tests that the system correctly lists all available books in the library.

## Exception Handling

The system includes robust exception handling to manage scenarios such as:

- Attempting to borrow a book that is not available.
- Trying to add a book with invalid attributes (e.g., invalid ISBN, empty title).

