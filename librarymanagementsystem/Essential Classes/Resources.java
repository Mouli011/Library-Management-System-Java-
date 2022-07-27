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
    public static int totalBooksOnHoldByAllPatrons = 0;

    public static Date currentDate = new Date();

}
