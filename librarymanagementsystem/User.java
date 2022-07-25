/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;

import java.util.*;
import java.io.*;
/**
 *
 * @author mouli011
 */
public abstract class User{

    protected static int userIDReference = 1;
    protected int userID;
    protected String userName;
    protected String phoneNumber;
    protected String gender;
    protected Date dateOfBirth;

    protected String password = new String();

    public static int signIn() {

        int userID;
        String password;
        System.out.println("Enter userID: ");
        userID = Utils.getInt();
        System.out.println("Enter password: ");
        password = Utils.getString();
        if (isAValidUserPassword(userID, password)) {
            return userID;
        } else {
            System.out.println("Invalid Username or password");
        }
        return -1;
    }

    public static boolean isAValidUserPassword(int userID, String password) {

        for (User user : Resources.users) {
            if ((user.userID == userID) && (user.password.equals(password))) {
                return true;
            }
        }
        return false;
    }

    public static int getUserId() {
        return userIDReference++;
    }

    abstract public void intialOptionPage();

}
