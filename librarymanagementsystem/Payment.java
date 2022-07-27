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

public class Payment extends Patron {

    protected static int paymentIDReference = 0;
    protected int paymentID;
    protected String paymentPurpose;//either for renewal or fine
    protected String paymentPlatform;
    boolean paymentResult;
    protected double paymentAmount;
    static Date currentDate = new Date();
    Date paymentDate;
    //static int paymentCount = 0;

    public boolean performPayment(Patron patron) {

        System.out.println("Enter a Method to Pay");
        System.out.println("\t\t1.NETBANKING\n\t\t2.UPI\n\t\t0.CANCEL");
        int userChoice = Utils.getInt();
        switch (userChoice) {
            case Constants.NETBANKING:
                paymentResult = this.paymentInPaymentPlatform(patron, "NET BANKING");
                break;

            case Constants.UPI:
                paymentResult = this.paymentInPaymentPlatform(patron, "UPI");
                break;

            case Constants.CANCEL:
                paymentResult = false;
                break;

            default:
                System.out.println("Enter Valid Option!!");
                paymentResult = false;
                break;
        }
        return paymentResult;
    }

    public static int setPaymentID() {
        return paymentIDReference++;
    }

    public boolean paymentInPaymentPlatform(Patron patron, String message) {
        System.out.println("Amount debitted from your bank through " + message + "!!!");
        paymentID = setPaymentID();
        paymentAmount = patron.fine;
        paymentDate = currentDate;
        paymentPlatform = message;
        return true;
    }
}
