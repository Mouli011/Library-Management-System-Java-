/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import java.util.*;
import java.lang.*;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author mouli011
 */
public class Patron extends User{

    protected int bookCount = 0;
    protected double totalFine = 0.0;
    ArrayList<Book> borrowedBooks = new ArrayList<Book>();
    protected double fine = 0.0;
    ArrayList<Payment> individualPatronPayments = new ArrayList<Payment>();
    //int booksOnHoldCount = 0;
    protected HashSet<Book> approvedBooks = new HashSet<Book>();
    protected HashSet<Book> booksOnHold = new HashSet<Book>();
    //protected int renewalCount = 0;
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

            case Constants.EXIT:
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
        /*System.out.println("Enter Password:");
        password = PasswordField.readPassword();*/
        phoneNumber = Patron.getInputString("Enter Phone Number:");
        while (!Utils.checkValidPhoneNumber(phoneNumber)) {
            phoneNumber = Utils.getValidPhoneNo();
        }
        System.out.println("Enter Gender: ");
        gender = Utils.getGender();
        mailID = Patron.getInputString("Enter Mail ID:");
        while (!Utils.isAValidMailID(mailID)) {
            mailID = Utils.getValidMailID();
        }
        String dateInput = Patron.getInputString("Enter Date Of Birth(dd-MM-yyyy): ");
        while(!Utils.isAValidDate(dateInput)){
            dateInput = Utils.getValidDate();
        }
        dateOfBirth = Utils.parseDate(dateInput);
        userID = User.getUserId();
        Resources.patrons.add(this);
        Resources.users.add(this);
        System.out.println("\nSign Up Successful!!\n\nUsername: " + userName + "\nUserID: " + userID);
    }

    public static void listUserFunctions(int userID) {
        //constants
        final int MYPROFILE = 1;
        final int SEARCHBOOKS = 2;
        final int BORROWBOOKS = 3;
        final int RETURNBOOKS = 4;
        final int DONATEBOOKS = 6;
        final int VIEWPAYMENT = 8;
        final int RENEWBOOKS = 5;
        final int BOOKLOST = 7;
        final int HISTORY = 9;
        final int PAYFINE = 10;

        int bookCount = 0;
        double fine = 0.0;
        int bookOnHoldCount = 0;

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
        while (userChoice != Constants.EXIT) {
            for (Patron patron : Resources.patrons) {
                if (patron.userID == userID) {
                    bookCount = patron.bookCount;
                    if ((patron.userID == userID)) {
                        patron.fine = patron.checkTotalFine();
                        fine = patron.fine;
                        bookOnHoldCount = patron.booksOnHold.size();
                    }
                }
            }
            System.out.println("\n\nEnter Appropriate Options:");
            if ((bookCount != 0) && (fine > 0) && (bookOnHoldCount != 0)) {
                System.out.println("\t\t1.My Profile - " + bookCount + " Books Borrowed," + bookOnHoldCount + " Books Requested \n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Renew Books\n\t\t6.Donate Books\n\t\t7.Book Lost\n\t\t8.View Payments\n\t\t9.Borrowed Book History\n\t\t10.Pay Fine");
            } else if ((bookCount == 0) && (fine > 0) && (bookOnHoldCount == 0)) {
                System.out.println("\t\t1.My Profile\n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Renew Books\n\t\t6.Donate Books\n\t\t7.Book Lost\n\t\t8.View Payments\n\t\t9.Borrowed Book History\n\t\t10.Pay Fine");
            } else if ((bookCount != 0) && (fine == 0.0) && (bookOnHoldCount == 0)) {
                System.out.println("\t\t1.My Profile - " + bookCount + " Books Borrowed \n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Renew Books\n\t\t6.Donate Books\n\t\t7.Book Lost\n\t\t8.View Payments\n\t\t9.Borrowed Book History");

            } else if ((bookCount == 0) && (fine == 0.0) && (bookOnHoldCount == 0)) {
                System.out.println("\t\t1.My Profile\n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Renew Books\n\t\t6.Donate Books\n\t\t7.Book Lost\n\t\t8.View Payments\n\t\t9.Borrowed Book History");

            } else if (((bookCount != 0) && (fine == 0.0) && (bookOnHoldCount != 0))) {
                System.out.println("\t\t1.My Profile - " + bookCount + " Books Borrowed," + bookOnHoldCount + " Books Requested \n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Renew Books\n\t\t6.Donate Books\n\t\t7.Book Lost\n\t\t8.View Payments\n\t\t9.Borrowed Book History");
            } else if (((bookCount == 0) && (fine != 0.0) && (bookOnHoldCount != 0))) {
                System.out.println("\t\t1.My Profile - " + bookOnHoldCount + " Books Requested \n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Renew Books\n\t\t6.Donate Books\n\t\t7.Book Lost\n\t\t8.View Payments\n\t\t9.Borrowed Book History\n\t\t10.Pay Fine");
            } else if (((bookCount == 0) && (fine == 0.0) && (bookOnHoldCount != 0))) {
                System.out.println("\t\t1.My Profile - " + bookOnHoldCount + " Books Requested \n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Renew Books\n\t\t6.Donate Books\n\t\t7.Book Lost\n\t\t8.View Payments\n\t\t9.Borrowed Book History");
            } else if ((bookCount != 0) && (fine != 0.0) && (bookOnHoldCount == 0)) {
                System.out.println("\t\t1.My Profile - " + bookCount + " Books Borrowed \n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Renew Books\n\t\t6.Donate Books\n\t\t7.Book Lost\n\t\t8.View Payments\n\t\t9.Borrowed Book History\n\t\t10.Pay Fine");
            }
            System.out.println("\n\t\t0.Exit\n\nEnter Your Choice: ");

            userChoice = Utils.getInt();
            Patron patron = new Patron();

            switch (userChoice) {

                case MYPROFILE:
                    patron.displayPatronProfile(userID);
                    break;

                case SEARCHBOOKS:
                    searchBooks(userID);
                    break;

                case BORROWBOOKS:
                    patron.borrowBooks(userID, 0, null);
                    break;

                case RETURNBOOKS:
                    patron.returnBooks(userID);
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
                    patron.renewBooks(userID);
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

                case PAYFINE:
                    for (Patron patron1 : Resources.patrons) {
                        if (patron1.fine > 0) {
                            patron1.payFine(patron1);
                            patron1.updateDueReturnDate();
                            patron1.returnBookAfterPayingFine();
                        } else {
                            System.out.println("No Fine To Pay!!");
                        }

                    }

                case Constants.EXIT:
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
                //patron.fine = patron.checkTotalFine();
                System.out.println("Books In Request List: " + patron.booksOnHold.size());
                if (!patron.booksOnHold.isEmpty()) {
                    patron.viewRequestedBooks();
                }
                System.out.println("Fine Amount: " + patron.fine);
            }
        }
    }

    public static void searchBooks(int userID) {
        //CONSTANTS FOR SWITCH CASE:
        final int SEARCHBYNAME = 1;
        final int SEARCHBYAUTHOR = 2;
        final int SEARCHBYGENRE = 3;
        ArrayList<Integer> nearestBookIDList = new ArrayList<>();
        int userChoice = 1;
        while (userChoice != Constants.EXIT) {
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

                case Constants.EXIT:
                    break;

                default:
                    System.out.println("Enter Valid Option!!");
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

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %15s %20s %20s %25s %25s ", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "BORROWED DATE", "RETURN DATE DUE");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.bookLocation.equals(Constants.BORROWED)) {
                System.out.printf("%5s %20s %15s %20s %20s %25s %25s ", borrowedBook.bookID, borrowedBook.bookName, borrowedBook.bookNo, borrowedBook.author, borrowedBook.publishedIn, Utils.printDate(borrowedBook.borrowedDate), Utils.printDate(borrowedBook.toBeReturnedDate));
                System.out.println();
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        }
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

        int loopExit = 1;
        int bookID;
        int bookMatch = 0;
        int confirm;

        for (Patron patron : Resources.patrons) {
            first:
            while (loopExit != Constants.EXIT) {
                if ((patron.userID == userID) && (patron.bookCount > 0)) {

                    patron.displayBorrowedBooks();
                    System.out.println("Enter the BookID to be renewed Or Enter 0 To Go To Main Page\n");
                    bookID = Utils.getInt();
                    if (bookID == 0) {
                        loopExit = Constants.EXIT;
                        break first;
                    }
                    for (Book book : Resources.books) {
                        for (Book bookCopy : patron.borrowedBooks) {

                            if ((bookCopy.bookID == bookID) && (bookCopy.bookLocation.equals(Constants.BORROWED))) {
                                if (bookCopy.bookRenewalCount < Rules.maxRenewalCount) {

                                    bookMatch++;
                                    System.out.println("Confirm The Renewal Of The Book\n\t\t1.Yes\n\t\t0.No");
                                    confirm = Utils.getInt();
                                    while ((confirm != Constants.YES) && (confirm != Constants.NO)) {
                                        System.out.println("Enter Valid Option: ");
                                        confirm = Utils.getInt();
                                    }
                                    if (confirm == Constants.NO) {
                                        System.out.println("Book Not Renewed!!");
                                        loopExit = Constants.EXIT;
                                        break first;
                                    }
                                    if (confirm == Constants.YES) {
                                        //patron.fine = patron.checkFine(bookCopy.bookID);
                                        patron.fine = patron.fine + Rules.renewalFee;
                                        boolean finePaid = patron.payRenewalFee(patron.fine);
                                        if (finePaid) {
                                            System.out.println("Book Return Date Extended!!");
                                            bookCopy.renewBookCopy();
                                            System.out.println("Renewed Return Date: " + Utils.printDate(bookCopy.toBeReturnedDate));
                                            patron.fine = 0;
                                            patron.updateDueReturnDate();
                                            patron.returnBookAfterPayingFine();
                                            loopExit = Constants.EXIT;
                                            break first;

                                        } else {
                                            System.out.println("Book Not Renewed!!!");
                                            loopExit = Constants.EXIT;
                                            break first;
                                        }
                                    }

                                } else if ((bookCopy.bookRenewalCount >= Rules.maxRenewalCount)) {
                                    System.out.println("Renewal Limit Reached");
                                    loopExit = Constants.EXIT;
                                    break first;
                                }
                            }
                        }
                    }
                } else if ((patron.userID == userID) && (patron.bookCount == 0)) {
                    System.out.println("No Books to Renew!!");
                    loopExit = Constants.EXIT;
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
                loopExit = Constants.EXIT;
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
            if (borrowedBook.bookLocation.equals(Constants.BORROWED) || borrowedBook.bookLocation.contains(Constants.RETURNED_FINENOTPAID)) {
                fine = borrowedBook.fineCalculator() + fine;
            }
        }
        return fine;
    }

    public static void getLostBooks(int userID) {
        if (!Resources.books.isEmpty()) {
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

        final int YES = 1;
        final int NO = 0;

        int loopExit = 1;
        int confirm;
        while (loopExit != Constants.EXIT) {
            Book returnedBook = new Book();
            int bookMatch = 0;
            for (Book bookLost : this.borrowedBooks) {
                if ((bookLost.bookID == bookID) && (bookLost.bookLocation.equals(Constants.BORROWED))) {
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

                        //this.fine = this.checkFine(bookLost.bookID);
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
                                        this.updateDueReturnDate();
                                        this.returnBookAfterPayingFine();

                                    }
                                }
                                book.bookCopies.remove(returnedBook);
                            }
                            this.bookCount--;
                            //this.fine = 0;
                            bookLost.bookLocation = Constants.LOST;
                            System.out.println("Fine Paid!!");
                            loopExit = Constants.EXIT;
                            continue;
                        } else {
                            System.out.println("Fine Not Paid!!");
                            loopExit = Constants.EXIT;
                            continue;
                        }
                    }
                }
            }
            //this.borrowedBooks.remove(returnedBook);

            if ((bookMatch == 0) && (loopExit != Constants.EXIT)) {
                System.out.println("\n\nEnter Proper Book ID!!\n\nEnter Options:\n\t\t1.Lost Book\n\t\t0.Main Page");
                loopExit = Utils.getInt();
                while ((loopExit != NO) && (loopExit != YES)) {
                    System.out.println("Enter Valid Option!!");
                    loopExit = Utils.getInt();
                }
                if (loopExit == 1) {
                    Patron.getLostBooks(this.userID);
                    loopExit = Constants.EXIT;
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

    public static void deleteApprovedBooks(Patron patron) {
        for (Book approvedBook : patron.approvedBooks) {
            for (Book book : Resources.books) {
                for (Book bookCopy : book.bookCopies) {
                    if (bookCopy.bookID == approvedBook.bookID) {
                        bookCopy.bookLocation = Constants.GODOWN;
                        bookCopy.isAvailable = true;
                        book.availableCopies++;
                    }
                }
            }
        }
        patron.booksOnHold.removeAll(patron.booksOnHold);
        patron.approvedBooks.removeAll(patron.approvedBooks);
        //patron.booksOnHoldCount = 0;
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
            System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK LOCATION");
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

    public void displayPatronHistory() {
        if (!borrowedBooks.isEmpty()) {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %20s %15s %20s %20s %25s %25s %25s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "BOOK STATUS", "BORROWED DATE" , "RETURNED DATE");
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for (Book borrowedBook : borrowedBooks) {
                if(borrowedBook.bookLocation.contains(Constants.RETURNED)){
                System.out.printf("%5s %20s %15s %20s %20s %25s %25s %25s", borrowedBook.bookID, borrowedBook.bookName, borrowedBook.bookNo, borrowedBook.author, borrowedBook.publishedIn, borrowedBook.bookLocation, Utils.printDate(borrowedBook.borrowedDate), Utils.printDate(borrowedBook.returnedDate));
                System.out.println();
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
                }else{
                    System.out.printf("%5s %20s %15s %20s %20s %25s %25s %25s", borrowedBook.bookID, borrowedBook.bookName, borrowedBook.bookNo, borrowedBook.author, borrowedBook.publishedIn, borrowedBook.bookLocation, Utils.printDate(borrowedBook.borrowedDate), "NIL");
                    System.out.println();
                    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");

                }
            }
        } else {
            System.out.println("No Books Found In Borrowed History!!");
        }
    }

    public static String getInputString(String message) {
        System.out.println(message);
        String inputString = Utils.getString();
        while (!Utils.checkNotNull(inputString)) {
            inputString = Utils.getNotNull();
        }

        return inputString;
    }

    public void returnBooks(int userID) {

        int loopExit = 1;
        int confirm;
        for (Patron patron : Resources.patrons) {
            if ((patron.userID == userID)) {
                if (patron.bookCount > 0) {
                    whileLoop:
                    while (loopExit != Constants.EXIT) {
                        //Book returnedBook = new Book();
                        int bookID;
                        int bookMatch = 0;
                        patron.displayBorrowedBooks();
                        System.out.println("Enter the BookID to be returned Or Enter 0 To Go To Main Page\n");
                        bookID = Utils.getInt();
                        if (bookID == 0) {
                            loopExit = Constants.EXIT;
                            continue;
                        }

                        for (Book bookCopy : patron.borrowedBooks) {
                            if ((bookCopy.bookID == bookID) && (bookCopy.isAvailable == false) && (bookCopy.bookLocation.equals(Constants.BORROWED))) {
                                bookMatch++;
                                if (patron.fine == 0) {
                                    System.out.println("Confirm The Return Of The Book\n\t\t1.Yes\n\t\t0.No");
                                    confirm = Utils.getInt();
                                    while ((confirm != Constants.NO) && (confirm != Constants.YES)) {
                                        System.out.println("Enter Valid Option: ");
                                        confirm = Utils.getInt();
                                    }
                                    if (confirm == Constants.NO) {
                                        System.out.println("Book Not Returned!!");
                                        loopExit = Constants.EXIT;
                                        break whileLoop;
                                    } else if (confirm == Constants.YES) {
                                        bookCopy.bookLocation = Constants.RETURNED;
                                        new Book().returnBook(bookID);
                                        patron.returnBookCompletely(bookID);
                                        patron.bookCount--;
                                        loopExit = Constants.EXIT;
                                        continue whileLoop;
                                    }
                                } else {
                                    System.out.println("Enter 1 To Return Book Without Paying Fine Or 2 To Pay Fine Along With The Book ");
                                    int userChoice = Utils.getInt();
                                    while ((userChoice != 1) && (userChoice != 2)) {
                                        System.out.println("Enter Valid Option: ");
                                        userChoice = Utils.getInt();
                                    }
                                    if (userChoice == 1) {
                                        System.out.println("Confirm The Return Of The Book\n\t\t1.Yes\n\t\t0.No");
                                        confirm = Utils.getInt();
                                        while ((confirm != Constants.NO) && (confirm != Constants.YES)) {
                                            System.out.println("Enter Valid Option: ");
                                            confirm = Utils.getInt();
                                        }
                                        if (confirm == Constants.NO) {
                                            System.out.println("Book Not Returned!!");
                                            loopExit = Constants.EXIT;
                                            break whileLoop;
                                        } else if (confirm == Constants.YES) {
                                            new Book().returnBook(bookID);
                                            patron.bookCount--;

                                            for (Book borrowedBook : patron.borrowedBooks) {
                                                if ((borrowedBook.bookID == bookID) && (borrowedBook.bookLocation.equals(Constants.BORROWED))) {
                                                    borrowedBook.bookLocation = Constants.RETURNED_FINENOTPAID;
                                                    borrowedBook.returnedDate = Resources.currentDate;
                                                }
                                            }
                                            System.out.println("Book Returned Successfully!!");
                                            loopExit = Constants.EXIT;
                                            continue whileLoop;
                                        }

                                    } else if (userChoice == 2) 
                                            System.out.println("Confirm The Return Of The Book\n\t\t1.Yes\n\t\t0.No");
                                            confirm = Utils.getInt();
                                            while ((confirm != Constants.NO) && (confirm != Constants.YES)) {
                                                System.out.println("Enter Valid Option: ");
                                                confirm = Utils.getInt();
                                            }
                                            if (confirm == Constants.NO) {
                                                System.out.println("Book Not Returned!!");
                                                loopExit = Constants.EXIT;
                                                break whileLoop;
                                            } else if (confirm == Constants.YES) {
                                                if (patron.payFine(patron)) {
                                                    System.out.println("Book Returned Successfully!!");
                                                    new Book().returnBook(bookID);
                                                    bookCopy.bookLocation = Constants.RETURNED;
                                                    patron.returnBookCompletely(bookID);
                                                    patron.updateDueReturnDate();
                                                    patron.bookCount--;

                                                } else {
                                                    new Book().returnBook(bookID);
                                                    patron.bookCount--;

                                                    for (Book borrowedBook : patron.borrowedBooks) {
                                                        if ((borrowedBook.bookID == bookID) && (borrowedBook.bookLocation.equals(Constants.BORROWED))) {
                                                            borrowedBook.bookLocation = Constants.RETURNED_FINENOTPAID;
                                                            borrowedBook.returnedDate = Resources.currentDate;
                                                        }
                                                    }
                                                    System.out.println("Book Returned Successfully Without Paying Fine!!\n\nFine Amount: Rs." + patron.fine);
                                                }
                                                loopExit = Constants.EXIT;
                                                continue whileLoop;
                                            }
                                }
                            }
                        }
                        //}
                        //}
                        if (bookMatch == 0) {
                            System.out.println("Enter Valid Book ID!!");
                            loopExit = 1;
                        }
                    }
                } else {
                    System.out.println("No Books To Return!!Borrow Some Books!!");
                    loopExit = Constants.EXIT;
                }
            }
        }
    }

    public boolean payFine(Patron patron) {
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
                    payment.paymentPurpose = "Fine for Late Return";
                    payment.userName = patron.userName;
                    payment.userID = patron.userID;
                    patron.individualPatronPayments.add(payment);
                    Resources.payments.add(payment);
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

    public void returnBookCompletely(int bookID) {
        int bookMatch = 0;
        for (Book borrowedBook : borrowedBooks) {
            if ((borrowedBook.bookID == bookID) && ((borrowedBook.bookLocation.equals(Constants.BORROWED)) || (borrowedBook.bookLocation.contains(Constants.RETURNED_FINENOTPAID)))) {
                bookMatch++;
                //borrowedBook.bookLocation = Constants.RETURNED;
                borrowedBook.toBeReturnedDate = Utils.parseDate("30-12-3000");
                borrowedBook.returnedDate = Resources.currentDate; 
            }
        }
    }

    public void updateDueReturnDate() {
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.bookLocation.equals(Constants.BORROWED)) {
                long difference = Resources.currentDate.getTime() - borrowedBook.toBeReturnedDate.getTime();
                long diffInDays = TimeUnit.MILLISECONDS.toDays(difference);
                if ((!borrowedBook.toBeReturnedDate.after(Resources.currentDate))) {
                    borrowedBook.toBeReturnedDate = Resources.currentDate;
                }
            }
        }
    }

    public void borrowBooks(int userID, int borrowRequest, ArrayList<Integer> nearestBookIDS) {

        String filterChoice = null;
        int loopExit = 1;
        boolean valid = true;
        while (loopExit != Constants.EXIT) {
            int bookID;
            int availableBooks = 0;
            if (borrowRequest == 0) {
                nearestBookIDS = (ArrayList) this.displayFirstAvailableBook().clone();
                if (!(nearestBookIDS.isEmpty())) {
                    filterChoice = getInputString(" Enter the Book ID Or Enter 'f' To Filter Books Or Press'0' To Go To Main Page");
                    while ((!((filterChoice.equalsIgnoreCase("F")) || ((filterChoice.charAt(0) >= '0') && (filterChoice.charAt(0) <= '9'))))) {
                        filterChoice = getInputString(" Enter Valid Option!!");
                    }
                    if (filterChoice.charAt(0) == 'f') {
                        Book.filterBooks();
                        filterChoice = getInputString("Enter Book ID From The Above List Or Enter 0 To Go To Main Page: ");
                    }
                    if (filterChoice.charAt(0) == '0') {
                        loopExit = Constants.EXIT;
                        continue;
                    }
                } else {
                    System.out.println("NO BOOKS AVAILABLE!!!");
                    loopExit = Constants.EXIT;
                    continue;
                }
            }
            if ((borrowRequest == 1)) {
                filterChoice = getInputString("Enter Book ID From The Above List");
            }
            if (!(nearestBookIDS.isEmpty()) || (borrowRequest == 1)) {
                while (!Utils.checkHasString(filterChoice)) {
                    filterChoice = Utils.getValidInt();
                }
                bookID = Integer.parseInt(filterChoice);
                if (bookID == 0) {
                    loopExit = Constants.EXIT;
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
                                        /*for (Book borrowedBook : patron.borrowedBooks) {
                                            patron.fine = patron.checkFine(borrowedBook.bookID);
                                        }*/

                                    }
                                    loopExit = Constants.EXIT;
                                    valid = false;
                                } else if (patron.fine > 0) {
                                    System.out.println("Pay Fine To Borrow New Books!!");
                                    if (patron.payFine(patron)) {
                                        patron.returnBookAfterPayingFine();
                                        patron.updateDueReturnDate();
                                    } else {
                                        System.out.println("Fine Not Paid!!");
                                    }
                                    loopExit = Constants.EXIT;
                                    valid = false;
                                } else if (patron.booksOnHold.size() >= 2) {
                                    System.out.println("Requested Book List Full!!");
                                    loopExit = Constants.EXIT;
                                    valid = false;
                                } else if (((patron.userID) == userID) && (patron.bookCount > 1)) {
                                    System.out.println("Return Books to Borrow new Books");
                                    patron.returnBooks(userID);
                                    Shelf.allotLocationForBooks();
                                    loopExit = Constants.EXIT;
                                    valid = false;
                                } else {
                                    loopExit = Constants.EXIT;
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
                            loopExit = Constants.EXIT;
                            valid = false;
                        }
                    }
                }
            } else if ((nearestBookIDS.isEmpty()) && (borrowRequest == 0)) {
                System.out.println("No Books Available!!");
                loopExit = Constants.EXIT;
            }
        }
    }

    public static void borrowRequestApproved(Patron patron) {

        patron.fine = patron.checkTotalFine();

        int bookID;
        int bookMatch = 0;

        final int BORROW = 1;
        final int MAINPAGE = 0;

        ArrayList<Book> booksToRemoveFromApprovedList = new ArrayList<>();
        int confirmBorrow;

        whileLoop:
        while (true) {
            System.out.println("\n\n------Requested Book Approved!!!-------\n\n");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN", "GENRE", "BOOK LOCATION");
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

            for (Book approvedBook : patron.approvedBooks) {
                approvedBook.displaySingleBookCopy();
            }
            System.out.println("\nEnter The BookID You Want To Borrow From The Above List:");
            bookID = Utils.getInt();
            approvedBookLoop:
            for (Book approvedBookCopy : patron.approvedBooks) {
                if ((patron.bookCount <= 1) && (patron.fine == 0)) {
                    if (approvedBookCopy.bookID == bookID) {
                        bookMatch++;
                        for (Book book : Resources.books) {
                            for (Book bookCopy : book.bookCopies) {
                                if (bookCopy.bookID == approvedBookCopy.bookID) {
                                    bookCopy.setDateLimit();
                                    bookCopy.bookLocation = Constants.BORROWED;
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

                } else if (patron.fine > 0.0) {
                    System.out.println("Pay Fine To Borrow Your Approved Books!!!\n\t\tEnter 1.To Pay Fine\n\t\t 0.To Main Page\n\t-------NOTE: IF YOU PROCEED TO MAIN PAGE YOUR REQUESTED BOOKS WILL BE REMOVED FROM YOUR APPROVED LIST-------");
                    int userChoice = Utils.getInt();
                    while ((userChoice != 0) && (userChoice != 1)) {
                        System.out.println("Enter Valid Option!!");
                        userChoice = Utils.getInt();
                    }
                    if (userChoice == 0) {
                        Patron.deleteApprovedBooks(patron);
                        Shelf.allotLocationForBooks();
                        System.out.println("Requested Books Removed From Approved List");
                        break whileLoop;
                    } else if (userChoice == 1) {
                        if (patron.payFine(patron)) {
                            patron.updateDueReturnDate();
                            patron.returnBookAfterPayingFine();
                            Patron.borrowRequestApproved(patron);
                            Shelf.allotLocationForBooks();
                            break whileLoop;
                        } else {
                            System.out.println("Requested Books Removed From Approved List");
                            break whileLoop;
                        }

                    }

                } else if (patron.bookCount > 1) {
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
                            Shelf.allotLocationForBooks();
                            break whileLoop;
                        } else {
                            Patron.deleteApprovedBooks(patron);
                            Shelf.allotLocationForBooks();
                            System.out.println("Requested Books Removed From Approved List");
                            break whileLoop;
                        }
                    } else if (userChoice == 0) {
                        Patron.deleteApprovedBooks(patron);
                        Shelf.allotLocationForBooks();
                        System.out.println("Requested Books Removed From Approved List");
                        break whileLoop;
                    }
                }
            }
            patron.approvedBooks.removeAll(booksToRemoveFromApprovedList);
            if (patron.approvedBooks.isEmpty()) {
                break whileLoop;
            }
            if (bookMatch == 0) {
                System.out.println("Enter Valid Book ID!!");
                continue whileLoop;
            }
        }

    }

    public void returnBookAfterPayingFine() {
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.bookLocation.contains(Constants.RETURNED_FINENOTPAID)) {
                borrowedBook.bookLocation = Constants.RETURNED;
            }
        }
    }

    public void viewRequestedBooks() {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %15s %20s %20s ", "BOOK ID", "BOOK NAME", "BOOK NO", "AUTHOR", "PUBLISHED IN");
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------");
        for (Book requestedBook : booksOnHold) {
            System.out.printf("%5s %20s %15s %20s %20s ", requestedBook.bookID, requestedBook.bookName, requestedBook.bookNo, requestedBook.author, requestedBook.publishedIn);
            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------");

        }

    }

}
