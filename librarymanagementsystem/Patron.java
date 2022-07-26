/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import java.util.*;
import java.lang.*;
/**
 *
 * @author mouli011
 */
public class Patron extends User{

    static int patronCount = 0;
    protected int bookCount = 0;
    protected double totalFine = 0.0;
    ArrayList<Book> borrowedBooks = new ArrayList<Book>();
    protected double fine = 0.0;
    ArrayList<Payment> individualPatronPayments = new ArrayList<Payment>();
    int booksOnHoldCount = 0;
    protected HashSet<Book> approvedBooks = new HashSet<Book>();
    protected HashSet<Book> booksOnHold = new HashSet<Book>();
    protected int renewalCount = 0;
    String mailID;

    @Override
    public void intialOptionPage() {
        //contants
        final int EXIT = 0;

        int userChoice = 1;

        while (userChoice != EXIT) {
            System.out.println("\n\n\nEnter Options:");
            System.out.println("\t\t1.SIGN UP - If you are a new user\n\t\t2.SIGN IN - If you have a account\n\t\t0.Exit");
            System.out.println("\nEnter your Choice: ");
            userChoice = Patron.chosenOption(userChoice);
        }
    }

    public static int chosenOption(int userChoice) {
        //constants
        final int REGISTER = 1;
        final int SIGNIN = 2;
        final int EXIT = 0;
        int userID;

        userChoice = Utils.getInt();

        switch (userChoice) {
            case REGISTER:
                Patron p = new Patron();
                p.registerPatron();
                break;

            case SIGNIN:
                if ((userID = User.signIn()) > 0) {
                    Patron.listUserFunctions(userID);
                }
                break;

            case EXIT:
                break;

            default:
                System.out.println("Enter valid option");
                break;
        }

        return userChoice;
    }

    public void registerPatron() {
        userName = Patron.getInputString("Enter Username: ");
        password = Patron.getInputString("Enter Password:");
        phoneNumber = Patron.getInputString("Enter Phone Number:");
        phoneNumber = Utils.checkNGetValidPhoneNumber(phoneNumber);
        System.out.println("Enter Gender: ");
        gender = Utils.getGender();
        mailID = Patron.getInputString("Enter Mail ID:");
        mailID = Utils.checkNGetValidMailID(mailID);
        String dateInput = Patron.getInputString("Enter Date Of Birth(dd-MM-yyyy): ");
        dateOfBirth = Utils.parseDate(dateInput);
        userID = User.getUserId();
        Resources.patrons.add(this);
        Resources.users.add(this);
        System.out.println("\nSign Up Successful!!\n\nUsername: " + userName + "\nUserID: " + userID);
        patronCount++;
    }

    public static void listUserFunctions(int userID) {
        //constants
        final int MYPROFILE = 1;
        final int SEARCHBOOKS = 2;
        final int BORROWBOOKS = 3;
        final int RETURNBOOKS = 4;
        final int DONATEBOOKS = 5;
        final int VIEWPAYMENT = 6;
        final int RENEWBOOKS = 7;
        final int BOOKLOST = 8;
        final int HISTORY = 9;
        final int EXIT = 0;

        int bookCount = 0;

        for (User user : Resources.users) {
            if (user.userID == userID) {
                System.out.println("Sign In Successfull!!");
                System.out.println("Welcome " + user.userName);
            }

        }
        for (Patron patron : Resources.patrons) {
            if ((patron.userID == userID) && (!patron.approvedBooks.isEmpty())) {
                Patron.borrowRequestApproved(patron);
            }
        }
        int userChoice = 1;
        while (userChoice != EXIT) {
            for (Patron patron : Resources.patrons) {
                if (patron.userID == userID) {
                    bookCount = patron.bookCount;
                }
            }
            System.out.println("\n\nEnter Appropriate Options:");
            if (bookCount != 0) {
                System.out.println("\t\t1.My Profile - " + bookCount + " Books Borrowed \n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Donate Books\n\t\t6.View Payments\n\t\t7.Renew Books\n\t\t8.Book Lost\n\t\t9.History");
            }
            if (bookCount == 0) {
                System.out.println("\t\t1.My Profile\n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Donate Books\n\t\t6.View Payments\n\t\t7.Renew Books\n\t\t8.Book Lost\n\t\t9.History");
            }
            System.out.println("\n\t\t0.Exit\n\nEnter Your Choice: ");

            userChoice = Utils.getInt();

            switch (userChoice) {
                case MYPROFILE:
                    Patron patron = new Patron();
                    patron.displayPatronProfile(userID);
                    break;

                case SEARCHBOOKS:
                    Patron.searchBooks(userID);
                    break;

                case BORROWBOOKS:
                    Patron borrowPatron = new Patron();
                    borrowPatron.borrowBooks(userID, 0, null);
                    break;

                case RETURNBOOKS:
                    Patron returnPatron = new Patron();
                    returnPatron.returnBooks(userID);
                    Shelf.allotLocationForBooks();
                    break;

                case DONATEBOOKS:
                    Book.displayForAddBooks();
                    Shelf.allotLocationForBooks();
                    break;

                case VIEWPAYMENT:
                    for (Patron viewPatron : Resources.patrons) {
                        if (viewPatron.userID == userID) {
                            viewPatron.displayIndividualPatronPayment(userID);
                        }
                    }
                    break;

                case RENEWBOOKS:
                    Patron patronRenew = new Patron();
                    patronRenew.renewBooks(userID);
                    break;

                case BOOKLOST:
                    Patron.getLostBooks(userID);
                    break;

                case HISTORY:
                    for (Patron patronHistory : Resources.patrons) {
                        if (patronHistory.userID == userID) {
                            patronHistory.displayPatronHistory();
                        }
                    }
                    break;

                case EXIT:
                    break;

                default:
                    System.out.println("Enter valid option");
                    break;
            }
        }
    }

    public void displayPatronProfile(int userID) {
        for (Patron patron : Resources.patrons) {
            if (patron.userID == userID) {
                System.out.println("UserName: " + patron.userName);
                System.out.println("Phone Number: " + patron.phoneNumber);
                System.out.println("Gender: " + patron.gender);
                System.out.println("Mail ID: " + patron.mailID);
                System.out.println("Date Of Birth: " + Utils.printDate(patron.dateOfBirth));
                System.out.println("\n\nNumber of Books Borrowed: " + patron.bookCount);
                if (patron.bookCount != 0) {
                    patron.displayBorrowedBooks();
                }
                patron.fine = patron.checkTotalFine();
                System.out.println("Fine Amount: " + patron.fine);
            }
        }
    }

    public static void searchBooks(int userID) {
        //CONSTANTS FOR SWITCH CASE:
        final int SEARCHBYNAME = 1;
        final int SEARCHBYAUTHOR = 2;
        final int SEARCHBYGENRE = 3;
        final int EXIT = 0;
        ArrayList<Integer> nearestBookIDList = new ArrayList<>();
        int userChoice = 1;
        while (userChoice != EXIT) {
            System.out.println("Enter Appropriate Option: ");
            System.out.println("\t\t1.Search By Book Name\n\t\t2.Search By Author Name\n\t\t3.Search By Genre\n\t\t0.Main Page");
            System.out.println("Your Option: ");
            userChoice = Utils.getInt();
            switch (userChoice) {
                case SEARCHBYNAME:
                    nearestBookIDList = (ArrayList) Book.filterByBookName().clone();
                    if ((nearestBookIDList.isEmpty())) {
                        System.out.println("No Books Found!!");
                        userChoice = 1;
                        continue;
                    }
                    break;

                case SEARCHBYAUTHOR:
                    nearestBookIDList = (ArrayList) Book.filterByAuthorName().clone();
                    if ((nearestBookIDList.isEmpty())) {
                        userChoice = 1;
                        continue;
                    }
                    break;

                case SEARCHBYGENRE:
                    nearestBookIDList = (ArrayList) Book.filterByGenre().clone();
                    if ((nearestBookIDList.isEmpty())) {
                        userChoice = 1;
                        continue;
                    }
                    break;

                case EXIT:
                    break;

                default:
                    System.out.println("Enter Valid Option!!");
                    userChoice = Utils.getInt();
                    continue;
            }
            if (userChoice > 0) {
                System.out.println("Enter Options:\n\t\t1.Borrow Book\n\t\t2.Search Page\n\t\t0.Main Page");
                System.out.println("Enter Your Choice: ");
                userChoice = Utils.getInt();
                while ((userChoice != 0) && (userChoice != 1) && (userChoice != 2)) {
                    System.out.println("Enter Vaild Option:");
                    userChoice = Utils.getInt();
                }
                if (userChoice == 1) {
                    new Patron().borrowBooks(userID, userChoice, nearestBookIDList);
                }
            }
        }
    }

    public void displayBorrowedBooks() {

        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %15s %20s %20s %25s %25s ", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "BORROWED DATE", "RETURN DATE DUE");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.bookLocation.equals("BORROWED")) {
                System.out.printf("%5s %20s %15s %20s %20s %25s %25s ", borrowedBook.bookID, borrowedBook.bookName, borrowedBook.bookNo, borrowedBook.author, borrowedBook.publishedIn, Utils.printDate(borrowedBook.borrowedDate), Utils.printDate(borrowedBook.toBeReturnedDate));
                System.out.println();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------");
            }
        }
    }

    public void returnBooks(int userID) {
        //constants
        final int EXIT = 0;

        int loopExit = 1;
        int confirm;
        for (Patron patron : Resources.patrons) {
            if ((patron.userID == userID) && (patron.bookCount > 0)) {
                while (loopExit != EXIT) {
                    //Book returnedBook = new Book();
                    int bookID;
                    int bookMatch = 0;
                    patron.displayBorrowedBooks();
                    System.out.println("Enter the BookID to be returned Or Enter 0 To Go To Main Page\n");
                    bookID = Utils.getInt();
                    if (bookID == 0) {
                        loopExit = EXIT;
                        continue;
                    }
                    for (Book book : Resources.books) {
                        for (Book bookCopy : book.bookCopies) {
                            if ((bookCopy.bookID == bookID) && (bookCopy.isAvailable == false) && (bookCopy.bookLocation.equals("BORROWED"))) {
                                patron.fine = patron.checkFine(bookCopy.bookID);
                                bookMatch++;
                                if (patron.fine == 0) {
                                    System.out.println("Confirm The Return Of The Book\n\t\t1.Yes\n\t\t0.No");
                                    confirm = Utils.getInt();
                                    while ((confirm != 0) && (confirm != 1)) {
                                        System.out.println("Enter Valid Option: ");
                                        confirm = Utils.getInt();
                                    }
                                    if (confirm == 0) {
                                        System.out.println("Book Not Returned!!");
                                        loopExit = EXIT;
                                        continue;
                                    }
                                    if (confirm == 1) {
                                        bookCopy.returnBookCopy();
                                        book.availableCopies++;
                                        patron.bookCount--;
                                        //returnedBook =bookCopy;
                                        //patron.borrowedBooks.remove(returnedBook);
                                        bookCopy.bookLocation = "";
                                        for (Book borrowedBook : patron.borrowedBooks) {
                                            if (borrowedBook.bookID == bookID) {
                                                borrowedBook.bookLocation = "RETURNED";
                                            }
                                        }
                                        System.out.println("Book Returned Successfully!!");
                                        loopExit = EXIT;
                                        continue;
                                    }
                                } else {
                                    System.out.println("Confirm The Return Of The Book\n\t\t1.Yes\n\t\t0.No");
                                    confirm = Utils.getInt();
                                    while ((confirm != 0) && (confirm != 1)) {
                                        System.out.println("Enter Valid Option: ");
                                        confirm = Utils.getInt();
                                    }
                                    if (confirm == 0) {
                                        System.out.println("Book Not Returned!!");
                                        loopExit = EXIT;
                                        continue;
                                    }
                                    if (confirm == 1) {
                                        boolean finePaid = patron.payFine(patron.fine, userID);
                                        if (finePaid) {

                                            bookCopy.returnBookCopy();
                                            book.availableCopies++;
                                            patron.bookCount--;
                                            patron.fine = 0;
                                            //returnedBook = bookCopy;
                                            for (Book borrowedBook : patron.borrowedBooks) {
                                                if (borrowedBook.bookID == bookCopy.bookID) {
                                                    borrowedBook.bookLocation = "RETURNED";
                                                }
                                            }
                                            //patron.borrowedBooks.remove(returnedBook);
                                            bookCopy.bookLocation = "";
                                            System.out.println("Book Returned Successfully!!");
                                            loopExit = EXIT;
                                            continue;
                                        } else {
                                            System.out.println("Book Not Returned!!");
                                            loopExit = EXIT;
                                            continue;
                                        }
                                    }
                                }
                            }

                        }
                    }
                    if (bookMatch == 0) {
                        System.out.println("Enter Valid Book ID!!");
                        new Patron().returnBooks(userID);
                        loopExit = EXIT;
                        continue;
                    }
                }
            } else if ((patron.userID == userID) && (patron.bookCount == 0)) {
                System.out.println("No Books to Return!!");
                loopExit = EXIT;
                continue;
            }
        }
    }

    public boolean payFine(double fine, int userID) {
        //constants
        final int TOPAY = 1;
        final int CANCEL = 0;

        boolean paymentResult = false;
        System.out.println("Amount To Be Paid: " + fine);
        System.out.println("Enter appropriate Option");
        System.out.println("\t\t1.Proceed To Pay\n\t\t0.Cancel");
        int userChoice;
        userChoice = Utils.getInt();
        switch (userChoice) {
            case TOPAY:
                Payment payment = new Payment();
                paymentResult = payment.performPayment(this);
                if (paymentResult) {
                    for (Patron patron : Resources.patrons) {
                        if (patron.userID == userID) {
                            payment.paymentPurpose = "Fine for Late Return";
                            payment.userName = patron.userName;
                            payment.userID = patron.userID;
                            patron.individualPatronPayments.add(payment);
                            Resources.payments.add(payment);
                        }
                    }
                }
                break;

            case CANCEL:
                paymentResult = false;
                break;

            default:
                System.out.println("Enter Appropriate Option: ");
                paymentResult = false;
                break;
        }
        return paymentResult;
    }

    public void displayIndividualPatronPayment(int userID) {
        if (!individualPatronPayments.isEmpty()) {
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            System.out.format("%5s %20s %15s %25s %25s", "PAYMENT ID", "PAYMENT DATE", "AMOUNT PAID", "PAYMENT PLATFORM", "PAYMENT PURPOSE");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            for (Payment payment : individualPatronPayments) {
                System.out.format("%5s %20s %15s %25s %25s", payment.paymentID, Utils.printDate(payment.paymentDate), payment.paymentAmount, payment.paymentPlatform, payment.paymentPurpose);
                System.out.println();
                System.out.println("-----------------------------------------------------------------------------------------------------------------");
            }
        } else {
            System.out.println("No Payments Done");
        }
    }

    public void renewBooks(int userID) {

        final int EXIT = 0;

        int loopExit = 1;
        int bookID;
        int bookMatch = 0;
        int confirm;

        for (Patron patron : Resources.patrons) {
            first:
            while (loopExit != EXIT) {
                if ((patron.userID == userID) && (patron.renewalCount < Rules.maxRenewalCount) && (patron.bookCount > 0)) {

                    patron.displayBorrowedBooks();
                    System.out.println("Enter the BookID to be renewed Or Enter 0 To Go To Main Page\n");
                    bookID = Utils.getInt();
                    if (bookID == 0) {
                        loopExit = EXIT;
                        break first;
                    }
                    for (Book book : Resources.books) {
                        for (Book bookCopy : patron.borrowedBooks) {
                            if ((bookCopy.bookID == bookID) && (bookCopy.bookLocation.equals("BORROWED"))) {

                                bookMatch++;
                                System.out.println("Confirm The Renewal Of The Book\n\t\t1.Yes\n\t\t0.No");
                                confirm = Utils.getInt();
                                while ((confirm != 0) && (confirm != 1)) {
                                    System.out.println("Enter Valid Option: ");
                                    confirm = Utils.getInt();
                                }
                                if (confirm == 0) {
                                    System.out.println("Book Not Renewed!!");
                                    loopExit = EXIT;
                                    break first;
                                }
                                if (confirm == 1) {
                                    patron.fine = patron.checkFine(bookCopy.bookID);
                                    patron.fine = patron.fine + Rules.renewalFee;
                                    boolean finePaid = patron.payRenewalFee(patron.fine);
                                    if (finePaid) {
                                        System.out.println("Book Return Date Extended!!");
                                        patron.renewalCount++;
                                        bookCopy.renewBookCopy();
                                        System.out.println("Renewed Return Date: " + Utils.printDate(bookCopy.toBeReturnedDate));
                                        patron.fine = 0;
                                        loopExit = EXIT;
                                        break first;

                                    } else {
                                        System.out.println("Book Not Renewed!!!");
                                        loopExit = EXIT;
                                        break first;
                                    }
                                }

                            }
                        }
                    }
                } else if ((patron.userID == userID) && (patron.renewalCount < Rules.maxRenewalCount) && (patron.bookCount == 0)) {
                    System.out.println("No Books to Renew!!");
                    loopExit = EXIT;
                    break first;
                } else if ((patron.userID == userID) && (patron.renewalCount <= Rules.maxRenewalCount)) {
                    System.out.println("Renewal Limit Reached");
                    loopExit = EXIT;
                    break first;
                }
                /*else if((bookMatch==0)&&(loopExit>0))
                {
                    System.out.println("Enter Valid Book ID!!");
                    new Patron().renewBooks(userID);
                    loopExit=0;
                    continue;
                }*/
                System.out.println("Enter Valid Book ID!!");
                new Patron().renewBooks(userID);
                loopExit = EXIT;
            }
        }
    }

    public boolean payRenewalFee(double fine) {
        //constants
        final int TOPAY = 1;
        final int CANCEL = 0;

        boolean paymentResult = false;
        System.out.println("\t\tFine: Rs." + (fine - Rules.renewalFee) + "\n\t\tRenewal Fee: Rs." + Rules.renewalFee);
        System.out.println("Amount To Be Paid: \tRs." + fine);
        System.out.println("Enter appropriate Option");
        System.out.println("\t\t1.Proceed To Pay\n\t\t0.Cancel");
        int userChoice;
        userChoice = Utils.getInt();
        switch (userChoice) {
            case TOPAY:
                Payment payment = new Payment();
                paymentResult = payment.performPayment(this);
                if (paymentResult == true) {
                    for (Patron patron : Resources.patrons) {
                        if (patron.userID == userID) {
                            payment.paymentPurpose = "Return Date Renewal";
                            payment.userID = patron.userID;
                            payment.userName = patron.userName;
                            patron.individualPatronPayments.add(payment);
                            Resources.payments.add(payment);
                        }
                    }
                }
                break;

            case CANCEL:
                paymentResult = false;
                break;
        }
        return paymentResult;
    }

    public double checkFine(int bookID) {

        double fine = 0.0;
        this.fine = 0.0;
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.bookID == bookID) {
                fine = this.fine + borrowedBook.fineCalculator();
            }
        }
        return fine;
    }

    public double checkTotalFine() {

        double fine = 0.0;
        this.fine = 0.0;
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.bookLocation.equals("BORROWED")) {
                fine = borrowedBook.fineCalculator() + fine;
            }
        }
        return fine;
    }

    public static void getLostBooks(int userID) {
        if (Book.bookCount != 0) {
            for (Patron patron : Resources.patrons) {
                if ((patron.userID == userID) && (patron.bookCount > 0)) {
                    patron.displayBorrowedBooks();
                    System.out.println("Enter The Book ID Of The Book Lost from The Above List: ");
                    int bookID = Utils.getInt();
                    patron.lostBook(bookID);
                } else if ((patron.userID == userID) && (patron.bookCount == 0)) {
                    System.out.println("No Books Borrowed to Loose");
                }
            }
        } else {
            System.out.println("No Books to Loose!!");
        }
    }

    public void lostBook(int bookID) {

        final int EXIT = 0;
        final int YES = 1;
        final int NO = 0;

        int loopExit = 1;
        int confirm;
        while (loopExit != EXIT) {
            Book returnedBook = new Book();
            int bookMatch = 0;
            for (Book bookLost : this.borrowedBooks) {
                if ((bookLost.bookID == bookID) && (bookLost.bookLocation.equals("BORROWED"))) {
                    System.out.println("Confirm To Proceed Further:\n\t\t1.Yes\n\t\t0.No");
                    confirm = Utils.getInt();
                    while ((confirm != YES) && (confirm != NO)) {
                        System.out.println("Enter Valid Option: ");
                        confirm = Utils.getInt();
                    }
                    if (confirm == NO) {
                        System.out.println("Fine Not Paid!!");
                        loopExit = 0;
                        continue;
                    }
                    if (confirm == YES) {

                        this.fine = this.checkFine(bookLost.bookID);
                        this.fine = this.fine + bookLost.bookPrice;
                        bookMatch++;
                        boolean finePaid = this.payBookLostFine(this.fine, bookLost.bookPrice);
                        if (finePaid) {
                            //bookLost.returnBookCopy(); 
                            //returnedBook =bookLost;
                            for (Book book : Resources.books) {
                                for (Book bookCopy : book.bookCopies) {
                                    if (bookCopy.bookID == bookLost.bookID) {
                                        //book.totalCopies--;
                                        returnedBook = bookCopy;
                                        //Resources.lostBooks.add(returnedBook);
                                        //Resources.lostBookCount++;
                                        //book.bookCopies.remove(returnedBook);
                                        book.totalCopies--;
                                        Resources.lostBooks.add(returnedBook);
                                        Resources.lostBookCount++;
                                    }
                                }
                                book.bookCopies.remove(returnedBook);
                            }
                            this.bookCount--;
                            this.fine = 0;
                            bookLost.bookLocation = "LOST";
                            System.out.println("Fine Paid!!");
                            loopExit = EXIT;
                            continue;
                        } else {
                            System.out.println("Fine Not Paid!!");
                            loopExit = EXIT;
                            continue;
                        }
                    }
                }
            }
            //this.borrowedBooks.remove(returnedBook);

            if ((bookMatch == 0) && (loopExit != EXIT)) {
                System.out.println("\n\nEnter Proper Book ID!!\n\nEnter Options:\n\t\t1.Lost Book\n\t\t0.Main Page");
                loopExit = Utils.getInt();
                while ((loopExit != NO) && (loopExit != YES)) {
                    System.out.println("Enter Valid Option!!");
                    loopExit = Utils.getInt();
                }
                if (loopExit == 1) {
                    Patron.getLostBooks(this.userID);
                    loopExit = EXIT;
                }
            }
        }
    }

    public boolean payBookLostFine(double fine, double bookPrice) {

        //constants
        final int TOPAY = 1;
        final int CANCEL = 0;

        boolean paymentResult = false;
        System.out.println("\t\tFine: " + (fine - bookPrice) + "\n\t\tLost Book Fee " + bookPrice);
        System.out.println("Amount To Be Paid: " + fine);
        System.out.println("Enter appropriate Option");
        System.out.println("\t\t1.Proceed To Pay\n\t\t0.Cancel");
        int userChoice;
        userChoice = Utils.getInt();
        switch (userChoice) {
            case TOPAY:
                Payment payment = new Payment();
                paymentResult = payment.performPayment(this);
                if (paymentResult == true) {
                    for (Patron patron : Resources.patrons) {
                        if (patron.userID == userID) {
                            payment.paymentPurpose = "Book Lost Fine";
                            payment.userID = patron.userID;
                            payment.userName = patron.userName;
                            patron.individualPatronPayments.add(payment);
                            payment.userID = patron.userID;
                            payment.userName = patron.userName;
                            Resources.payments.add(payment);
                        }
                    }
                }
                break;

            case CANCEL:
                paymentResult = false;
                break;
        }
        return paymentResult;
    }

    public static void borrowRequestApproved(Patron patron) {

        final int BORROW = 1;
        final int MAINPAGE = 0;

        ArrayList<Book> booksToRemoveFromApprovedList = new ArrayList<>();
        int confirmBorrow;

        whileLoop:
        while (true) {
            if ((patron.bookCount <= 1) && (patron.fine == 0)) {
                approvedBookLoop:
                for (Book approvedBook : patron.approvedBooks) {
                    for (Book book : Resources.books) {
                        for (Book bookCopy : book.bookCopies) {
                            if (bookCopy.bookID == approvedBook.bookID) {
                                System.out.println("\n\n------Requested Book Approved!!!-------\n\n");
                                System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                                System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
                                System.out.println();
                                System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                                bookCopy.displaySingleBookCopy();

                                System.out.println("Enter 1 To Borrow The Book Or 0 To Go To Main Page\n\n\t-------NOTE: IF YOU PROCEED TO MAIN PAGE YOUR REQUESTED BOOKS WILL BE REMOVED FROM YOUR APPROVED LIST-------");
                                confirmBorrow = Utils.getInt();
                                while ((confirmBorrow != BORROW) && (confirmBorrow != MAINPAGE)) {
                                    System.out.println("Enter Valid Option!!\n");
                                    confirmBorrow = Utils.getInt();
                                }
                                if (confirmBorrow == MAINPAGE) {
                                    Patron.deleteApprovedBooks(patron);
                                    Shelf.allotLocationForBooks();
                                    System.out.println("Requested Books Removed From Approved List");
                                    break whileLoop;
                                } else if (confirmBorrow == BORROW) {

                                    bookCopy.setDateLimit();
                                    bookCopy.bookLocation = "BORROWED";
                                    Book bookToAdd = new Book();
                                    bookToAdd.copyBookInfoToBorrowedBook(bookCopy);
                                    patron.borrowedBooks.add(bookToAdd);
                                    booksToRemoveFromApprovedList.add(bookCopy);
                                    System.out.println("\n\n---------BOOK BORROWED SUCCESSFULLY------\n\n");
                                    patron.displayBorrowedBooks();
                                    System.out.println("\n\n");
                                    for (Book borrowedBook : patron.borrowedBooks) {
                                        patron.fine = patron.checkFine(borrowedBook.bookID);

                                    }
                                    patron.bookCount++;
                                    break approvedBookLoop;

                                }

                            }
                        }
                    }
                }
                patron.approvedBooks.removeAll(booksToRemoveFromApprovedList);
                if (patron.approvedBooks.isEmpty()) {
                    break whileLoop;
                }
                continue whileLoop;
            } else if ((patron.bookCount > 1) || (patron.fine != 0)) {
                for (Book approvedBook : patron.approvedBooks) {
                    for (Book book : Resources.books) {
                        for (Book bookCopy : book.bookCopies) {
                            if (bookCopy.bookID == approvedBook.bookID) {
                                System.out.println("\n\n------Requested Book Approved!!!-------\n\n");
                                System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                                System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
                                System.out.println();
                                System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                                bookCopy.displaySingleBookCopy();
                            }
                        }
                    }
                }
                System.out.println("Return Books To Borrow Your Approved Books!!!\n\t\tEnter 1.To Return Books\n\t\t 0.To Main Page\n\t-------NOTE: IF YOU PROCEED TO MAIN PAGE YOUR REQUESTED BOOKS WILL BE REMOVED FROM YOUR APPROVED LIST-------");
                int userChoice = Utils.getInt();
                while ((userChoice != 1) && (userChoice != 0)) {
                    System.out.println("Enter Valid Options: ");
                    userChoice = Utils.getInt();
                }
                if (userChoice == 1) {
                    patron.returnBooks(patron.userID);
                    if ((patron.bookCount <= 1) && (patron.fine == 0)) {
                        Patron.borrowRequestApproved(patron);
                    }
                    if (patron.approvedBooks.isEmpty()) {
                        Shelf.allotLocationForBooks();
                        break whileLoop;
                    } else {
                        Patron.deleteApprovedBooks(patron);
                        Shelf.allotLocationForBooks();
                        System.out.println("Requested Books Removed From Approved List");
                        break whileLoop;
                    }
                }
                if (userChoice == 0) {
                    Patron.deleteApprovedBooks(patron);
                    Shelf.allotLocationForBooks();
                    System.out.println("Requested Books Removed From Approved List");
                    break whileLoop;
                }
            }

        }

    }

    public static void deleteApprovedBooks(Patron patron) {
        for (Book approvedBook : patron.approvedBooks) {
            for (Book book : Resources.books) {
                for (Book bookCopy : book.bookCopies) {
                    if (bookCopy.bookID == approvedBook.bookID) {
                        bookCopy.bookLocation = "GODOWN";
                        bookCopy.isAvailable = true;
                        book.availableCopies++;
                    }
                }
            }
        }
        patron.booksOnHold.removeAll(patron.booksOnHold);
        patron.approvedBooks.removeAll(patron.approvedBooks);
        patron.booksOnHoldCount = 0;
    }

    public ArrayList<Integer> displayFirstAvailableBook() {
        ArrayList<Integer> nearestBookIDS = new ArrayList<>();
        int nearestBookID = -1;
        int availableBooksFound = 0;
        for (Book book : Resources.books) {
            if (book.availableCopies > 0) {
                availableBooksFound++;
            }
        }
        if (availableBooksFound != 0) {
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK STATUS");
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

            for (Book nearestBook : Resources.books) {
                if (nearestBook.availableCopies > 0) {
                    nearestBookID = Shelf.getFirstAvailableBook(nearestBook);
                    for (Book nearestBookCopy : nearestBook.bookCopies) {
                        if (nearestBookCopy.bookID == nearestBookID) {
                            nearestBookCopy.displaySingleBookCopy();
                            nearestBookIDS.add(nearestBookCopy.bookID);
                        } else if ((nearestBookID == -1) && (nearestBookCopy.isAvailable == true)) {
                            nearestBookCopy.displaySingleBookCopy();
                            nearestBookIDS.add(nearestBookCopy.bookID);
                            break;
                        }
                    }
                }
            }
            return nearestBookIDS;
        }
        return nearestBookIDS;
    }

    public void borrowBooks(int userID, int borrowRequest, ArrayList<Integer> nearestBookIDS) {

        final int EXIT = 0;

        String filterChoice = null;
        int loopExit = 1;
        boolean valid = true;
        while (loopExit != EXIT) {
            int bookID;
            int availableBooks = 0;
            if (borrowRequest == 0) {
                nearestBookIDS = (ArrayList) this.displayFirstAvailableBook().clone();
                if (!(nearestBookIDS.isEmpty())) {
                    System.out.println("Enter 'f' To Filter Books Or Enter the Book ID Or Press'0' To Go To Main Page");
                    filterChoice = Utils.getString();
                    filterChoice = Utils.checkNGetNotNull(filterChoice);
                    while ((!((filterChoice.equalsIgnoreCase("F")) || ((filterChoice.charAt(0) >= '0') && (filterChoice.charAt(0) <= '9'))))) {
                        System.out.println("Enter Valid Option: ");
                        filterChoice = Utils.getString();
                        filterChoice = Utils.checkNGetNotNull(filterChoice);
                    }
                    if (filterChoice.charAt(0) == 'f') {
                        Book.filterBooks();
                        System.out.println("Enter Book ID From The Above List Or Enter 0 To Go To Main Page: ");
                        filterChoice = Utils.getString();
                        filterChoice = Utils.checkNGetNotNull(filterChoice);
                    }
                    if (filterChoice.charAt(0) == '0') {
                        loopExit = EXIT;
                        continue;
                    }
                } else {
                    System.out.println("NO BOOKS AVAILABLE!!!");
                    loopExit = EXIT;
                    continue;
                }
            }
            if ((borrowRequest == 1)) {
                System.out.println("Enter Book ID From The Above List");
                filterChoice = Utils.getString();
                filterChoice = Utils.checkNGetNotNull(filterChoice);
            }
            if (!(nearestBookIDS.isEmpty()) || (borrowRequest == 1)) {
                bookID = Utils.checkHasStringNGetValidInt(filterChoice);
                if (bookID == 0) {
                    loopExit = EXIT;
                    continue;
                }
                while (valid) {
                    for (Integer nearestBookID : nearestBookIDS) {
                        if (nearestBookID == bookID) {
                            availableBooks++;
                            for (Patron patron : Resources.patrons) {
                                if (((patron.userID) == userID) && (patron.bookCount <= 1) && (patron.fine == 0)) {

                                    Book addedBook = new Book();
                                    addedBook.copyBookInfoToBorrowedBook(addedBook.assignBookCopy(bookID, patron));
                                    //addedBook=addedBook.assignBookCopy(bookID,patron);
                                    if (addedBook.bookID > 0) {
                                        patron.bookCount++;
                                        patron.borrowedBooks.add(addedBook);
                                        System.out.println("Book Borrowed Succussfully!!!");
                                        patron.displayBorrowedBooks();
                                        for (Book borrowedBook : patron.borrowedBooks) {
                                            patron.fine = patron.checkFine(borrowedBook.bookID);
                                        }

                                    }
                                    loopExit = EXIT;
                                    valid = false;
                                } else if (patron.booksOnHoldCount > 1) {
                                    System.out.println("Book Hold List Full!!");
                                    loopExit = EXIT;
                                    valid = false;
                                } else if (((patron.userID) == userID) && (patron.bookCount > 0) && (patron.fine > 0)) {
                                    System.out.println("Return Books and Pay Fine to Borrow new Books");
                                    patron.returnBooks(userID);
                                    Shelf.allotLocationForBooks();
                                    loopExit = EXIT;
                                    valid = false;
                                } else if (((patron.userID) == userID) && (patron.bookCount == 2) && (patron.fine >= 0)) {
                                    System.out.println("Return Books to Borrow new Books");
                                    patron.returnBooks(userID);
                                    Shelf.allotLocationForBooks();
                                    loopExit = EXIT;
                                    valid = false;
                                } else {
                                    loopExit = EXIT;
                                    continue;
                                }
                            }
                        }
                    }
                    if (availableBooks == 0) {
                        System.out.println("Enter Valid Book ID From the Above List Or Enter 0 To Go To Main Page");
                        bookID = Utils.getInt();
                        if (bookID != 0) {
                            valid = true;
                        }
                        if (bookID == 0) {
                            loopExit = EXIT;
                            valid = false;
                        }
                    }
                }
            } else if ((nearestBookIDS.isEmpty()) && (borrowRequest == 0)) {
                System.out.println("No Books Available!!");
                loopExit = EXIT;
            }
        }
    }

    public void displayPatronHistory() {

        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %15s %20s %20s %25s ", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "BOOK STATUS");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (Book borrowedBook : borrowedBooks) {
            System.out.printf("%5s %20s %15s %20s %20s %25s ", borrowedBook.bookID, borrowedBook.bookName, borrowedBook.bookNo, borrowedBook.author, borrowedBook.publishedIn, borrowedBook.bookLocation);
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public static String getInputString(String message) {
        System.out.println(message);
        String inputString = Utils.getString();
        return Utils.checkNGetNotNull(inputString);
    }

}


/*public void borrowBooks(int userID,int borrowRequest)
    {
        int loopExit=1;
        while(LibraryManagementSystem.toBoolean((loopExit)))
        {
        int bookID;
        int availableBooks=0;
        
        if(borrowRequest==0)
        {
             System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
             System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"BOOK STATUS");  
            System.out.println();  
             System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
                for(Book book:Resources.books)
                {
                    if(book.availableCopies>0)
                    {
                        for(Book bookCopy:book.bookCopies)
                        {
                            if(bookCopy.isAvailable==true)
                            {
                                availableBooks++;
                                bookCopy.displaySingleBookCopy();
                            }
                        }
                    }
                    
                }
                System.out.println("Enter\n\t\t1.Filter\n\t\t2.Borrow\n\t\t0.Main Page");
                int filterChoice = Utils.getInt();
                while((filterChoice!=0)&&(filterChoice!=1)&&(filterChoice!=2))
                {
                    System.out.println("Enter Valid Option: ");
                    filterChoice =Utils.getInt();
                }
                if(filterChoice==1)
                Book.filterBooks();
                if(filterChoice==0)
                {
                    loopExit=0;
                    continue;
                }
                
        }
                if((availableBooks>0)||(borrowRequest==1))
                {
                System.out.println("Enter the ID of the Book which you need from the above List: \n\nOr Enter 0 to Main Page: \n\n");
                bookID = Utils.getInt();
                if(bookID>0)
                {
                for(Patron patron: Resources.patrons)
                {
            if(((patron.userID)==userID)&&(patron.bookCount<=1)&&(patron.fine==0))
            {
                
                
                Book addedBook = new Book();
                addedBook=addedBook.assignBookCopy(bookID,patron);
                if(addedBook.bookID>0){
                patron.bookCount++;
                patron.borrowedBooks.add(addedBook);
                System.out.println("Book Borrowed Succussfully!!!");
                patron.displayBorrowedBooks();
                for(Book borrowedBook:patron.borrowedBooks)  
                {
                    patron.fine = patron.checkFine(borrowedBook.bookID);
                }
                
                }
                loopExit=0;
                continue;
                }
                
            else if(patron.booksOnHoldCount>1)
            {
                System.out.println("Book Hold List Full!!");
                loopExit=0;
                continue;
            }
            
            else if(((patron.userID)==userID)&&(patron.bookCount>0)&&(patron.fine>0))
            {
                System.out.println("Return Books and Pay Fine to Borrow new Books");
                patron.returnBooks(userID );
                Shelf.allotLocationForBooks();
                loopExit=0;
                continue;
            }
            else if(((patron.userID)==userID)&&(patron.bookCount==2)&&(patron.fine>=0))
            {
                System.out.println("Return Books to Borrow new Books");
                patron.returnBooks(userID );
                Shelf.allotLocationForBooks();
                loopExit=0;
                continue;
            }
                }
                }
                else
                {
                    loopExit=0;
                    continue;
                }
                }
                else if((availableBooks==0)&&(borrowRequest==0))
            {
                System.out.println("No Books Available!!");
                loopExit=0;
                continue;
            }
            
            
        
    }
    }*/
