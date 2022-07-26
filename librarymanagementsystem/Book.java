/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author mouli011
 */

enum Genre
{
    ACTION(1),
    DRAMA(2),
    SCIENCEFICTION(3),
    ROMANCE(4),
    CRIME(5),
    THRILLER(6),
    HORROR(7),
    DOCUMENTARY(8),
    NOVEL(9),
    HISTORY(10),
    OTHER(11);

    private int genreValue;

    Genre(int genreValue) {
        this.genreValue = genreValue;
    }

    int getGenreValue() {
        return genreValue;
    }

}

public class Book {

    Genre genre = Genre.ACTION;

    static int bookCount = 0;

    ArrayList<Book> bookCopies = new ArrayList<Book>();
    ArrayList<Book> toBeDeletedBooks = new ArrayList<Book>();

    int bookNo;
    String bookName;
    static int bookIDReference = 1;
    int bookID;//unique for every books
    String author;
    String publishedIn;
    int availableCopies;
    int genreReference;
    //String genre;
    boolean isAvailable;
    Date borrowedDate;
    Date toBeReturnedDate;
    int totalCopies;
    double bookPrice;
    String bookLocation = "";
    int borrowedCopies = 0;
    int nearestBookID;

    Book() {

    }

    public void copyBookInfoToBorrowedBook(Book bookCopy) {

        this.bookNo = bookCopy.bookNo;
        this.bookName = bookCopy.bookName;
        this.bookID = bookCopy.bookID;
        this.bookPrice = bookCopy.bookPrice;
        this.isAvailable = bookCopy.isAvailable;
        this.bookLocation = bookCopy.bookLocation;
        this.genre = bookCopy.genre;
        this.borrowedDate = bookCopy.borrowedDate;
        this.toBeReturnedDate = bookCopy.toBeReturnedDate;
        this.author = bookCopy.author;
        this.publishedIn = bookCopy.publishedIn;
    }

    public Book addBook(int bookNo) {
        this.bookNo = bookNo;
        System.out.println("Enter Book Name: ");
        bookName = Utils.getString();
        bookName = Utils.checkNGetNotNull(bookName);

        System.out.println("Enter Author: ");
        author = Utils.getString();
        author = Utils.checkNGetNotNull(author);

        System.out.println("Enter Published Year: ");
        publishedIn = Utils.getString();
        publishedIn = Utils.checkNGetNotNull(publishedIn);
        publishedIn = Utils.checkNGetValidYear(publishedIn);
        publishedIn = Utils.checkNGetNotNull(publishedIn);

        System.out.println("Enter Total Copies: ");
        totalCopies = Utils.getInt();
        System.out.println("Enter Genre: ");
        this.printGenre();
        //System.out.println("\t\t1.ACTION\n\t\t2.DRAMA\n\t\t3.SCIENCE FICTION\n\t\t4.ROMANCE\n\t\t5.CRIME\n\t\t6.THRILLER\n\t\t7.HORROR\n\t\t8.DOCUMENTARY\n\t\t9.NOVEL\n\t\t10.HISTORY\n\t\t11.OTHER");
        genreReference = Utils.getInt();
        genre = getGenre(genreReference);
        System.out.println("Enter Book Price: ");
        bookPrice = Utils.getDouble();
        bookID = bookIDReference;
        Book book;
        for (int i = 0; i < totalCopies; i++) {
            book = new Book();
            book.copyContents(bookNo, bookName, author, publishedIn, bookPrice, genre);
            bookCopies.add(book);
        }
        availableCopies = totalCopies;
        System.out.println("Book Added Successfully!!");
        return this;
    }

    public void copyContents(int bookNo, String bookName, String author, String publishedIn, double bookPrice, Genre genre) {
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.author = author;
        this.publishedIn = publishedIn;
        this.bookPrice = bookPrice;
        this.genre = genre;
        bookID = Book.setBookID();
        isAvailable = true;
    }

    public static int setBookID() {
        return bookIDReference++;
    }

    public static void viewBooksOption() {
        Book book = new Book();
        if (!Resources.books.isEmpty()) {
            book.viewBooks();
            int userChoice;
            System.out.println("\n\nEnter Option\n\t\t1.View copies of the book\n\t\t0.Main Page: ");
            userChoice = Utils.getInt();
            if (userChoice == 1) {
                book.viewBookCopies();
            } else if (userChoice != 0 && userChoice != 1) {
                System.out.println("Enter Valid Option!!!");
                new Book().viewBooks();
            }
        } else {
            System.out.println("No Books Added!! Add Some Books to view them!!");
        }
    }

    public void viewBooks() {

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %20s %20s %20S %20s %20s", "BOOK NO", "BOOK NAME", "AUTHOR", "PUBLISHED IN", "GENRE", "TOTAL COPIES", "AVAILABLE COPIES");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        //System.out.println("Book No\t\tBook Name\t\tAuthor\t\tPublisher\t\tTotal Copies\t\tAvailable Copies");
        for (Book book : Resources.books) {
            System.out.format("%5s %20s %20s %20s %22s %15s %15s", book.bookNo, book.bookName, book.author, book.publishedIn, book.genre, book.totalCopies, book.availableCopies);
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            //System.out.println(book.bookNo+"\t\t   "+book.bookName+"\t\t   "+book.author+"\t\t"+book.publishedIn+"\t\t   "+book.totalCopies+"\t\t\t   "+book.availableCopies+"\t\t");
        }

    }

    public void viewBookCopies() {

        int bookNo;
        System.out.println("Enter the Book Number of which you want to enquire: ");
        bookNo = Utils.getInt();
        int bookMatch = 0;
        for (Book book : Resources.books) {
            if (book.bookNo == bookNo && (!book.bookCopies.isEmpty())) {
                bookMatch++;
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%5s %20s %15s %20s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "AVAILABILITY", "BOOK STATUS");
                System.out.println();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
                //System.out.println("Book ID\t\tBook Name\t\tBook No\t\tAuthor\t\tPublisher\t\tAvailability");
                for (Book bookCopy : book.bookCopies) {
                    System.out.printf("%5s %20s %15s %20s %20s %22s %15s %15s", bookCopy.bookID, bookCopy.bookName, bookCopy.bookNo, bookCopy.author, bookCopy.publishedIn, bookCopy.genre, bookCopy.isAvailable, Shelf.getBookPosition(bookCopy.bookID));
                    System.out.println();
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
                    //System.out.println(bookCopy.bookID+"\t\t"+bookCopy.bookName+"\t\t"+bookCopy.bookNo+"\t\t\t"+bookCopy.author+"\t\t"+bookCopy.publishedIn+"\t\t"+bookCopy.isAvailable);
                }
            } else if ((book.bookNo == bookNo) && (book.bookCopies.isEmpty())) {
                bookMatch++;
                System.out.println("No Copy is available for the Book:\n\t\tBook Name:" + book.bookName + "\t\tBook Number:" + book.bookNo);
            }
        }
        if (bookMatch == 0) {
            System.out.println("Enter Valid Book Number!!");
            new Book().viewBookCopies();
        }
    }

    public void displaySearchResult() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %20s %20s %20s", "BOOK NO", "BOOK NAME", "AUTHOR", "PUBLISHED IN", "AVAILABLE COPIES");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        this.displaySingleBook();
    }

    public void displaySingleBook() {
        System.out.format("%5s %20s %20s %20s %20s", bookNo, bookName, author, publishedIn, availableCopies);
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    public Book assignBookCopy(int bookID, Patron patron) {

        final int YES = 1;
        final int NO = 0;

        int bookMatch = 0;
        for (Book book : Resources.books) {
            for (Book bookCopy : book.bookCopies) {
                if ((bookCopy.bookID == bookID) && (!(bookCopy.bookLocation.contains("G")))) {
                    bookMatch++;
                    if (bookCopy.isAvailable == true) {

                        System.out.println("\nConfirm\n\t\t1.YES\n\t\t0.NO\n\n");
                        int userChoice = Utils.getInt();
                        while ((userChoice != YES) && (userChoice != NO)) {
                            System.out.println("Enter Valid Option:\n\n");
                            userChoice = Utils.getInt();
                        }
                        if (userChoice == NO) {
                            System.out.println("Book Not Borrowed!!\n\n");
                            Book returnBook = new Book();
                            returnBook.bookID = -1;
                            return returnBook;
                        } else if (userChoice == YES) {

                            bookCopy.isAvailable = false;
                            bookCopy.setDateLimit();
                            book.availableCopies--;
                            bookCopy.bookLocation = "BORROWED";
                            book.borrowedCopies++;
                            Shelf.removeBookFromShelf(book.bookNo, bookCopy.bookID);
                            return bookCopy;
                        }
                    } else {
                        System.out.println("Sorry!!No Books Available!!");
                    }
                } else if ((bookCopy.bookID == bookID) && ((bookCopy.bookLocation.contains("G")))) {
                    if (patron.booksOnHoldCount <= 1) {
                        System.out.println("Waiting For Librarian's Response!!");
                        patron.booksOnHold.add(bookCopy);
                        bookCopy.isAvailable = false;
                        book.availableCopies--;
                        book.borrowedCopies++;
                        bookCopy.bookLocation = "HOLD";
                        patron.booksOnHoldCount++;
                        Book returnBook = new Book();
                        Resources.totalBooksOnHoldByAllPatrons++;
                        returnBook.bookID = -1;
                        return returnBook;
                    } else {
                        System.out.println("Book Hold List Full!!");
                        Book returnBook = new Book();
                        returnBook.bookID = -1;
                        return returnBook;
                    }
                }
            }
        }
        if (bookMatch == 0) {
            System.out.println("Enter Valid Book ID: ");
            bookID = Utils.getInt();
            Book addedBook = new Book();
            addedBook = addedBook.assignBookCopy(bookID, patron);
            return addedBook;
        }
        Book book = new Book();
        book.bookID = -1;
        return book;
    }

    public void setDateLimit() {

        //System.out.println("Enter the date you Borrow the Book(dd-MM-yyyy)");
        //String dateInput = Utils.getString();
        borrowedDate = Resources.currentDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowedDate);
        calendar.add(Calendar.DATE, Rules.returnDateLimit);
        toBeReturnedDate = calendar.getTime();

    }

    public void returnBookCopy() {

        isAvailable = true;
        toBeReturnedDate = Utils.parseDate("30-12-3000");
        borrowedDate = null;

    }

    public double fineCalculator() {
        Date currentDate = new Date();
        long difference = currentDate.getTime() - toBeReturnedDate.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(difference);
        double fineCalculated;
        if (diffInDays > 0) {
            fineCalculated = diffInDays * Rules.fineFee;
        } else {
            fineCalculated = 0;
        }
        return fineCalculated;
    }

    public void renewBookCopy() {

        Date currentDate = new Date();
        if (currentDate.compareTo(toBeReturnedDate) <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toBeReturnedDate);
            calendar.add(Calendar.DATE, Rules.noOfRenewalDate);
            toBeReturnedDate = calendar.getTime();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE, Rules.noOfRenewalDate);
            toBeReturnedDate = calendar.getTime();
        }
    }

    public static void displayForAddBookCopies(int bookNo) {

        int bookCopied = 0;
        System.out.println("Enter total copies to be added: ");
        int totalCopiesToBeAdded = Utils.getInt();
        totalCopiesToBeAdded = Utils.checkNGetPositiveIntValue(totalCopiesToBeAdded);
        int bookFound = 0;
        for (Book book : Resources.books) {
            if (book.bookNo == bookNo) {
                for (int i = 0; i < totalCopiesToBeAdded; i++) {
                    Book addBook = new Book();
                    addBook.copyContents(book.bookNo, book.bookName, book.author, book.publishedIn, book.bookPrice, book.genre);
                    bookCopied++;
                    book.bookCopies.add(addBook);
                }
                book.totalCopies = book.totalCopies + totalCopiesToBeAdded;
                book.availableCopies = book.availableCopies + totalCopiesToBeAdded;
                bookFound++;
            }
        }
        if ((bookFound != 0) && (bookCopied != 0)) {
            System.out.println("Copies Added Successfully!!");
        } else if ((bookFound != 0) && (bookCopied == 0)) {
            System.out.println("No Copies Added!!");
        }
    }

    public void displaySingleBookCopy() {
        System.out.printf("%5s %20s %15s %20s %20s %20s %20s", bookID, bookName, bookNo, author, publishedIn, genre, Shelf.getBookPosition(bookID));
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void displayForAddBooks() {
        System.out.println("\n\nEnter Book Number: ");
        int bookNo = Utils.getInt();
        int bookFound = 0;
        for (Book book : Resources.books) {
            if (book.bookNo == bookNo) {
                bookFound++;
            }
        }
        if (bookFound == 0) {
            Book bookAdd = new Book();
            bookAdd = bookAdd.addBook(bookNo);
            Resources.books.add(bookAdd);
            Book.bookCount++;
        } else if (bookFound > 0) {
            Book.displayForAddBookCopies(bookNo);
        }
    }

    public static void filterBooks() {
        ArrayList<Integer> nearestBookIDList = new ArrayList<>();
        final int AUTHORNAME = 1;
        final int GENRE = 2;
        final int POPULARITY = 3;
        final int BORROW = 4;
        System.out.println("Enter\n\t\t1.Filter By Author Name\n\t\t2.Filter by Genre\n\t\t3.Filter By Popularity\n\t\t4.Borrow");
        int userChoice = Utils.getInt();
        switch (userChoice) {
            case AUTHORNAME:
                nearestBookIDList = (ArrayList) Book.filterByAuthorName().clone();
                break;
            case GENRE:
                nearestBookIDList = (ArrayList) Book.filterByGenre().clone();
                break;
            case POPULARITY:
                nearestBookIDList = (ArrayList) Book.filterByPopularity().clone();
                break;
            case BORROW:
                break;
            default:
                System.out.println("Enter Valid Option!!");
                break;
        }
        if (nearestBookIDList.isEmpty()) {
            new Patron().displayFirstAvailableBook().clone();
        }
    }

    public static ArrayList<Integer> filterByAuthorName() {

        int bookFound = 0;
        int nearestBookID;
        ArrayList<Integer> nearestBookIDList = new ArrayList<>();
        System.out.println("Enter Author Name: ");
        String author = Utils.getString();
        for (Book book : Resources.books) {
            for (Book bookCopy : book.bookCopies) {
                if (((book.author.replaceAll("\\s+", "").toLowerCase()).contains(author.replaceAll("\\s+", "").toLowerCase()))) {
                    nearestBookID = Shelf.getFirstAvailableBook(book);
                    if ((bookFound == 0) && (bookCopy.bookID == nearestBookID)) {
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
                        System.out.println();
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                        bookCopy.displaySingleBookCopy();
                        nearestBookIDList.add(nearestBookID);
                        bookFound++;
                    } else if ((bookFound > 0) && (bookCopy.bookID == nearestBookID)) {
                        bookCopy.displaySingleBookCopy();
                        nearestBookIDList.add(nearestBookID);
                    } else if ((nearestBookID == -1) && (bookCopy.isAvailable == true)) {
                        bookCopy.displaySingleBookCopy();
                        nearestBookIDList.add(bookCopy.bookID);
                        break;
                    }
                }
            }
        }
        if (bookFound == 0) {
            System.out.println("No Authors Found!!");
        }
        return nearestBookIDList;

    }

    public static ArrayList<Integer> filterByGenre() {

        ArrayList<Integer> nearestBookIDList = new ArrayList<>();
        int bookFound = 0;
        int nearestBookID;
        Genre userGenreChoice = Genre.ACTION;
        System.out.println("Select the Appropriate Genre: ");
        new Book().printGenre();
        System.out.println("Enter Option: ");
        int genreReference = Utils.getInt();
        userGenreChoice = new Book().getGenre(genreReference);
        //String genre = Utils.assignGenre(genreReference);
        for (Book book : Resources.books) {
            for (Book bookCopy : book.bookCopies) {
                //if(((book.genre.replaceAll("\\s+","").toLowerCase()).contains(genre.replaceAll("\\s+","").toLowerCase()))) 
                nearestBookID = Shelf.getFirstAvailableBook(book);
                if ((book.genre == userGenreChoice)) {

                    if ((bookFound == 0) && (nearestBookID == bookCopy.bookID)) {
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
                        System.out.println();
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                        bookCopy.displaySingleBookCopy();
                        nearestBookIDList.add(nearestBookID);
                        bookFound++;
                    } else if ((bookFound > 0) && (nearestBookID == bookCopy.bookID)) {
                        bookCopy.displaySingleBookCopy();
                        nearestBookIDList.add(nearestBookID);
                    } else if ((nearestBookID == -1) && (bookCopy.isAvailable == true)) {
                        bookCopy.displaySingleBookCopy();
                        nearestBookIDList.add(bookCopy.bookID);
                        break;
                    }
                }
            }
        }
        if (bookFound == 0) {
            System.out.println("No Books Found in this Genre!!");
        }
        return nearestBookIDList;
    }

    public static ArrayList<Integer> filterByPopularity() {

        int nearestBookID;
        ArrayList<Integer> nearestBookIDList = new ArrayList<>();
        Collections.sort(Resources.books, new BookPopularityComparator());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        for (Book book : Resources.books) {
            if (book.availableCopies > 0) {
                nearestBookID = Shelf.getFirstAvailableBook(book);
                for (Book bookCopy : book.bookCopies) {
                    if (bookCopy.bookID == nearestBookID) {
                        bookCopy.displaySingleBookCopy();
                        nearestBookIDList.add(nearestBookID);
                    } else if ((nearestBookID == -1) && (bookCopy.isAvailable == true)) {
                        bookCopy.displaySingleBookCopy();
                        nearestBookIDList.add(bookCopy.bookID);
                        break;
                    }
                }
            }
        }
        Collections.sort(Resources.books, new BookIDComparator());
        return nearestBookIDList;
    }

    public static ArrayList<Integer> filterByBookName() {

        int bookFound = 0;
        int nearestBookID;
        ArrayList<Integer> nearestBookIDList = new ArrayList<>();
        System.out.println("Enter Book Name: ");
        String bookName = Utils.getString();
        for (Book book : Resources.books) {
            if (book.availableCopies > 0) {
                nearestBookID = Shelf.getFirstAvailableBook(book);
                for (Book bookCopy : book.bookCopies) {
                    if (((book.bookName.replaceAll("\\s+", "").toLowerCase()).contains(bookName.replaceAll("\\s+", "").toLowerCase()))) {
                        if ((bookFound == 0) && (bookCopy.bookID == nearestBookID)) {
                            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
                            System.out.println();
                            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                            bookCopy.displaySingleBookCopy();
                            nearestBookIDList.add(nearestBookID);
                            bookFound++;
                        } else if ((bookFound > 0) && (bookCopy.bookID == nearestBookID)) {
                            bookCopy.displaySingleBookCopy();
                        } else if ((nearestBookID == -1) && (bookCopy.isAvailable == true)) {
                            bookCopy.displaySingleBookCopy();
                            nearestBookIDList.add(bookCopy.bookID);
                            break;
                        }
                    }
                }
            }
        }
        return nearestBookIDList;
    }

    public Genre getGenre(int value) {

        for (Genre g : Genre.values()) {
            if (g.getGenreValue() == value) {
                return g;
            }
        }
        System.out.println("Enter Valid Options!!");
        value = Utils.getInt();
        return getGenre(value);
    }

    public void printGenre() {

        for (Genre genre : Genre.values()) {
            System.out.println("\t\t" + genre.getGenreValue() + "." + genre);
        }
    }
}
