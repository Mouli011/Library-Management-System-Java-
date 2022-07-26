 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagementsystem;
import java.util.*;

/**
 *
 * @author mouli011
 */
public class LibraryManagementSystem {

    public static void main(String[] args) {
        //constants
        final int ADMIN = 1;
        final int PATRON = 2;

        int userChoice = 1;
        while (userChoice != Constants.EXIT) {
            System.out.println("\n\n\t\t-------Library Management System-------\n\n");
            System.out.println("Enter the desired Options:");
            System.out.println("\t\t1.Admin\n\t\t2.Patron\n\n\t\t0.To Exit\n\n");
            System.out.println("Enter your choice: ");
            userChoice = Utils.getInt();
            switch (userChoice) {
                case ADMIN:
                    Admin admin = new Admin();
                    admin.intialOptionPage();
                    break;

                case PATRON:
                    Patron patron = new Patron();
                    patron.intialOptionPage();
                    break;

                case Constants.EXIT:
                    break;

                default:
                    System.err.println("Enter appropriate option");
                    break;
            }
        }
    }
}
