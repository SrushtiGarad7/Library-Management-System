# Library Management System 

A lightweight, console-based Library Management System designed specifically for beginners to understand core Java programming and Object-Oriented Programming (OOP) concepts.

This project saves book data permanently in a text file (`books.txt`), meaning your additions and changes won't be lost when you close the application.

---

## Project Structure

The project contains only three files, making it extremely easy to navigate:
```text
java-project/
├── books.txt                # Plain text database storing your book list
├── README.md                # This documentation file
├── run.bat                  # Double-click script to compile and run the system
└── src/
    └── LibrarySystem.java   # The single file containing all classes, logic, and file I/O
```

---

## Core Java Concepts Used in this Project

This project serves as an educational tool to learn:

1. **Classes and Objects**:
   - The `Book` class is a template representing real books.
   - We construct book objects using the `new` keyword.

2. **Encapsulation**:
   - Variables like `id`, `title`, and `author` are marked as `private` to protect them from direct modification outside the class.
   - We use public `getters` and `setters` to read or modify them safely.

3. **Constructors**:
   - The `public Book(...)` constructor initializes the fields when a book is created.

4. **Dynamic Collections (`ArrayList`)**:
   - A standard Java `ArrayList<Book>` is used to hold books. Unlike typical arrays, an `ArrayList` dynamically resizes itself as books are added or deleted.

5. **File I/O & Exception Handling**:
   - `java.io.File`, `FileWriter`, and `PrintWriter` handle reading and writing database records to `books.txt`.
   - `try-catch` blocks handle potential I/O errors (exceptions) gracefully without crashing the application.

6. **Console Loops & Scanner**:
   - A `while (running)` loop keeps the program active.
   - A `switch-case` block processes user input collected via a `Scanner`.

---

## How to Run

### Windows (Quick Method)
1. Navigate to the project folder (`java project`).
2. Double-click **`run.bat`**.

### Manual Command Line (Any OS)
1. Open your terminal or Command Prompt.
2. Go to the project root directory.
3. Compile the Java file:
   ```cmd
   javac -d bin src/LibrarySystem.java
   ```
4. Run the program:
   ```cmd
   java -cp bin LibrarySystem
   ```

---

## How the Database File Works

On the very first launch, the program automatically generates a file named `books.txt` containing default books:
```text
101,Java Programming,James Gosling,false
102,Clean Code,Robert Martin,false
103,Head First Java,Kathy Sierra,false
```
* Each row is structured as: `ID,Title,Author,IsBorrowed`.
* When you add or borrow a book, the program modifies the database file immediately.
