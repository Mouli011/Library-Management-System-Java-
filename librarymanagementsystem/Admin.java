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
public class Admin extends User
{

    static int updateRulesCount = 0;

    Admin(String userName, String phoneNumber, String gender, Date dateOfBirth, String password) {

        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        userID = 0;

    }

    Admin() {

    }

    @Override
    public void intialOptionPage() {
        int userChoice = 1;
        int signInSuccessfull;
        Date dateOfBirth = Utils.parseDate("10-10-1990");
        Admin admin = new Admin("Admin1", "9786663130", "Male", dateOfBirth, "pass");
        Resources.admins.add(admin);
        Resources.users.add(admin);
        if ((signInSuccessfull = User.signIn()) >= 0) {
            Admin.listUserFunctions(userChoice);
        }
    }

    public static void listUserFunctions(int userChoice) {

        //constants
        final int VIEWBOOKS = 1;
        final int ADDBOOKS = 2;
        final int VIEWPATRONS = 3;
        final int DELETEBOOKS = 4;
        final int UPDATERULES = 5;
        final int VIEWPAYMENTS = 6;
        final int EXIT = 0;
        final int VIEWBORROWEDBOOK = 7;
        final int LOSTBOOKS = 8;
        final int APPROVEREQUEST = 9;

        while (userChoice != EXIT) {
            System.out.println("\n\nEnter Appropriate Options:");
            if (Resources.totalBooksOnHoldByAllPatrons > 0) {
                System.out.println("\t\t1.View Books\n\t\t2.Add Books\n\t\t3.View Patrons\n\t\t4.Delete Books\n\t\t5.Add/Update Rules\n\t\t6.view Payments\n\t\t7.View Borrowed Books\n\t\t8.View Lost Books\n\t\t9.Approve Request - " + Resources.totalBooksOnHoldByAllPatrons + " Notifications\n\t\t0.Exit");
            } else {
                System.out.println("\t\t1.View Books\n\t\t2.Add Books\n\t\t3.View Patrons\n\t\t4.Delete Books\n\t\t5.Add/Update Rules\n\t\t6.view Payments\n\t\t7.View Borrowed Books\n\t\t8.View Lost Books\n\t\t9.Approve Request\n\t\t0.Exit");
            }
            System.out.println("Enter Option: ");

            userChoice = Utils.getInt();

            switch (userChoice) {
                case VIEWBOOKS:

                    Book.viewBooksOption();
                    break;

                case ADDBOOKS:
                    Book.displayForAddBooks();
                    Shelf.allotLocationForBooks();
                    break;

                case VIEWPATRONS:
                    Admin admin = new Admin();
                    admin.viewPatrons();
                    break;

                case DELETEBOOKS:
                    new Admin().deleteBook();
                    break;

                case UPDATERULES:
                    if (updateRulesCount == 0) {
                        Admin.addRules();
                        updateRulesCount++;
                    } else {
                        Admin.updateRules();
                    }
                    break;

                case VIEWBORROWEDBOOK:
                    Admin.viewBorrowedBooks();
                    break;

                case VIEWPAYMENTS:
                    new Admin().viewPayments();
                    break;

                case LOSTBOOKS:
                    Admin.displayLostBooks();
                    break;

                case APPROVEREQUEST:
                    Admin.bookApproveOption();
                    break;

                case EXIT:
                    break;

                default:
                    System.out.println("Enter Appropriate Options!!");
                    break;
            }
        }
    }

    public static void viewPatrons() {
        if (!Resources.patrons.isEmpty()) {
            System.out.println("------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %20s %15s %15s %20s %15s %15s %15s", "USER ID", "USER NAME", "PHONE NUMBER", "GENDER", "MAIL ID", "DATE OF BIRTH", "BOOKS BORROWED", "FINE");
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------");
            //System.out.println("UserName\t\tPhone Number\t\tGender\t\tDate Of Birth\t\tNumber Of Books Borrowed\t\tFine");
            for (Patron patron : Resources.patrons) {
                System.out.printf("%5s %20s %15s %15s %20s %15s %15s %15s", patron.userID, patron.userName, patron.phoneNumber, patron.gender, patron.mailID, Utils.printDate(patron.dateOfBirth), patron.bookCount, patron.checkTotalFine());
                System.out.println();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------");
                // System.out.println(patron.userID+"\t\t"+patron.phoneNumber+"\t\t"+patron.gender+"\t\t"+patron.dateOfBirth+"\t\t"+patron.bookCount+"\t\t"+patron.fine);
            }
        } else {
            System.out.println("No Patrons Enrolled!!");
        }
    }

    public static void viewBorrowedBooks() {
        if (!Resources.patrons.isEmpty()) {
            System.out.println("Total Number Of Patrons: " + Patron.patronCount);
            for (Patron patron : Resources.patrons) {
                if (patron.bookCount > 0) {

                    System.out.println("Books Borrowed By Patron: " + patron.userID);
                    {
                        patron.displayBorrowedBooks();
                    }
                } else if (patron.bookCount == 0) {
                    System.out.println("No Book is Borrowed by Patron: " + patron.userID);
                }
            }
        } else {
            System.out.println("No Patrons Enrolled!!");
        }
    }

    public static void addRules() {

        Rules.returnDateLimit = Rules.getIntValueForRules("Enter the number of days after which the book must be returned: ");

        Rules.fineFee = Rules.getDoubleValueForRules("Fine Amount for late returning Per Day in Rupees: Rs.");

        Rules.renewalFee = Rules.getDoubleValueForRules("Enter fee for Renewal: ");

        Rules.noOfRenewalDate = Rules.getIntValueForRules("Number of days the book is extend after Renewed: ");

        Rules.maxRenewalCount = Rules.getIntValueForRules("Maximum times a Patron can renew: ");
        /*
        maximumBookInAShelf = Rules.getIntValueForRules("Maximum Books a Shelf can accomadate");*/
    }

    public static void updateRules() {
        //switch case constants
        final int RETURNDATE = 1;
        final int FINEAMOUNT = 2;
        final int RENEWALFEE = 3;
        final int RENEWALDAYS = 4;
        final int RENEWALCOUNT = 5;
        //final int SETSHELFCAPACITY=6;
        final int EXIT = 0;

        int userChoice = 1;
        while (userChoice != EXIT) {
            System.out.println("Enter: ");
            System.out.println("\t\t1.Update Return Date Limit\n\t\t2.Update Fine For Late Submission\n\t\t3.Update Renewal Fee\n\t\t4.Update Renewal days\n\t\t5.Set Maximum Renewal Count\n\t\t0.Previous Page ");
            System.out.println("Enter appropriate option: ");
            userChoice = Utils.getInt();

            switch (userChoice) {

                case RETURNDATE:
                    Rules.returnDateLimit = Rules.getIntValueForRules("Enter the number of days after which the book must be returned: ");
                    break;

                case FINEAMOUNT:
                    Rules.fineFee = Rules.getDoubleValueForRules("Fine Amount for late returning Per Day in Rupees: Rs.");
                    break;

                case RENEWALFEE:
                    Rules.renewalFee = Rules.getDoubleValueForRules("Enter fee for Renewal: ");
                    break;

                case RENEWALDAYS:
                    Rules.noOfRenewalDate = Rules.getIntValueForRules("Number of days the book is extend after Renewed: ");
                    break;

                case RENEWALCOUNT:
                    Rules.maxRenewalCount = Rules.getIntValueForRules("Maximum times a Patron can renew: ");
                    break;

                /*case SETSHELFCAPACITY:
               maximumBookInAShelf = Rules.getIntValueForRules("Maximum Books a Shelf can accomadate");
                break;*/
                case EXIT:
                    break;

                default:
                    System.out.println("Enter Valid Options");
                    break;
            }
        }
    }

    public static void displayLostBooks() {
        if (Resources.lostBookCount != 0) {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %20s %15s %20s %20s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE");
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            //System.out.println("Book ID\t\tBook Name\t\tBook No\t\tAuthor\t\tPublisher\t\tAvailability");
            for (Book bookCopy : Resources.lostBooks) {
                System.out.printf("%5s %20s %15s %20s %20s %22s", bookCopy.bookID, bookCopy.bookName, bookCopy.bookNo, bookCopy.author, bookCopy.publishedIn, bookCopy.genre);
                System.out.println();
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            }
        } else {
            System.out.println("No Books Lost!!");
        }

    }

    private void deleteBook() {

        final int EXIT = 0;
        final int DELETEBOOKCOPY = 1;
        final int DELETEBOOK = 2;
        final int YES = 1;
        final int NO = 0;

        int bookNo = -1;
        int deleteCount = 0;
        int userChoice = 1;
        int confirm;
        while (userChoice != EXIT) {
            if (!Resources.books.isEmpty()) {
                Book book = new Book();
                book.viewBooks();
                System.out.println("\n\nEnter Option\n\t\t1.View copies of the book\n\t\t2.Delete a Whole Book \n\t\t0.Main Page");
                userChoice = Utils.getInt();
                while ((userChoice != EXIT) && (userChoice != DELETEBOOK) && (userChoice != DELETEBOOKCOPY)) {
                    System.out.println("Enter Valid Option!!");
                    userChoice = Utils.getInt();
                }
                if (userChoice == EXIT) {
                    break;
                } else if (userChoice == DELETEBOOK) {
                    System.out.println("Enter the Book Number To Be Deleted From The Above List: ");

                    int bookMatch = 0;
                    bookNo = Utils.getInt();
                    System.out.println("Confirm Deletion:\n\t\t1.Yes\n\t\t0.No");
                    confirm = Utils.getInt();
                    while ((confirm != YES) && (confirm != NO)) {
                        System.out.println("Enter Valid Option: ");
                        confirm = Utils.getInt();
                    }
                    if (confirm == NO) {
                        System.out.println("Book Not Deleted!!");
                        userChoice = 0;
                        continue;
                    }
                    if (confirm == YES) {
                        Book bookToBeDeleted = new Book();
                        for (Book singleBook : Resources.books) {
                            if ((singleBook.bookNo == bookNo) && (singleBook.availableCopies == singleBook.totalCopies)) {
                                bookToBeDeleted = singleBook;
                                deleteCount++;
                                bookMatch++;
                                Shelf.removeBooksFromShelfForDeletion(bookNo);
                            }
                            if ((singleBook.bookNo == bookNo) && (singleBook.availableCopies != singleBook.totalCopies)) {
                                System.out.println("Book with Book No: " + bookNo + " Is Not Eligible for Deletion!!");
                                bookMatch++;
                            }
                        }
                        boolean bookDeleteResult = Resources.books.remove(bookToBeDeleted);
                        if (bookDeleteResult) {
                            System.out.println("Book with Number: " + bookNo + " is Deleted Successfully!!!");
                            Book.bookCount--;
                            Shelf.relocateBooksToShelf(-1);
                        }
                        if (bookMatch == 0) {
                            System.out.println("Enter Valid Book Number!!\n\n");
                            userChoice = 1;
                            continue;
                        }
                        if (deleteCount == 0) {
                            System.out.println("No Books Deleted!!");
                            deleteCount = 0;
                        }
                    }

                } else if (userChoice == DELETEBOOKCOPY) {
                    book.viewBookCopies();
                    int bookID;
                    String bookIDInString;

                    System.out.println("\n\nEnter the ID of the books to be deleted(Press Enter After Each ID's): \nPress Enter after Completion\n");

                    ArrayList<Integer> bookIDList = new ArrayList<Integer>();

                    do {
                        bookIDInString = Utils.getString();
                        if (bookIDInString.isEmpty()) {
                            break;
                        }
                        bookID = Integer.parseInt(bookIDInString);
                        bookIDList.add(bookID);
                    } while (true);
                    System.out.println("Confirm Deletion:\n\t\t1.Yes\n\t\t0.No");
                    confirm = Utils.getInt();
                    while ((confirm != YES) && (confirm != NO)) {
                        System.out.println("Enter Valid Option: ");
                        confirm = Utils.getInt();
                    }
                    if (confirm == NO) {
                        System.out.println("Book Not Deleted!!");
                        userChoice = 0;
                        continue;
                    }
                    if (confirm == YES) {
                        for (Book singlebook : Resources.books) {

                            for (Integer ID : bookIDList) {
                                for (Book bookCopy : singlebook.bookCopies) {
                                    if ((bookCopy.bookID == ID) && (singlebook.availableCopies > 0) && (singlebook.totalCopies > 0) && (bookCopy.isAvailable == true)) {

                                        singlebook.toBeDeletedBooks.add(bookCopy);
                                        --singlebook.availableCopies;
                                        --singlebook.totalCopies;
                                        Shelf.removeBookCopyFromShelf(bookCopy.bookID);
                                        System.out.println("Book with Book ID: " + ID + " Is Deleted!!");
                                        deleteCount++;

                                    } else if ((bookCopy.bookID == ID) && ((singlebook.availableCopies <= 0) || (singlebook.totalCopies <= 0) || (bookCopy.isAvailable == false))) {
                                        System.out.println("Book with Book ID: " + ID + " Is Not Eligible for Deletion!!");
                                    }

                                }
                            }
                        }

                    }
                    for (Book singleBook : Resources.books) {

                        singleBook.bookCopies.removeAll(singleBook.toBeDeletedBooks);

                    }

                    if (deleteCount == 0) {
                        System.out.println("No Books Deleted!!");

                    } else if (deleteCount > 0) {
                        System.out.println("Number Of Books Deleted: " + deleteCount);
                        deleteCount = 0;
                    }

                    for (Book books : Resources.books) {
                        books.toBeDeletedBooks.removeAll(books.toBeDeletedBooks);
                    }
                    Shelf.relocateBooksToShelf(bookNo);
                }
                System.out.println("Enter 1.Delete more books");
                System.out.println("Enter 0.Main Page");
                System.out.println("Enter appropraite Option: ");
                userChoice = Utils.getInt();

            } else {
                System.out.println("No Books Available!!Add Some Books");
                userChoice = 0;
            }
        }

    }

    private void viewPayments() {
        if (!Resources.payments.isEmpty()) {
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %15s %15s %15s %20s %25s %25s ", "PAYMENT ID", "USER ID", "USER NAME", "AMOUNT PAID", "PAYMENT DATE", "PAYMENT PLATFORM", "PAYMENT PURPOSE");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            //System.out.println("PaymentID\t\tUserID\t\tUsername\t\tAmount Paid\t\tPayment Date\t\tPayment Platform\t\tPayment Purpose");
            for (Payment payment : Resources.payments) {
                System.out.printf("%5s %15s %15s %15s %20s %25s %25s ", payment.paymentID, payment.userID, payment.userName, payment.paymentAmount, Utils.printDate(payment.paymentDate), payment.paymentPlatform, payment.paymentPurpose);
                System.out.println();
                System.out.println("-----------------------------------------------------------------------------------------------------------------");
                //System.out.println(payment.paymentID+"\t\t"+payment.userID+"\t\t"+payment.userName+"\t\t"+payment.paymentAmount+"\t\t"+Utils.printDate(payment.paymentDate)+"\t\t"+payment.paymentPlatform+"\t\t"+payment.paymentPurpose);
            }
        } else {
            System.out.println("No Payments Done");
        }
    }

    public static void viewBorrowBookRequest() {
        int bookDisplayCount = 0;
        for (Patron patron : Resources.patrons) {
            if (!patron.booksOnHold.isEmpty()) {
                if (bookDisplayCount == 0) {
                    bookDisplayCount++;
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%5s %5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "USER ID", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
                    System.out.println();
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
                }

                for (Book book : patron.booksOnHold) {
                    System.out.printf("%5s %5s %20s %15s %20s %20s %20s %20s", book.bookID, book.bookName, patron.userID, book.bookNo, book.author, book.publishedIn, book.genre, Shelf.getBookPosition(book.bookID));
                    System.out.println();
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
        }
    }

    public static void bookApproveOption() {
        if (Resources.totalBooksOnHoldByAllPatrons != 0) {
            Admin.viewBorrowBookRequest();
            System.out.println("\n\nEnter the BookID to be Approved(Press Enter After Each ID's): PRESS ENTER AFTER COMPLETION\n");
            ArrayList<Integer> bookIDList = getBookIDList();
            approveBorrowRequest(bookIDList);
        } else {
            System.out.println("No Books in Hold List!!");
        }
    }

    public static void approveBorrowRequest(ArrayList<Integer> bookIDList) {
        final int EXIT = 0;
        int userChoice = 1;
        while (true) {
            int bookMatch = 0;
            for (Patron patron : Resources.patrons) {
                for (Book bookOnHold : patron.booksOnHold) {
                    for (Integer bookID : bookIDList) {
                        if (bookOnHold.bookID == bookID) {
                            bookMatch++;
                            for (Book book : Resources.books) {
                                for (Book bookCopy : book.bookCopies) {
                                    if (bookCopy.bookID == bookID) {
                                        System.out.println("Book with Book ID: " + bookID + "is Approved!!");
                                        bookCopy.bookLocation = "APPROVED(WITH LIBRARIAN)";
                                        patron.approvedBooks.add(bookCopy);
                                        patron.booksOnHoldCount--;
                                        Resources.totalBooksOnHoldByAllPatrons--;
                                    }
                                }
                            }
                        }
                    }
                    bookIDList.remove(Integer.valueOf(bookOnHold.bookID));
                }
                patron.booksOnHold.removeAll(patron.approvedBooks);
            }

            if (bookMatch == 0) {
                System.out.println("Enter Valid Book ID(Press Enter After Each ID's) Or O To Go To Main Page: PRESS ENTER AFTER COMPLETION ");
                userChoice = Utils.getInt();
                if (userChoice == EXIT) {
                    break;
                }
                bookIDList = getBookIDList();
                bookIDList.add(userChoice);
                approveBorrowRequest(bookIDList);
                break;
            } else if (!bookIDList.isEmpty()) {
                for (Integer bookID : bookIDList) {
                    System.out.println("Invalid Book ID: " + bookID);
                }
                break;
            } else if (bookIDList.isEmpty()) {
                System.out.println("Book Approved Successfully!!");
                break;
            }
        }
    }

    public static ArrayList<Integer> getBookIDList() {
        ArrayList<Integer> bookIDList = new ArrayList<>();
        do {
            String bookIDInString = Utils.getString();
            if (bookIDInString.isEmpty()) {
                break;
            }
            int bookID = Integer.parseInt(bookIDInString);
            bookIDList.add(bookID);
        } while (true);
        return bookIDList;
    }

}


    /* public static void viewBorrowBookRequest() {
        int bookDisplayCount = 0;
        for (Patron patron : Resources.patrons) {
            if (patron.booksOnHoldCount > 0) {
                if (bookDisplayCount == 0) {
                    bookDisplayCount++;
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%5s %5s %20s %15s %20s %20s %15s %20s", "USER ID", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
                    System.out.println();
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
                }

                for (Book book : patron.booksOnHold) {
                    System.out.printf("%5s %5s %20s %15s %20s %20s %20s %20s", patron.userID, book.bookID, book.bookName, book.bookNo, book.author, book.publishedIn, book.genre, Shelf.getBookPosition(book.bookID));
                    System.out.println();
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
                }
            }
        }
    }

    public static void bookApproveOption() {
        if (Resources.totalBooksOnHoldByAllPatrons != 0) {
            int loopExit = 1;
            int userID;
            int userMatched = 0;
            int bookID;
            while (LibraryManagementSystem.toBoolean(loopExit)) {
                Admin.viewBorrowBookRequest();
                System.out.println("Select Appropriate UserId to Approve Their Request\n\t\t0.Main Page");
                userID = Utils.getInt();
                while (userID < 0) {
                    System.out.println("Enter Valid Opiton!!");
                    userID = Utils.getInt();
                }

                if (userID == 0) {
                    break;
                }

                for (Patron patron : Resources.patrons) {
                    if ((patron.userID == userID) && (patron.booksOnHoldCount > 0)) {
                        loopExit = 0;
                        userMatched++;
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
                        System.out.println();
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                        for (Book book : patron.booksOnHold) {
                            book.displaySingleBookCopy();
                        }
                        //System.out.println("Enter BookID To Approve: ");
                        //bookID = Utils.getInt();
                        ArrayList<Integer> bookIDList = getBookIDList();
                        approveBorrowRequest(patron, bookIDList);

                    }
                }
                if (userMatched == 0) {
                    System.out.println("Enter Valid UserID: ");
                    loopExit = 1;
                }
            }
        } else {
            System.out.println("No Books in Hold List!!");
        }
    }

    public static void approveBorrowRequest(Patron patron, ArrayList<Integer> bookIDList) {
        int bookMatch = 0;
        for (Book bookOnHold : patron.booksOnHold) {
            for (Integer bookID : bookIDList) {
                if (bookOnHold.bookID == bookID) {
                    bookMatch++;
                    for (Book book : Resources.books) {
                        for (Book bookCopy : book.bookCopies) {
                            if (bookCopy.bookID == bookID) {
                                System.out.println("Book with Book ID: " + bookID + "is Approved!!");
                                bookCopy.bookLocation = "APPROVED(WITH LIBRARIAN)";
                                patron.approvedBooks.add(bookCopy);
                                patron.booksOnHoldCount--;
                                Resources.totalBooksOnHoldByAllPatrons--;
                                //bookCopy.setDateLimit();

                            }
                        }
                    }
                }
            }
        }
        if (bookMatch == 0) {
            System.out.println("Enter Valid Book ID: ");
            bookIDList = getBookIDList();
            approveBorrowRequest(patron, bookIDList);
        }
        else
        {
        patron.booksOnHold.removeAll(patron.approvedBooks);
        System.out.println("Book Approved Successfully!!");
        }
    }

    public static ArrayList<Integer> getBookIDList() {
        ArrayList<Integer> bookIDList = new ArrayList<>();
        System.out.println("\n\nEnter the ID of the books to be Approved(Press Enter After Each ID's): \nPress Enter after Completion\n");
        do {
            String bookIDInString = Utils.getString();
            if (bookIDInString.isEmpty()) {
                break;
            }
            int bookID = Integer.parseInt(bookIDInString);
            bookIDList.add(bookID);
        } while (true);
        return bookIDList;
    }*/
