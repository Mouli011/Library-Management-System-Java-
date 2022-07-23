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
    
    static int patronCount =0;
    
    protected int bookCount = 0;
    protected double totalFine = 0.0;
    ArrayList<Book> borrowedBooks = new ArrayList<Book>();
    protected double fine = 0.0;
    ArrayList<Payment> individualPatronPayments = new ArrayList<Payment>();
    int booksOnHoldCount=0;
    int previousBooksOnHoldCount =0;
    protected HashSet<Book> approvedBooks = new HashSet<Book>();
    protected HashSet<Book> booksOnHold = new HashSet<Book>();
    protected int renewalCount=0;
    String mailID;
    
    
    public void displayOption()
    {
        int userChoice = 1;
        
        while(LibraryManagementSystem.toBoolean(userChoice))
        {
        System.out.println("\n\n\nEnter Options:");
        System.out.println("\t\t1.SIGN UP - If you are a new user\n\t\t2.SIGN IN - If you have a account\n\t\t0.Exit");
        System.out.println("\nEnter your Choice: ");
        userChoice = this.chosenOption(userChoice);
        }
    }
    public static int chosenOption(int userChoice)
    {
        //constants
        final int REGISTER = 1;
        final int SIGNIN = 2;
        final int EXIT = 0;
        
        int userID;
        
        
        userChoice = Utils.getInt();
        
        
        switch(userChoice)
        {
            case REGISTER:
                Patron p = new Patron();
                p.registerPatron();
                break;
                
            case SIGNIN:
                if((userID=User.signIn())>0)
                    Patron.afterSignInFunctions(userID);
                else
                    System.out.println("Invalid Username or password");
                break;
                
            case EXIT:
                break;
                
            default:
                System.out.println("Enter valid option");
                break;
        }
        
        return userChoice;
    }
    
    public void registerPatron()
    {
        try
        {
        System.out.println("Enter Username: ");
        userName = Utils.getString();
        userName = Utils.checkNotNull(userName);
        System.out.println("Enter password: ");
        //char[] passWord = console.readPassword();
        password = Utils.getString();
        //password = Utils.charToString(passWord);
        password = Utils.checkNotNull(password);
        
        System.out.println("Enter Phone Number: ");
        phoneNumber = Utils.getString();
        phoneNumber = Utils.checkNotNull(phoneNumber);
        phoneNumber = Utils.checkPhoneNumberConstraint(phoneNumber);
        
        System.out.println("Enter Gender: ");
        /*gender = Utils.getString();
        gender = Utils.checkNotNull(gender);*/
        gender = Utils.getGender();

        System.out.println("Enter Mail ID: ");
        mailID = Utils.getString();
        mailID = Utils.checkNotNull(mailID);
        mailID = Utils.checkMailID(mailID);
        //mailID = Utils.checkMailID(mailID);
        
        System.out.println("Enter Date Of Birth(dd-MM-yyyy): ");
        String dateInput = Utils.getString();
        dateInput = Utils.checkNotNull(dateInput);
        dateOfBirth = Utils.parseDate(dateInput);
       
        userID = User.getUserId();
        
        Resources.patrons.add(this);
        Resources.users.add(this);
        
        System.out.println("\nSign Up Successful!!\n\nUsername: "+userName+"\nUserID: "+userID);
        patronCount++;
        }
        
        catch(Exception e)
        {
            System.out.println("\n\nError occured: " +e);
        }
        
    }
    public static void afterSignInFunctions(int userID)
    {
        //constants
        final int MYPROFILE =1;
        final int SEARCHBOOKS=2; 
        final int BORROWBOOKS=3;    
        final int RETURNBOOKS=4;         
        final int DONATEBOOKS=5;   
        final int VIEWPAYMENT=6;
        final int RENEWBOOKS = 7;
        final int BOOKLOST=8;
        final int EXIT=0;        
                
                
        for(User user: Resources.users)
        {
            if(user.userID==userID)
            {
            System.out.println("Sign In Successfull!!");
            System.out.println("Welcome "+user.userName);
            }
            
        }
        for(Patron patron:Resources.patrons)
        {
            if((patron.userID==userID)&&(patron.booksOnHoldCount!=patron.previousBooksOnHoldCount))
            {
                Patron.borrowRequestApproved(patron);
            }
        }
        int userChoice = 1;
        while(LibraryManagementSystem.toBoolean(userChoice))
        {
            System.out.println("\n\nEnter Appropriate Options:");
            System.out.println("\t\t1.My Profile\n\t\t2.Search Books\n\t\t3.Borrow Books\n\t\t4.Return Books\n\t\t5.Donate Books\n\t\t6.View Payments\n\t\t7.Renew Books\n\t\t8.Book Lost");
            System.out.println("\n\t\t0.Exit\n\nEnter Your Choice: ");
            
            userChoice = Utils.getInt();
            
            switch(userChoice)
            {
                case MYPROFILE:
                    Patron patron = new Patron();
                    patron.displayPatronProfile(userID);
                    break;
                
                case SEARCHBOOKS:
                    Patron.searchBooks(userID);
                    break;
                
                case BORROWBOOKS:
                    Patron borrowPatron = new Patron();
                    borrowPatron.borrowBooks(userID,0);
                    break;
                
                case RETURNBOOKS:
                    Patron returnPatron = new Patron();
                    returnPatron.returnBooks(userID);
                    Shelf.assignShelf();
                    break;
                
                case DONATEBOOKS:
                     Book.displayForAddBooks();
                     Shelf.assignShelf();
                    break;
                
                
                    
                case VIEWPAYMENT:
                    for(Patron viewPatron:Resources.patrons)
                    {
                        if(viewPatron.userID==userID)
                        {
                        viewPatron.displayIndividualPatronPayment(userID);
                        }
                    }
                    
                    break;
                    
                case RENEWBOOKS:
                    Patron patronRenew = new Patron();
                    patronRenew.renewBooks(userID);
                    break;
                    
                case BOOKLOST:
                    Patron.bookLostOptionDisplay(userID);
                    break;
                    
                case EXIT:
                    break;
                
                default:
                System.out.println("Enter valid option");
                break;
            }
        }    
    }
    
    public void displayPatronProfile(int userID){
        
        for(Patron patron: Resources.patrons)
        {
            if(patron.userID==userID)
            {
            System.out.println("UserName: "+patron.userName);
            System.out.println("Phone Number: "+patron.phoneNumber);
            System.out.println("Gender: "+patron.gender);
            System.out.println("Mail ID: "+patron.mailID);
            System.out.println("Date Of Birth: "+Utils.printDate(patron.dateOfBirth));
            System.out.println("\n\nNumber of Books Borrowed: "+patron.bookCount);
            if(patron.bookCount!=0)
            {
                patron.displayBorrowedBooks();
            }
            
            patron.fine=patron.checkTotalFine();
            System.out.println("Fine Amount: "+patron.fine);
            
            }
            
        }
    }

    public static void searchBooks(int userID)
    {
        //CONSTANTS FOR SWITCH CASE:
        final int SEARCHBYNAME=1;
        final int SEARCHBYAUTHOR=2;
        final int SEARCHBYGENRE =3;
        final int EXIT = 0;
        
        int bookFound=0;
        String bookName;
        int userChoice = 1;
        while(LibraryManagementSystem.toBoolean(userChoice))
        {
        System.out.println("Enter Appropriate Option: ");
        System.out.println("\t\t1.Search By Book Name\n\t\t2.Search By Author Name\n\t\t3.Search By Genre\n\t\t0.Main Page");
        System.out.println("Your Option: ");
        userChoice = Utils.getInt();
        switch(userChoice)
        {
            case SEARCHBYNAME:
                System.out.println("Enter Book Name: ");
                bookName = Utils.getString();
                for(Book book:Resources.books)
                {
                    for(Book bookCopy:book.bookCopies)
                    {
                    if(((book.bookName.replaceAll("\\s+","").toLowerCase()).contains(bookName.replaceAll("\\s+","").toLowerCase()))) 
                    {
                        
                        if(bookFound==0)
                        {
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"BOOK LOCATION");  
                        System.out.println();  
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------"); 
                        bookCopy.displaySingleBookCopy();
                        }
                        if(bookFound>0)
                        {
                            bookCopy.displaySingleBookCopy();
                        }
                    bookFound++;
                }
                }
                }
       
               if(bookFound==0)
                {
                System.out.println("No Books Found!!");
                userChoice=1;
                continue;
                }
                
                break;
            case SEARCHBYAUTHOR:
                bookFound = Book.filterByAuthorName();
                if(bookFound==0)
                {
                userChoice=1;
                continue;
                }
                
                break;
            case SEARCHBYGENRE:
                bookFound = Book.filterByGenre();
                if(bookFound==0)
                {
                userChoice=1;
                continue;
                }
                
                break;
            case EXIT:
                break;
            default:
                System.out.println("Enter Valid Option!!");
                userChoice=Utils.getInt();
                continue;
        }   
        bookFound=0;
        if(userChoice>0)
        {
        System.out.println("Enter Options:\n\t\t1.Borrow Book\n\t\t2.Search Page\n\t\t0.Main Page");
        System.out.println("Enter Your Choice: ");
        userChoice = Utils.getInt();
        while((userChoice!=0)&&(userChoice!=1)&&(userChoice!=2))
        {
           System.out.println("Enter Vaild Option:");
           userChoice = Utils.getInt();
        }   
        if(userChoice==1)
        {
            new Patron().borrowBooks(userID, userChoice);
        }
        }
    }
    }
    /*public void borrowBooks(int userID,int borrowRequest)
    {
        int bookNo;
        int availableBooks=0;
        
        if(borrowRequest==0)
        {
            System.out.println("-----------------------------------------------------------------------------------------------------------------");  
            System.out.printf("%5s %20s %20s %20s %20s", "BOOK NO", "BOOK NAME", "AUTHOR", "PUBLISHED IN", "AVAILABLE COPIES");  
            System.out.println();  
            System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
            //System.out.println("Book No\t\tBook Name\t\tAuthor\t\tPublisher\t\tAvaialable Copies\t\t");

                for(Book book:Resources.books)
                {
                    if(book.availableCopies>0)
                    {
                        availableBooks++;
                        book.displaySingleBook();
                    }
                    
                }
        }
                if((availableBooks>0)||(borrowRequest==1))
                {
                System.out.println("Enter the Number of the Book which you need from the above List: ");
                bookNo = Utils.getInt();
                for(Patron patron: Resources.patrons)
                {
            if(((patron.userID)==userID)&&(patron.bookCount<=1)&&(patron.fine==0))
            {
                
                
                Book addedBook = new Book();
                addedBook=Book.assignBook(bookNo,borrowedBooks);
                if(addedBook.bookID>=0){
                patron.bookCount++;
                patron.borrowedBooks.add(addedBook);
                System.out.println("Book Borrowed Succussfully!!!");
                patron.displayBorrowedBooks();
                for(Book borrowedBook:patron.borrowedBooks)  
                {
                    patron.fine = patron.checkFine();
                }
                
                }
                }
                
                
            
            else if(((patron.userID)==userID)&&(patron.bookCount>0)&&(patron.fine>0))
            {
                System.out.println("Return Books and Pay Fine to Borrow new Books");
                patron.returnBooks(userID );
            }
            else if(((patron.userID)==userID)&&(patron.bookCount==2)&&(patron.fine>=0))
            {
                System.out.println("Return Books to Borrow new Books");
                patron.returnBooks(userID );
            }
                }
                }
                else if((availableBooks==0)&&(borrowRequest==0))
            {
                System.out.println("No Books Available!!");
            }
            
            
        
    }*/
    
    public void borrowBooks(int userID,int borrowRequest)
    {
        int loopExit=1;
        while(LibraryManagementSystem.toBoolean((loopExit)))
        {
        int bookID;
        int availableBooks=0;
        
        if(borrowRequest==0)
        {
             System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
             System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"BOOK LOCATION");  
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
            if(((patron.userID)==userID)&&(patron.bookCount<=1)&&(patron.fine==0)&&(patron.booksOnHoldCount<=1))
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
                Shelf.assignShelf();
                loopExit=0;
                continue;
            }
            else if(((patron.userID)==userID)&&(patron.bookCount==2)&&(patron.fine>=0))
            {
                System.out.println("Return Books to Borrow new Books");
                patron.returnBooks(userID );
                Shelf.assignShelf();
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
    }
    /*public void addBookToBorrowedBooks(int bookNo,int bookID)
    {
        for(Book book:Resources.books)
                {
                    if(book.bookNo==bookNo)
                    {
                        for(Book bookCopy:book.bookCopies)
                        {
                            if(bookCopy.bookID==bookID)
                                borrowedBooks.add(bookCopy);
                        }
                    }
                }
    }*/
    public void displayBorrowedBooks()
    {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");  
        System.out.printf("%5s %20s %15s %20s %20s %25s %25s ", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "BORROWED DATE","RETURN DATE DUE");  
        System.out.println();  
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");  
        //System.out.println("Book ID\t\tBook Name\t\tBook No\t\tAuthor\t\tPublisher\t\tBorrowedDate\t\tLast Date To Return the Book");
        for(Book borrowedBook:borrowedBooks)
        {
        System.out.printf("%5s %20s %15s %20s %20s %25s %25s ",borrowedBook.bookID, borrowedBook.bookName,borrowedBook.bookNo, borrowedBook.author, borrowedBook.publishedIn, Utils.printDate(borrowedBook.borrowedDate),Utils.printDate(borrowedBook.toBeReturnedDate));  
        System.out.println();  
        System.out.println("------------------------------------------------------------------------------------------------------------------------------"); 
         //System.out.println(borrowedBook.bookID+"\t\t"+borrowedBook.bookName+"\t\t"+borrowedBook.bookNo+"\t\t\t"+borrowedBook.author+"\t\t"+borrowedBook.publishedIn+"\t\t"+Utils.printDate(borrowedBook.borrowedDate)+"\t\t"+Utils.printDate(borrowedBook.toBeReturnedDate));   
        }
    }
    public void returnBooks(int userID)
    {
        int loopExit =1;
        int confirm;
         for(Patron patron:Resources.patrons)
        {
            if((patron.userID==userID)&&(patron.bookCount>0))
            {
        while(LibraryManagementSystem.toBoolean(loopExit))
        {
        Book returnedBook = new Book();
        int bookID;
        int bookMatch =0;
                patron.displayBorrowedBooks();
                System.out.println("Enter the ID of the book to be returned: \n\t\t0.Main Page\n");
                bookID = Utils.getInt();
                if(bookID==0)
                {
                    loopExit=0;
                    continue;
                    
                }
                
                for(Book book:Resources.books)
                {
                    for(Book bookCopy:book.bookCopies)
                    {
                        if((bookCopy.bookID==bookID)&&(bookCopy.isAvailable==false))
                        {
                            patron.fine = patron.checkFine(bookCopy.bookID);
                            bookMatch++;
                            if(patron.fine==0)
                            {
                                System.out.println("Confirm The Return Of The Book\n\t\t1.Yes\n\t\t0.No");
                                confirm = Utils.getInt();
                                while((confirm!=0)&&(confirm!=1))
                                {
                                    System.out.println("Enter Valid Option: ");
                                    confirm = Utils.getInt();
                                }
                                if(confirm==0)
                                {
                                    System.out.println("Book Not Returned!!");
                                    loopExit =0;
                                    continue;
                                }
                                if(confirm==1)
                                {
                                bookCopy.returnBookCopy();
                                book.availableCopies++;
                                patron.bookCount--;
                                returnedBook =bookCopy;
                                patron.borrowedBooks.remove(returnedBook);
                                bookCopy.bookLocation="";
                                System.out.println("Book Returned Successfully!!");
                                loopExit=0;
                                continue;
                                }
                                
                            }
                            else 
                            {
                                System.out.println("Confirm The Return Of The Book\n\t\t1.Yes\n\t\t0.No");
                                confirm = Utils.getInt();
                                while((confirm!=0)&&(confirm!=1))
                                {
                                    System.out.println("Enter Valid Option: ");
                                    confirm = Utils.getInt();
                                }
                                if(confirm==0)
                                {
                                    System.out.println("Book Not Returned!!");
                                    loopExit =0;
                                    continue;
                                }
                                if(confirm==1)
                                {
                                boolean finePayCheck = patron.payFine(patron.fine,userID);
                                if(finePayCheck)
                                {
                                  
                                   bookCopy.returnBookCopy(); 
                                   book.availableCopies++;
                                   patron.bookCount--;
                                   patron.fine = 0;
                                   returnedBook =bookCopy;
                                   patron.borrowedBooks.remove(returnedBook);
                                   bookCopy.bookLocation="";
                                   System.out.println("Book Returned Successfully!!");
                                   loopExit=0;
                                    continue;
                                }
                                else
                                {
                                    System.out.println("Book Not Returned!!");
                                    loopExit=0;
                                    continue;
                                }
                            }
                            }
                        }
                      
                    }
                }
                
                
                if(bookMatch==0)
                {
                    System.out.println("Enter Valid Book ID!!");
                    new Patron().returnBooks(userID);
                    loopExit=0;
                    continue;
                    
                }
                
            }
            
        }
            else if((patron.userID==userID)&&(patron.bookCount==0))
                {
                System.out.println("No Books to Return!!");
                loopExit=0;
                continue;
                }
        }
    }
    public boolean payFine(double fine,int userID)
    {
        
        //constants
        final int TOPAY = 1;
        final int CANCEL =0;       
        
        
        
        boolean paymentResult = false;
        
        System.out.println("Amount To Be Paid: "+fine);
        System.out.println("Enter appropriate Option");
        System.out.println("\t\t1.Proceed To Pay\n\t\t0.Cancel");
        int userChoice;
        userChoice = Utils.getInt();
        switch(userChoice)
        {
            case TOPAY:
                Payment payment = new Payment();
                paymentResult = payment.performPayment(this);
                if(paymentResult)
                {
                    for(Patron patron:Resources.patrons)
                    {
                        if(patron.userID==userID)
                        {
                        payment.paymentPurpose ="Fine for Late Return";
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
    public void displayIndividualPatronPayment(int userID)
    {
        if(!individualPatronPayments.isEmpty())
        {
        System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
        System.out.format("%5s %20s %15s %25s %25s", "PAYMENT ID", "PAYMENT DATE", "AMOUNT PAID","PAYMENT PLATFORM", "PAYMENT PURPOSE");  
        System.out.println();  
        System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
        
        //System.out.println("PaymentID\t\tPayment Platform\t\tPayment Purpose\t\tAmount Paid\t\tPayment Date");
        for(Payment payment:individualPatronPayments)
        {
            System.out.format("%5s %20s %15s %25s %25s", payment.paymentID, Utils.printDate(payment.paymentDate), payment.paymentAmount,payment.paymentPlatform, payment.paymentPurpose);  
            System.out.println();  
            System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
            //System.out.println(payment.paymentID+"\t\t"+payment.paymentPlatform+"\t\t"+payment.paymentPurpose+"\t\t"+payment.paymentAmount+"\t\t"+Utils.printDate(payment.paymentDate));
        }
        
       
        
        }
        else
        {
            System.out.println("No Payments Done");
        }
    }
    public void renewBooks(int userID)
    {
        int loopExit=1;
        
        int bookID;
        int bookMatch=0;
        int confirm;
        
        for(Patron patron:Resources.patrons)
        {
            first:
            while(LibraryManagementSystem.toBoolean(loopExit))
        {
            if((patron.userID==userID)&&(patron.renewalCount<Rules.maxRenewalCount)&&(patron.bookCount>0))
            {
                
                patron.displayBorrowedBooks();
                System.out.println("Enter the ID of the book to be renewed: \n\t\t0.Main Page\n");
                bookID = Utils.getInt();
                if(bookID==0)
                {
                    loopExit=0;
                    break first;
                }
                for(Book book:Resources.books)
                {
                    for(Book bookCopy:patron.borrowedBooks)
                    {
                        if(bookCopy.bookID==bookID)
                        {
                            
                            bookMatch++;
                            System.out.println("Confirm The Renewal Of The Book\n\t\t1.Yes\n\t\t0.No");
                                confirm = Utils.getInt();
                                while((confirm!=0)&&(confirm!=1))
                                {
                                    System.out.println("Enter Valid Option: ");
                                    confirm = Utils.getInt();
                                }
                                if(confirm==0)
                                {
                                    System.out.println("Book Not Renewed!!");
                                    loopExit =0;
                                    break first;
                                }
                                if(confirm==1)
                                {   
                            patron.fine = patron.checkFine(bookCopy.bookID);
                            patron.fine = patron.fine + Rules.renewalFee;
                            boolean finePayCheck = patron.payRenewal(patron.fine);
                                if(finePayCheck)
                                {
                                   System.out.println("Book Return Date Extended!!");
                                   patron.renewalCount++;
                                   bookCopy.renewBookCopy();
                                   System.out.println("Renewed Return Date: "+Utils.printDate(bookCopy.toBeReturnedDate));
                                   patron.fine = 0;
                                   loopExit=0;
                                   break first;
                                   
                                }
                                else
                                {
                                    System.out.println("Book Not Renewed!!!");
                                    loopExit=0;
                                    break first;
                                }
                                }
                        
                        }
                    }
                }
               }
                
            else if((patron.userID==userID)&&(patron.renewalCount<Rules.maxRenewalCount)&&(patron.bookCount==0))
            {
                System.out.println("No Books to Renew!!");
                loopExit=0;
                break first;
            }
            else if((patron.userID==userID)&&(patron.renewalCount<=Rules.maxRenewalCount))
            {
                System.out.println("Renewal Limit Reached");
                loopExit=0;
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
            loopExit=0;
        }
        }
    }
     public boolean payRenewal(double fine)
    {
        
        //constants
        final int TOPAY = 1;
        final int CANCEL =0;        
        
        boolean paymentResult = false;
        
        System.out.println("Amount To Be Paid: "+fine);
        System.out.println("\t\tFine: "+(fine-Rules.renewalFee)+"\n\t\tRenewal Fee: "+Rules.renewalFee);
        System.out.println("Enter appropriate Option");
        System.out.println("\t\t1.Proceed To Pay\n\t\t0.Cancel");
        int userChoice;
        userChoice = Utils.getInt();
        switch(userChoice)
        {
            case TOPAY:
                Payment payment = new Payment();
                paymentResult = payment.performPayment(this);
                if(paymentResult==true)
                {
                    for(Patron patron:Resources.patrons)
                    {
                        if(patron.userID==userID)
                        {
                        payment.paymentPurpose ="Return Date Renewal";
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
     public double checkFine(int bookID)
     {
         
         double fine = 0.0;
         
                 this.fine = 0.0;
                 for(Book borrowedBook:borrowedBooks)
                 {   
                     if(borrowedBook.bookID==bookID)
                     {
                 fine = this.fine +borrowedBook.fineCalculator();
                     }
                  }
             
         return fine;
     }
     public double checkTotalFine()
     {
         
         double fine = 0.0;
         
                 this.fine = 0.0;
                 for(Book borrowedBook:borrowedBooks)
                 {   
                 fine = borrowedBook.fineCalculator() +fine;
                  }
             
         return fine;
     }
     
     public static void bookLostOptionDisplay(int userID)
     {
         if(Book.bookCount!=0)
         {
         for(Patron patron:Resources.patrons)
         {
             if((patron.userID==userID)&&(patron.bookCount>0))
             {
                 patron.displayBorrowedBooks();
                 System.out.println("Enter ID of the Book Lost from Above: ");
                 int bookID = Utils.getInt();
                 patron.bookLostAction(bookID);
                 
             }
             else if((patron.userID==userID)&&(patron.bookCount==0))
             {
                 System.out.println("No Books Borrowed to Loose");
             }
         }
     }
     
     else
     {
         System.out.println("No Books to Loose!!");
     }
     
    }
     public void bookLostAction(int bookID)
     {
         int loopExit=1;
         int confirm;
         while(LibraryManagementSystem.toBoolean(loopExit))
         {
         Book returnedBook = new Book();
         int bookMatch =0;
         for(Book bookLost:this.borrowedBooks)
         {
             if(bookLost.bookID==bookID)
             {
                 System.out.println("Confirm To Proceed Further:\n\t\t1.Yes\n\t\t0.No");
                 confirm = Utils.getInt();
                 while((confirm!=0)&&(confirm!=1))
                 {
                  System.out.println("Enter Valid Option: ");
                  confirm = Utils.getInt();
                 }
                 if(confirm==0)
                 {
                    System.out.println("Fine Not Paid!!");
                    loopExit =0;
                    continue;
                 }
                 if(confirm==1)
                 {   
                 
                 this.fine = this.checkFine(bookLost.bookID);
                 this.fine = this.fine + bookLost.bookPrice;
                 bookMatch++;
                 boolean finePayCheck = this.payBookLostFine(this.fine,bookLost.bookPrice);
                 if(finePayCheck)
                 {
                            //bookLost.returnBookCopy(); 
                            returnedBook =bookLost;
                            for(Book book:Resources.books)
                            {
                                if(book.bookNo==bookLost.bookNo)
                                        book.totalCopies--;
                                        Resources.lostBooks.add(returnedBook);
                                        Resources.lostBookCount++;
                                        book.bookCopies.remove(returnedBook);
                            }
                            this.bookCount--;
                            this.fine = 0;
                            
                            System.out.println("Fine Paid!!");
                            loopExit=0;
                            continue;
                            }
                else
                {
                            System.out.println("Fine Not Paid!!");
                            loopExit=0;
                            continue;
                }
             }
             }
             
                 
         }
         this.borrowedBooks.remove(returnedBook);
         
         if((bookMatch==0)&&(loopExit!=0))
         {
             System.out.println("\n\nEnter Proper Book ID!!\n\nEnter Options:\n\t\t1.Lost Book\n\t\t0.Main Page");
             loopExit = Utils.getInt();
             while((loopExit!=0)&&(loopExit!=1))
             {
                 System.out.println("Enter Valid Option!!");
                 loopExit = Utils.getInt();
             }
             if(loopExit==1)
             {
             Patron.bookLostOptionDisplay(this.userID);
             loopExit=0;
             }
         }
     }
     }
     
     public boolean payBookLostFine(double fine,double bookPrice)
    {
        
        //constants
        final int TOPAY = 1;
        final int CANCEL =0;        
        
        boolean paymentResult = false;
        
        System.out.println("Amount To Be Paid: "+fine);
        System.out.println("\t\tFine: "+(fine-bookPrice)+"\n\t\tLost Book Fee "+bookPrice);
        System.out.println("Enter appropriate Option");
        System.out.println("\t\t1.Proceed To Pay\n\t\t0.Cancel");
        int userChoice;
        userChoice = Utils.getInt();
        switch(userChoice)
        {
            case TOPAY:
                Payment payment = new Payment();
                paymentResult = payment.performPayment(this);
                if(paymentResult==true)
                {
                    for(Patron patron:Resources.patrons)
                    {
                        if(patron.userID==userID)
                        {
                        payment.paymentPurpose ="Book Lost Fine";
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
     
     public static void borrowRequestApproved(Patron patron)
     {
         whileLoop:
         while(true)
         {
         if((patron.bookCount<=1)&&(patron.fine==0))
         {
             approvedBookLoop:
         for(Book approvedBook:patron.approvedBooks)
         {
             for(Book book:Resources.books)
             {
                 for(Book bookCopy:book.bookCopies)
                 {
                     if(bookCopy.bookID==approvedBook.bookID)
                     {
                         System.out.println("\n\n------Requested Book Approved!!!-------\n\n");
                         System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
                         System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"BOOK LOCATION");  
                         System.out.println();  
                         System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
                         bookCopy.displaySingleBookCopy();
                         bookCopy.setDateLimit();
                         patron.borrowedBooks.add(bookCopy);
                         System.out.println("\n\n---------BOOK BORROWED SUCCESSFULLY------\n\n");
                         patron.displayBorrowedBooks();
                         System.out.println("\n\n");
                         bookCopy.bookLocation="BORROWED";
                         for(Book borrowedBook:patron.borrowedBooks)  
                         {
                            patron.fine = patron.checkFine(borrowedBook.bookID);
                            
                         }
                         patron.previousBooksOnHoldCount--;
                         patron.bookCount++;
                         break approvedBookLoop;
                         
                     }
                 }
             }
             
         }
         
         patron.approvedBooks.removeAll(patron.borrowedBooks);
         int previousBooksOnHoldCount=Patron.getPreviousBooksOnHoldCount(patron);
         if(previousBooksOnHoldCount<=0)
         {
             break whileLoop;
         }
         continue whileLoop;
         }
             else if((patron.bookCount>1)||(patron.fine!=0))
         {
             System.out.println("Return Books And Pay Fine To Borrow Books!!!\n\t\tEnter 1.To Return Books\n\t\t 0.To Main Page\n\t-------NOTE: IF YOU PROCEED TO MAIN PAGE YOUR APPROVED BOOKS WILL BE REMOVED FROM YOUR APPROVED LIST-------");
             int userChoice = Utils.getInt();
             while((userChoice!=1)&&(userChoice!=0))
             {
                 System.out.println("Enter Valid Options: ");
                 userChoice = Utils.getInt();
             }
             if(userChoice==1)
             {
                 patron.returnBooks(patron.userID);
                 if((patron.bookCount<=1)&&(patron.fine==0))
                 Patron.borrowRequestApproved(patron);
                 int previousBooksOnHoldCount=Patron.getPreviousBooksOnHoldCount(patron);
                 if(previousBooksOnHoldCount<=0)
                 {
                     Shelf.assignShelf();
                     break whileLoop;
                 }
                     
                 else
                 {
                     Patron.deleteApprovedBooks(patron);
                     Shelf.assignShelf();
                     break whileLoop;
                 }
             }
             if(userChoice==0)
             {
                     Patron.deleteApprovedBooks(patron);
                     Shelf.assignShelf();
                     break whileLoop;
             }
         }
     
     }
         
          
     }
     public static void deleteApprovedBooks(Patron patron)
     {
         for(Book approvedBook:patron.approvedBooks)
                     {
                         for(Book book:Resources.books)
                         {
                             for(Book bookCopy:book.bookCopies)
                             {
                                 if(bookCopy.bookID==approvedBook.bookID)
                                 {
                                     bookCopy.bookLocation = "GODOWN";
                                     bookCopy.isAvailable = true;
                                     book.availableCopies++;
                                 }
                             }
                         }
                     }
                     patron.booksOnHold.removeAll(patron.booksOnHold);
                     patron.approvedBooks.removeAll(patron.approvedBooks);
                     patron.booksOnHoldCount=0;
                     patron.previousBooksOnHoldCount=0;
     }
     
     public static int getPreviousBooksOnHoldCount(Patron patron)
     {
         return patron.previousBooksOnHoldCount;
     }
     
}
