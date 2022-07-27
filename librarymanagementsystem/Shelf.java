/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import java.util.*;
/**
 *
 * @author mouli011
 */
public abstract class Shelf{

    final static int TOTALSHELF = Genre.values().length + 1;

    static int[][] allShelfs = new int[(TOTALSHELF)][Rules.maximumBookInAShelf];
    static ArrayList<Integer> garage = new ArrayList<>();

    public static void allotLocationForBookWRTGenre(Book bookToBeAllotted) {
        int shelfAllotted = 0;
        int rows;

        shelfAllotted = Shelf.setShelf(bookToBeAllotted, bookToBeAllotted.genre.getGenreValue() - 1, shelfAllotted);

        if (shelfAllotted == 0) {
            shelfAllotted = Shelf.setShelf(bookToBeAllotted, 11, shelfAllotted);
            if (shelfAllotted == 0) {
                bookToBeAllotted.bookLocation = Constants.GODOWN;
            }
        }
    }

    public static void allotLocationForBooks() {
        for (Book book : Resources.books) {
            for (Book bookCopy : book.bookCopies) {
                if (((bookCopy.bookLocation.equals("")) || (bookCopy.bookLocation.equals(Constants.GODOWN))) && (book.booksInShelfCount == 0)) {
                    Shelf.allotLocationForBookWRTGenre(bookCopy);
                    book.booksInShelfCount++;
                } else if ((bookCopy.bookLocation.equals("")) && (book.booksInShelfCount != 0)) {
                    bookCopy.bookLocation = Constants.GODOWN;
                }
            }
        }
    }

    public static String getBookPosition(int bookID) {

        for (int rows = 0; rows < (TOTALSHELF); rows++) {
            for (int columns = 0; columns < Rules.maximumBookInAShelf; columns++) {
                if (bookID == allShelfs[rows][columns]) {
                    return "S" + (rows + 1) + " - B" + (columns + 1);
                }
            }
        }

        for (Book book : Resources.books) {
            for (Book bookCopy : book.bookCopies) {
                if (bookCopy.bookID == bookID) {
                    return bookCopy.bookLocation;
                }
            }
        }

        return Constants.GODOWN;
    }

    public static void removeBookFromShelf(int bookNo, int bookID) {
        for (int rows = 0; rows < (TOTALSHELF); rows++) {
            for (int columns = 0; columns < Rules.maximumBookInAShelf; columns++) {
                if (bookID == allShelfs[rows][columns]) {
                    allShelfs[rows][columns] = 0;
                    Shelf.relocateBooksToShelf(bookNo);
                }
            }
        }
    }

    public static void relocateBooksToShelf(int bookNo) {

        for (Book book : Resources.books) {
            for (Book bookCopy : book.bookCopies) {

                if ((((bookCopy.bookLocation.equals(Constants.GODOWN)) || (bookCopy.bookLocation.equals("")))) && (book.booksInShelfCount == 0)) {
                    Shelf.allotLocationForBookWRTGenre(bookCopy);
                    book.booksInShelfCount++;
                    if (bookCopy.bookLocation.equals(Constants.SHELF)) {
                        garage.remove(Integer.valueOf(bookCopy.bookID));
                    }
                }
            }
        }
    }

    public static void removeBookCopyFromShelf(int bookID) {
        for (int rows = 0; rows < (TOTALSHELF); rows++) {
            for (int columns = 0; columns < Rules.maximumBookInAShelf; columns++) {
                if (bookID == allShelfs[rows][columns]) {
                    allShelfs[rows][columns] = 0;
                }
            }
        }
    }

    public static void removeBooksFromShelfForDeletion(int bookNo) {

        for (Book book : Resources.books) {
            if (book.bookNo == bookNo) {
                for (Book bookCopy : book.bookCopies) {
                    for (int rows = 0; rows < (TOTALSHELF); rows++) {
                        for (int columns = 0; columns < Rules.maximumBookInAShelf; columns++) {
                            if (bookCopy.bookID == allShelfs[rows][columns]) {
                                allShelfs[rows][columns] = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    public static int setShelf(Book bookToBeAllotted, int shelf, int shelfAllotted) {
        for (int columns = 0; columns < Rules.maximumBookInAShelf; columns++) {
            if (allShelfs[shelf][columns] == 0) {
                allShelfs[shelf][columns] = bookToBeAllotted.bookID;
                shelfAllotted++;
                bookToBeAllotted.bookLocation = Constants.SHELF;
                return shelfAllotted;
            }
        }
        return shelfAllotted;
    }

    public static int getFirstAvailableBook(Book book) {
        for (int shelf = 0; shelf < (TOTALSHELF); shelf++) {
            for (int bookPosition = 0; bookPosition < Rules.maximumBookInAShelf; bookPosition++) {
                for (Book bookCopy : book.bookCopies) {
                    if ((allShelfs[shelf][bookPosition] == bookCopy.bookID) && (bookCopy.isAvailable == true)) {
                        return bookCopy.bookID;
                    }
                }
            }
        }
        return -1;
    }

}
