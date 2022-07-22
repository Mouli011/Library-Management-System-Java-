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
public class Resources {
    public static ArrayList<Book> books = new ArrayList<Book>();
    public static ArrayList<Patron> patrons = new ArrayList<Patron>();
    public static ArrayList<Admin> admins = new ArrayList<Admin>();
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Payment> payments = new ArrayList<Payment>();
    public static HashSet<Book> lostBooks = new HashSet<Book>();
    
    public static int lostBookCount = 0;
    
    public static void displayLostBooks()
    {
        if(lostBookCount!=0)
        {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");  
        System.out.printf("%5s %20s %15s %20s %20s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE");  
        System.out.println();  
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");  
             //System.out.println("Book ID\t\tBook Name\t\tBook No\t\tAuthor\t\tPublisher\t\tAvailability");
             for(Book bookCopy:lostBooks)
             {
             System.out.printf("%5s %20s %15s %20s %20s %22s", bookCopy.bookID, bookCopy.bookName,bookCopy.bookNo, bookCopy.author, bookCopy.publishedIn, bookCopy.genre);  
            System.out.println();  
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");  
             }
         }
        else
        {
            System.out.println("No Books Lost!!");
        }
    
    }
}
