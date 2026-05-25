import java.util.ArrayList;
import java.util.Scanner;
// New imports for handling Files and File Errors (Exceptions)
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * ==========================================
 * CONCEPT 1: CLASSES AND OBJECTS
 * ==========================================
 * In Object-Oriented Programming (OOP), a Class is a blueprint. 
 * An Object is an instance of that blueprint.
 * 
 * Here, the "Book" class represents what a Book is and does.
 */
class Book {
    // Fields (Attributes) of a book
    private String id;
    private String title;
    private String author;
    private boolean isBorrowed; // true if borrowed, false if available

    /**
     * ==========================================
     * CONCEPT 2: CONSTRUCTORS
     * ==========================================
     * A constructor is a special method used to initialize objects.
     * It has the same name as the class and runs when we use the "new" keyword.
     */
    public Book(String id, String title, String author, boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = isBorrowed;
    }

    /**
     * ==========================================
     * CONCEPT 3: ENCAPSULATION (Getters and Setters)
     * ==========================================
     * Encapsulation is hiding internal data and exposing it only through methods.
     * We declare variables as "private" so they can't be modified directly from outside,
     * and use "getters" (to read) and "setters" (to update) them.
     */
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }
}

/**
 * ==========================================
 * CONCEPT 4: MAIN SYSTEM CLASS
 * ==========================================
 * This class contains the logic of our library (adding, viewing, borrowing books)
 * and the user menu.
 */
public class LibrarySystem {

    /**
     * ==========================================
     * CONCEPT 5: DYNAMIC ARRAYS (ArrayList)
     * ==========================================
     * An ArrayList is a resizable array. Standard arrays have a fixed size,
     * but an ArrayList automatically grows when we add elements.
     * We use it here to store all the Book objects in our library.
     */
    private static ArrayList<Book> library = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    // The name of the file where book data will be stored permanently
    private static final String FILE_NAME = "books.txt";

    public static void main(String[] args) {
        // Load existing books from the file on startup
        loadBooksFromFile();

        boolean running = true;

        // Loop keeps running until user chooses option 5 (Exit)
        while (running) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add a Book");
            System.out.println("2. View All Books");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    viewBooks();
                    break;
                case "3":
                    borrowBook();
                    break;
                case "4":
                    returnBook();
                    break;
                case "5":
                    running = false;
                    System.out.println("\nThank you for using the Library System! Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice! Please choose between 1 and 5.");
            }
        }
    }

    /**
     * =======================================================
     * CONCEPT 6: FILE READING & EXCEPTION HANDLING
     * =======================================================
     * This method reads books from a text file named "books.txt".
     * If the file does not exist, it creates the file and populates 
     * it with some default books so the user has data to play with.
     * 
     * We use 'try-catch' to handle potential inputs/output (I/O) errors safely.
     */
    private static void loadBooksFromFile() {
        File file = new File(FILE_NAME);

        // If the file does not exist, write the default startup books
        if (!file.exists()) {
            System.out.println("Database file not found. Creating default books database...");
            seedDefaultBooks();
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue; // skip blank lines

                // Split the comma-separated line back into variables
                String[] data = line.split(",");
                if (data.length == 4) {
                    String id = data[0];
                    String title = data[1];
                    String author = data[2];
                    boolean isBorrowed = Boolean.parseBoolean(data[3]);

                    // Add the loaded book to our ArrayList
                    library.add(new Book(id, title, author, isBorrowed));
                }
            }
            System.out.println("Success: Loaded " + library.size() + " books from permanent database file.");
        } catch (IOException e) {
            System.out.println("Error loading database file: " + e.getMessage());
        }
    }

    /**
     * =======================================================
     * CONCEPT 7: FILE WRITING & PERSISTENCE
     * =======================================================
     * This method writes the entire library list into the text file.
     * It formats book fields as: id,title,author,isBorrowed
     * 
     * We overwrite the file every time a book is added, borrowed, or returned.
     */
    private static void saveBooksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book book : library) {
                // Write book data separated by commas
                writer.println(book.getId() + "," + 
                               book.getTitle() + "," + 
                               book.getAuthor() + "," + 
                               book.isBorrowed());
            }
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    /**
     * Helper method to insert default books and save them to the file.
     */
    private static void seedDefaultBooks() {
        library.add(new Book("101", "Java Programming", "James Gosling", false));
        library.add(new Book("102", "Clean Code", "Robert Martin", false));
        library.add(new Book("103", "Head First Java", "Kathy Sierra", false));
        saveBooksToFile(); // Save these initial books to "books.txt" immediately
    }

    /**
     * METHOD 1: ADD A BOOK
     */
    private static void addBook() {
        System.out.println("\n--- Add a New Book ---");
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine().trim();

        // Check if a book with this ID already exists
        for (Book book : library) {
            if (book.getId().equalsIgnoreCase(id)) {
                System.out.println("Error: A book with this ID already exists!");
                return; // Stop the method
            }
        }

        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine().trim();

        // Check for blank inputs (validation)
        if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
            System.out.println("Error: Fields cannot be empty!");
            return;
        }

        // Create new Book object and add it to the library list
        Book newBook = new Book(id, title, author, false);
        library.add(newBook);
        
        // Save the updated list to books.txt
        saveBooksToFile();
        System.out.println("Success: Book added and saved permanently!");
    }

    /**
     * METHOD 2: VIEW ALL BOOKS
     */
    private static void viewBooks() {
        System.out.println("\n--- Library Catalog ---");
        if (library.isEmpty()) {
            System.out.println("The library is currently empty.");
            return;
        }

        // Loop through each Book object in our library list
        for (Book book : library) {
            String status = book.isBorrowed() ? "Borrowed" : "Available";
            System.out.println("ID: " + book.getId() + 
                               " | Title: " + book.getTitle() + 
                               " | Author: " + book.getAuthor() + 
                               " | Status: " + status);
        }
    }

    /**
     * METHOD 3: BORROW A BOOK
     */
    private static void borrowBook() {
        System.out.println("\n--- Borrow a Book ---");
        System.out.print("Enter Book ID to borrow: ");
        String id = scanner.nextLine().trim();

        // Search for the book in our list
        for (Book book : library) {
            if (book.getId().equalsIgnoreCase(id)) {
                // Check if already borrowed
                if (book.isBorrowed()) {
                    System.out.println("Sorry, this book is already borrowed!");
                } else {
                    book.setBorrowed(true); // Mark it as borrowed in-memory
                    saveBooksToFile();      // Save the change permanently
                    System.out.println("Success: You have borrowed '" + book.getTitle() + "'.");
                }
                return; // Found the book, so stop searching
            }
        }

        // If the loop finished without returning, the book ID does not exist
        System.out.println("Error: Book with ID " + id + " not found.");
    }

    /**
     * METHOD 4: RETURN A BOOK
     */
    private static void returnBook() {
        System.out.println("\n--- Return a Book ---");
        System.out.print("Enter Book ID to return: ");
        String id = scanner.nextLine().trim();

        // Search for the book in our list
        for (Book book : library) {
            if (book.getId().equalsIgnoreCase(id)) {
                // Check if it was actually borrowed
                if (!book.isBorrowed()) {
                    System.out.println("This book is already in the library (not borrowed).");
                } else {
                    book.setBorrowed(false); // Mark it as available in-memory
                    saveBooksToFile();       // Save the change permanently
                    System.out.println("Success: You returned '" + book.getTitle() + "'.");
                }
                return; // Found the book, so stop searching
            }
        }

        // If the loop finished without returning, the book ID does not exist
        System.out.println("Error: Book with ID " + id + " not found.");
    }
}
