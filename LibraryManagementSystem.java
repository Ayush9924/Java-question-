import java.util.*;

/**
 * Library Management System - Java OOP + Collections
 */
public class LibraryManagementSystem {

    public static void main(String[] args) {
        Library library = new Library();

        // Members
        Member member = new Member(101, "Rahul");
        StudentMember student = new StudentMember(102, "Ayush");

        // Add books
        library.addBook(new Book(1, "Java Basics", "James Gosling"));
        library.addBook(new Book(2, "OOP Concepts", "Bjarne Stroustrup"));
        library.addBook(new Book(3, "Data Structures", "Mark Allen"));

        // Display books
        library.displayAllBooks();

        // Issue books
        library.issueBook(member.getMemberId(), 1);
        library.issueBook(student.getMemberId(), 2);

        // Display books after issue
        library.displayAllBooks();

        // Return a book
        library.returnBook(member.getMemberId(), 1);

        // Display books after return
        library.displayAllBooks();

        // Search (polymorphism via method overloading)
        library.searchBook("Java Basics");
        library.searchBook("Mark Allen", true); // search by author

        // Polymorphism via overriding
        System.out.println("\nFine for Member (5 late days): " + member.calculateFine(5));
        System.out.println("Fine for StudentMember (5 late days): " + student.calculateFine(5));
    }
}

class Member {
    private int memberId;
    private String name;

    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

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

    public double calculateFine(int lateDays) {
        return lateDays * 5.0;
    }
}

class StudentMember extends Member {
    public StudentMember(int memberId, String name) {
        super(memberId, name);
    }

    @Override
    public double calculateFine(int lateDays) {
        return lateDays * 2.0;
    }
}

class Library {
    // Collections Framework usage
    private final ArrayList<Book> books;
    private final HashMap<Integer, List<Book>> issuedBooks;

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

        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Issue a book to a member
    public void issueBook(int memberId, int bookId) {
        Book bookToIssue = findBookById(bookId);

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
        List<Book> memberIssuedBooks = issuedBooks.get(memberId);

        if (memberIssuedBooks == null || memberIssuedBooks.isEmpty()) {
            System.out.println("No books issued to this member.");
            return;
        }

        Book returnBook = null;
        for (Book book : memberIssuedBooks) {
            if (book.getBookId() == bookId) {
                returnBook = book;
                break;
            }
        }

        if (returnBook == null) {
            System.out.println("This book was not issued to member.");
            return;
        }

        returnBook.setAvailable(true);
        memberIssuedBooks.remove(returnBook);
        System.out.println("Book returned successfully.");
    }

    // Search for a book by title
    public void searchBook(String title) {
        System.out.println("\nSearch by Title: " + title);
        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching book found.");
        }
    }

    // Polymorphism via method overloading: search by author
    public void searchBook(String author, boolean byAuthor) {
        if (!byAuthor) {
            searchBook(author);
            return;
        }

        System.out.println("\nSearch by Author: " + author);
        boolean found = false;

        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching book found.");
        }
    }

    private Book findBookById(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }
}