import java.util.*;

// 1) Member class
class Member {
    private int memberId;
    private String name;

    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    // Getters and Setters (Encapsulation)
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Polymorphism (method to be overridden)
    public double calculateFine(int lateDays) {
        return lateDays * 5.0; // default fine
    }
}

// 4) Inheritance: StudentMember extends Member
class StudentMember extends Member {
    public StudentMember(int memberId, String name) {
        super(memberId, name);
    }

    // 4) Polymorphism (overriding)
    @Override
    public double calculateFine(int lateDays) {
        return lateDays * 2.0; // lower fine for students
    }
}

// 3) Library class
class Library {
    // 2) Collections
    private ArrayList<Book> books; // store all books
    private HashMap<Integer, List<Book>> issuedBooks; // memberId -> list of books issued

    public Library() {
        books = new ArrayList<>();
        issuedBooks = new HashMap<>();
    }

    // Add a book
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    // Display all books
    public void displayAllBooks() {
        System.out.println("\n--- All Books ---");
        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }

    // Issue a book to a member
    public void issueBook(int memberId, int bookId) {
        Book bookToIssue = null;
        for (Book b : books) {
            if (b.getBookId() == bookId) {
                bookToIssue = b;
                break;
            }
        }

        if (bookToIssue == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!bookToIssue.isAvailable()) {
            System.out.println("Book is already issued.");
            return;
        }

        bookToIssue.setAvailable(false);
        issuedBooks.putIfAbsent(memberId, new ArrayList<>());
        issuedBooks.get(memberId).add(bookToIssue);

        System.out.println("Book issued successfully to Member ID: " + memberId);
    }

    // Return a book
    public void returnBook(int memberId, int bookId) {
        List<Book> memberBooks = issuedBooks.get(memberId);

        if (memberBooks == null || memberBooks.isEmpty()) {
            System.out.println("No books issued to this member.");
            return;
        }

        Book returnBook = null;
        for (Book b : memberBooks) {
            if (b.getBookId() == bookId) {
                returnBook = b;
                break;
            }
        }

        if (returnBook == null) {
            System.out.println("This book was not issued to member.");
            return;
        }

        returnBook.setAvailable(true);
        memberBooks.remove(returnBook);
        System.out.println("Book returned successfully.");
    }

    // Search for a book by title (Polymorphism: overloading)
    public void searchBook(String title) {
        System.out.println("\nSearch by Title: " + title);
        boolean found = false;
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching book found.");
        }
    }

    // Search for a book by author
    public void searchBookByAuthor(String author) {
        System.out.println("\nSearch by Author: " + author);
        boolean found = false;
        for (Book b : books) {
            if (b.getAuthor().equalsIgnoreCase(author)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching book found.");
        }
    }
}

// Main class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Members
        Member m1 = new Member(101, "Rahul");
        StudentMember s1 = new StudentMember(102, "Ayush");

        // Add books
        library.addBook(new Book(1, "Java Basics", "James Gosling"));
        library.addBook(new Book(2, "OOP Concepts", "Bjarne Stroustrup"));
        library.addBook(new Book(3, "Data Structures", "Mark Allen"));

        // Display books
        library.displayAllBooks();

        // Issue book
        library.issueBook(m1.getMemberId(), 1);
        library.issueBook(s1.getMemberId(), 2);

        // Display after issue
        library.displayAllBooks();

        // Return book
        library.returnBook(m1.getMemberId(), 1);

        // Display after return
        library.displayAllBooks();

        // Search
        library.searchBook("Java Basics");
        library.searchBookByAuthor("Mark Allen");

        // Polymorphism demonstration (overriding)
        System.out.println("\nFine for Member (5 late days): " + m1.calculateFine(5));
        System.out.println("Fine for StudentMember (5 late days): " + s1.calculateFine(5));
    }
}
