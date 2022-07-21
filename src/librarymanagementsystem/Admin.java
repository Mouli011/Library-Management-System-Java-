/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;

/**
 *
 * @author mouli011
 */
public class Admin extends User{
    
    
   
    static int updateRulesCount=0;
    
    
    
    Admin(String userName,String phoneNumber,String gender,String dateInput,String password){
        
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.password = password;
        try{
            this.dateOfBirth = Utils.parseDate(dateInput);
        }
        catch(Exception e)
                {
                    System.out.println("Error Occured: " +e);
                }
        userID = 0;
        
    }
    Admin(){
        
    }
    public void displayOption()
    {
        
        
        
        
        int userChoice = 1;
        int signInSuccessfull;
        Admin admin = new Admin("Admin1","9786663130","Male","10-10-1990","pass");
        Resources.admins.add(admin);
        Resources.users.add(admin);
        
        
        if((signInSuccessfull=User.signIn())>=0)
        {
                Admin.afterSignInFunctions(userChoice);
        }
        
        else 
        {
            System.out.println("Invalid Username or password");
        }
        
                
    }
    public static void afterSignInFunctions(int userChoice){
        
        //constants
        final int VIEWBOOKS = 1;
        final int ADDBOOKS = 2;
        final int VIEWPATRONS = 3;
        final int DELETEBOOKS = 4;
        final int UPDATERULES =5;
        final int VIEWPAYMENTS = 6;
        final int EXIT = 0;
        final int VIEWBORROWEDBOOK =7;
        final int LOSTBOOKS = 8;
        
        
        
            while(LibraryManagementSystem.toBoolean(userChoice))
            {
                System.out.println("\n\nEnter Appropriate Options:");
                System.out.println("\t\t1.View Books\n\t\t2.Add Books\n\t\t3.View Patrons\n\t\t4.Delete Books\n\t\t5.Add/Update Rules\n\t\t6.view Payments\n\t\t7.View Borrowed Books\n\t\t8.View Lost Books\n\t\t0.Exit");
                System.out.println("Enter Option: ");
                
                userChoice = Utils.getInt();
                
                switch(userChoice)
                {
                    case VIEWBOOKS:
                        
                        Book.viewBooksOption();
                        break;
                        
                    case ADDBOOKS:
                        Book.displayForAddBooks();
                        Shelf.assignShelf();
                        break;
                        
                    case VIEWPATRONS:
                        Admin admin = new Admin();
                        admin.viewPatrons();
                        break;
                        
                    case DELETEBOOKS:
                        Book bookToBeDeleted = new Book();
                        bookToBeDeleted.deleteBook();
                        break;
                        
                    case UPDATERULES:
                        if(updateRulesCount==0)
                        {
                           Rules.addRules();
                           updateRulesCount++;
                        }
                        else
                        Rules.updateRules();
                        break;
                        
                    case VIEWBORROWEDBOOK:
                        Admin.viewBorrowedBooks();
                        break;
                        
                    
                        
                        
                        
                    case VIEWPAYMENTS:
                        Payment.viewPayments();
                        break;
                        
                    case LOSTBOOKS:
                        Resources.displayLostBooks();
                        break;
                        
                    case EXIT:
                        break;
                        
                    default:
                        System.out.println("Enter Appropriate Options!!");
                        break;
                }
            }
        }
    
    public static void viewPatrons(){
        if(Patron.patronCount!=0)
        {
            System.out.println("------------------------------------------------------------------------------------------------------------------------------");  
             System.out.printf("%5s %20s %15s %15s %20s %15s %15s %15s", "USER ID", "USER NAME","PHONE NUMBER",  "GENDER", "MAIL ID", "DATE OF BIRTH", "BOOKS BORROWED" , "FINE");  
            System.out.println();  
            System.out.println("------------------------------------------------------------------------------------------------------------------------------"); 
        //System.out.println("UserName\t\tPhone Number\t\tGender\t\tDate Of Birth\t\tNumber Of Books Borrowed\t\tFine");
        for(Patron patron:Resources.patrons)
        {
             System.out.printf("%5s %20s %15s %15s %20s %15s %15s %15s", patron.userID, patron.userName,patron.phoneNumber,  patron.gender, patron.mailID,Utils.printDate(patron.dateOfBirth), patron.bookCount,patron.fine);  
            System.out.println();  
            System.out.println("------------------------------------------------------------------------------------------------------------------------------"); 
           // System.out.println(patron.userID+"\t\t"+patron.phoneNumber+"\t\t"+patron.gender+"\t\t"+patron.dateOfBirth+"\t\t"+patron.bookCount+"\t\t"+patron.fine);
        }
        }
        else
        {
            System.out.println("No Patrons Enrolled!!");
        }
    }
    public static void viewBorrowedBooks()
    {
        if(Patron.patronCount!=0)
        {
            System.out.println("Total Number Of Patrons: "+Patron.patronCount);
        for(Patron patron:Resources.patrons)
        {
            if(patron.bookCount>0)
            {
                
                System.out.println("Books Borrowed By Patron: "+patron.userID);
                {
                    patron.displayBorrowedBooks();
                }
            }
            else if(patron.bookCount==0)
            {
                System.out.println("No Book is Borrowed by Patron: "+patron.userID);
            }
        }
    }
    
    else
    {
        System.out.println("No Patrons Enrolled!!");
    }
}
}
