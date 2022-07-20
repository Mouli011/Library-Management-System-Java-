/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package librarymanagementsystem;

import java.util.ArrayList;

/**
 *
 * @author mouli011
 */
public interface Lists {
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<Patron> patrons = new ArrayList<Patron>();
    ArrayList<Admin> admins = new ArrayList<Admin>();
    ArrayList<User> users = new ArrayList<User>();
    ArrayList<Payment> payments = new ArrayList<Payment>();
}
