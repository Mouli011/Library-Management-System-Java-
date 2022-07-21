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
public class Patron extends User implements Lists{
    
    static int patronCount =0;
    
    protected int bookCount = 0;
    ArrayList<Book> borrowedBooks = new ArrayList<Book>();
    protected double fine = 0;
    ArrayList<Payment> individualPatronPayments = new ArrayList<Payment>();
    protected int renewalCount=0;
    String mailID;
    
    
    public void displayOption()
    {
        int userChoice = 1;
        
        while(LibraryManagementSystem.toBoolean(userChoice))
        {
        System.out.println("\n\n\nEnter Options:");
        System.out.println("\t\t1.SIGN UP - If you are a new user\n\t\t2.SIGN IN - If you have a account\n\t\t0.To Exit");
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
        
        System.out.println("Enter Gender");
        gender = Utils.getString();
        gender = Utils.checkNotNull(gender);
        System.out.println("Enter Mail ID: ");
        mailID = Utils.getString();
        mailID = Utils.checkNotNull(mailID);
        //mailID = Utils.checkMailID(mailID);
        
        System.out.println("Enter Date Of Birth(dd-MM-yyyy): ");
        String dateInput = Utils.getString();
        dateInput = Utils.checkNotNull(dateInput);
        dateOfBirth = Utils.parseDate(dateInput);
       
        userID = User.getUserId();
        
        patrons.add(this);
        users.add(this);
        
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
                
                
        for(User user: users)
        {
            if(user.userID==userID)
            {
            System.out.println("Sign In Successfull!!");
            System.out.println("Welcome "+user.userName);
            }
            
        }
        
        int userChoice = 1;
        while(LibraryManagementSystem.toBoolean(userChoice))
        {
            System.out.println("\n\nEnter Appropriate Options:");
            System.out.println("\t\t1.My Profile\n\t\t2.To Search Books\n\t\t3.To Borrow Books\n\t\t4.To Return Books\n\t\t5.To Donate Books\n\t\t6.To View Payments\n\t\t7.To Renew Books\n\t\t8.If Book is Lost");
            System.out.println("\n\t\t0.To Exit\n\nEnter Your Choice: ");
            
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
                     Book.displayAddBookOptions();
                     Shelf.assignShelf();
                    break;
                
                
                    
                case VIEWPAYMENT:
                    for(Patron viewPatron:patrons)
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
        
        for(Patron patron: patrons)
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
            
            patron.fine=patron.checkFine();
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
        System.out.println("\t\t1.To Search By Book Name\n\t\t2.To Search By Author Name\n\t\t3.To Search By Genre\n\t\t0.Exit");
        System.out.println("Your Option: ");
        userChoice = Utils.getInt();
        switch(userChoice)
        {
            case SEARCHBYNAME:
                System.out.println("Enter Book Name: ");
                bookName = Utils.getString();
                for(Book book:books)
                {
                    for(Book bookCopy:book.bookCopies)
                    {
                    if(((book.bookName.replaceAll("\\s+","").toLowerCase()).contains(bookName.replaceAll("\\s+","").toLowerCase()))) 
                    {
                        
                        if(bookFound==0)
                        {
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHER", "GENRE" ,"BOOK LOCATION");  
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
                userChoice=0;
                continue;
                }
                
                break;
            case SEARCHBYAUTHOR:
                System.out.println("Enter Author Name: ");
                String author = Utils.getString();
                for(Book book:books)
                {
                    for(Book bookCopy:book.bookCopies)
                    {
                    if(((book.author.replaceAll("\\s+","").toLowerCase()).contains(author.replaceAll("\\s+","").toLowerCase()))) 
                    {
                        
                        if(bookFound==0)
                        {
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHER", "GENRE" ,"BOOK LOCATION");  
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
                System.out.println("No Authors Found!!");
                userChoice=0;
                continue;
                }
                
                break;
            case SEARCHBYGENRE:
                System.out.println("Select the Appropriate Genre: ");
                System.out.println("\t\t0.ACTION\n\t\t1.DRAMA\n\t\t2.SCIENCE FICTION\n\t\t3.ROMANCE\n\t\t4.CRIME\n\t\t5.THRILLER\n\t\t6.HORROR\n\t\t7.DOCUMENTARY\n\t\t8.NOVEL\n\t\t9.HISTORY\n\t\t10.OTHER");
                System.out.println("Enter Option: ");
                int genreReference = Utils.getInt();
                String genre = Utils.assignGenre(genreReference);
                for(Book book:books)
                {
                    for(Book bookCopy:book.bookCopies)
                    {
                    if(((book.genre.replaceAll("\\s+","").toLowerCase()).contains(genre.replaceAll("\\s+","").toLowerCase()))) 
                    {
                        
                        if(bookFound==0)
                        {
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHER", "GENRE" ,"BOOK LOCATION");  
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
                System.out.println("No Books Found in this Genre!!");
                userChoice=0;
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
        System.out.println("Enter Options:\n\t\t1.To Borrow Book\n\t\t0.To Exit");
        System.out.println("Enter Your Choice: ");
        userChoice = Utils.getInt();
        while((userChoice!=0)&&(userChoice!=1))
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
            System.out.printf("%5s %20s %20s %20s %20s", "BOOK NO", "BOOK NAME", "AUTHOR", "PUBLISHER", "AVAILABLE COPIES");  
            System.out.println();  
            System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
            //System.out.println("Book No\t\tBook Name\t\tAuthor\t\tPublisher\t\tAvaialable Copies\t\t");

                for(Book book:books)
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
                for(Patron patron: patrons)
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
        int bookID;
        int availableBooks=0;
        
        if(borrowRequest==0)
        {
             System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
             System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHER", "GENRE" ,"BOOK LOCATION");  
            System.out.println();  
             System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  

                for(Book book:books)
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
        }
                if((availableBooks>0)||(borrowRequest==1))
                {
                System.out.println("Enter the ID of the Book which you need from the above List: ");
                bookID = Utils.getInt();
                for(Patron patron: patrons)
                {
            if(((patron.userID)==userID)&&(patron.bookCount<=1)&&(patron.fine==0))
            {
                
                
                Book addedBook = new Book();
                addedBook=addedBook.assignBookCopy(bookID);
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
            
            
        
    }
    /*public void addBookToBorrowedBooks(int bookNo,int bookID)
    {
        for(Book book:books)
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
        System.out.printf("%5s %20s %15s %20s %20s %25s %25s ", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHER", "BORROWED DATE","RETURN DATE DUE");  
        System.out.println();  
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");  
        //System.out.println("Book ID\t\tBook Name\t\tBook No\t\tAuthor\t\tPublisher\t\tBorrowedDate\t\tLast Date To Return the Book");
        for(Book borrowedBook:borrowedBooks)
        {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");  
        System.out.printf("%5s %20s %15s %20s %20s %25s %25s ",borrowedBook.bookID, borrowedBook.bookName,borrowedBook.bookNo, borrowedBook.author, borrowedBook.publisher, Utils.printDate(borrowedBook.borrowedDate),Utils.printDate(borrowedBook.toBeReturnedDate));  
        System.out.println();  
        System.out.println("------------------------------------------------------------------------------------------------------------------------------"); 
         //System.out.println(borrowedBook.bookID+"\t\t"+borrowedBook.bookName+"\t\t"+borrowedBook.bookNo+"\t\t\t"+borrowedBook.author+"\t\t"+borrowedBook.publisher+"\t\t"+Utils.printDate(borrowedBook.borrowedDate)+"\t\t"+Utils.printDate(borrowedBook.toBeReturnedDate));   
        }
    }
    public void returnBooks(int userID)
    {
        int loopExit =1;
        while(LibraryManagementSystem.toBoolean(loopExit))
        {
        Book returnedBook = new Book();
        int bookID;
        int bookMatch =0;
        for(Patron patron:patrons)
        {
            if((patron.userID==userID)&&(patron.bookCount>0))
            {
                patron.displayBorrowedBooks();
                System.out.println("Enter the ID of the book to be returned: \n\t\t0.To Main Page\n");
                bookID = Utils.getInt();
                if(bookID==0)
                {
                    loopExit=0;
                    continue;
                    
                }
                if((patron.userID==userID)&&(patron.bookCount==0))
                {
                System.out.println("No Books to Return!!");
                loopExit=0;
                continue;
                }
                for(Book book:books)
                {
                    for(Book bookCopy:book.bookCopies)
                    {
                        if((bookCopy.bookID==bookID)&&(bookCopy.isAvailable==false))
                        {
                            patron.fine = patron.checkFine();
                            bookMatch++;
                            if(patron.fine==0)
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
                            else 
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
                
                
                if(bookMatch==0)
                {
                    System.out.println("Enter Valid Book ID!!");
                    new Patron().returnBooks(userID);
                    loopExit=0;
                    continue;
                    
                }
                
            }
            
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
                    for(Patron patron:patrons)
                    {
                        if(patron.userID==userID)
                        {
                        payment.paymentPurpose ="Fine for Late Return";
                        patron.individualPatronPayments.add(payment);
                        payments.add(payment);
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
        System.out.format("%5s %20s %15s %25s %20s", "PAYMENT ID", "PAYMENT DATE", "AMOUNT PAID","PAYMENT PLATFORM", "PAYMENT PURPOSE");  
        System.out.println();  
        System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
        
        //System.out.println("PaymentID\t\tPayment Platform\t\tPayment Purpose\t\tAmount Paid\t\tPayment Date");
        for(Payment payment:individualPatronPayments)
        {
            System.out.format("%5s %20s %15s %25s %20s", payment.paymentID, Utils.printDate(payment.paymentDate), payment.paymentAmount,payment.paymentPlatform, payment.paymentPurpose);  
            System.out.println();  
            System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
            //System.out.println(payment.paymentID+"\t\t"+payment.paymentPlatform+"\t\t"+payment.paymentPurpose+"\t\t"+payment.paymentAmount+"\t\t"+Utils.printDate(payment.paymentDate));
        }
        
       
        
        
    }
    public void renewBooks(int userID)
    {
        int loopExit=1;
        while(LibraryManagementSystem.toBoolean(loopExit))
        {
        int bookID;
        int bookMatch=0;
        for(Patron patron:patrons)
        {
            if((patron.userID==userID)&&(patron.renewalCount<maxRenewalCount)&&(patron.bookCount>0))
            {
                patron.displayBorrowedBooks();
                System.out.println("Enter the ID of the book to be renewed: \n\t\t0.To Main Page\n");
                bookID = Utils.getInt();
                if(bookID==0)
                {
                    loopExit=0;
                    continue;
                }
                for(Book book:books)
                {
                    for(Book bookCopy:patron.borrowedBooks)
                    {
                        if(bookCopy.bookID==bookID)
                        {
                            patron.fine = patron.checkFine();
                            bookMatch++;
                            patron.fine = patron.fine + renewalFee;
                            boolean finePayCheck = patron.payRenewal(patron.fine);
                                if(finePayCheck)
                                {
                                   System.out.println("Book Return Date Extended!!");
                                   patron.renewalCount++;
                                   bookCopy.renewBookCopy();
                                   System.out.println("Renewed Return Date: "+Utils.printDate(bookCopy.toBeReturnedDate));
                                   patron.fine = 0;
                                   loopExit=0;
                                   continue;
                                   
                                }
                                else
                                {
                                    System.out.println("Book Not Renewed!!!");
                                    loopExit=0;
                                    continue;
                                }
                        }
                    }
                }
                if(bookMatch==0)
                {
                    System.out.println("Enter Valid Book ID!!");
                    new Patron().renewBooks(userID);
                    loopExit=0;
                    continue;
                }
            }
            else if((patron.userID==userID)&&(patron.renewalCount<maxRenewalCount)&&(patron.bookCount==0))
            {
                System.out.println("No Books to Renew!!");
                loopExit=0;
                continue;
            }
            else if((patron.userID==userID)&&(patron.renewalCount<=maxRenewalCount))
            {
                System.out.println("Renewal Limit Reached");
                loopExit=0;
                continue;
            }
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
        System.out.println("\t\tFine: "+(fine-renewalFee)+"\n\t\tRenewal Fee: "+renewalFee);
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
                    for(Patron patron:patrons)
                    {
                        if(patron.userID==userID)
                        {
                        payment.paymentPurpose ="Return Date Renewal";
                        payment.userID = patron.userID;
                        payment.userName = patron.userName;
                        patron.individualPatronPayments.add(payment);
                        payments.add(payment);
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
     public double checkFine()
     {
         
         double fine = 0;
         
                 this.fine = 0;
                 for(Book borrowedBook:borrowedBooks)
                 {   
                 fine = this.fine +borrowedBook.fineCalculator();
                  }
             
         return fine;
     }
     
     public static void bookLostOptionDisplay(int userID)
     {
         if(Book.bookCount!=0)
         {
         for(Patron patron:patrons)
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
         Book returnedBook = new Book();
         int bookMatch =0;
         for(Book bookLost:this.borrowedBooks)
         {
             if(bookLost.bookID==bookID)
             {
                 this.fine = this.checkFine();
                 this.fine = this.fine + bookLost.bookPrice;
                 bookMatch++;
                 boolean finePayCheck = this.payBookLostFine(this.fine,bookLost.bookPrice);
                 if(finePayCheck)
                 {
                            bookLost.returnBookCopy(); 
                            for(Book book:books)
                            {
                                if(book.bookNo==bookLost.bookNo)
                                        book.availableCopies++;
                            }
                            this.bookCount--;
                            this.fine = 0;
                            returnedBook =bookLost;
                            System.out.println("Fine Paid!!");
                            }
                else
                {
                            System.out.println("Fine Not Paid!!");
                }
                 
             }
             
                 
         }
         this.borrowedBooks.remove(returnedBook);
         
         if(bookMatch==0)
         {
             System.out.println("\n\nEnter Proper Book ID!!\n\n");
             Patron.bookLostOptionDisplay(this.userID);
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
                    for(Patron patron:patrons)
                    {
                        if(patron.userID==userID)
                        {
                        payment.paymentPurpose ="Book Lost Fine";
                        payment.userID = patron.userID;
                        payment.userName = patron.userName;
                        patron.individualPatronPayments.add(payment);
                        payments.add(payment);
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
     
}
