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
    Date currentDate = new Date();
    Date paymentDate;
    static int paymentCount = 0;
    
    
    public boolean performPayment(Patron patron){
        
        //constants
        final int UPI = 2;
        final int NETBANKING =1;
        final int CANCEL=0;
        
        
        
        System.out.println("Enter a Method to Pay");
        System.out.println("\t\t1.NETBANKING\n\t\t2.UPI\n\t\t0.CANCEL");
        int userChoice = Utils.getInt();
        switch(userChoice)
        {
            case NETBANKING:
                System.out.println("Amount debitted from your bank through netbanking!!");
                paymentID = setPaymentID();
                paymentResult = true;
                paymentPlatform = "NET BANKING";
                paymentAmount = patron.fine;
                paymentDate = currentDate;
                paymentCount++;
                break;
                
            case UPI:
                System.out.println("Amount debitted from your bank through UPI!!");  
                paymentID = this.setPaymentID();
                paymentResult = true;
                paymentPlatform = "UPI";
                paymentAmount = patron.fine;
                paymentDate = currentDate;
                paymentCount++;
                break;
                
            case CANCEL:
                paymentResult = false;
                break;   
                
            default:
                System.out.println("Enter Valid Option!!");
                paymentResult=false;
                break;
               
        }
        return paymentResult;
    }
    public static int setPaymentID()
    {
        return paymentIDReference++;
    }
    public static void viewPayments()
    {
        if(paymentCount!=0)
        {
            System.out.println("-----------------------------------------------------------------------------------------------------------------");  
             System.out.printf("%5s %15s %15s %15s %20s %25s %25s ", "PAYMENT ID", "USER ID","USER NAME",  "AMOUNT PAID", "PAYMENT DATE", "PAYMENT PLATFORM","PAYMENT PURPOSE"  );  
            System.out.println();  
            System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
        //System.out.println("PaymentID\t\tUserID\t\tUsername\t\tAmount Paid\t\tPayment Date\t\tPayment Platform\t\tPayment Purpose");
        for(Payment payment:Resources.payments)
        {
             System.out.printf("%5s %15s %15s %15s %20s %25s %25s ", payment.paymentID, payment.userID,payment.userName,  payment.paymentAmount, Utils.printDate(payment.paymentDate), payment.paymentPlatform,payment.paymentPurpose );  
            System.out.println();  
            System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
            //System.out.println(payment.paymentID+"\t\t"+payment.userID+"\t\t"+payment.userName+"\t\t"+payment.paymentAmount+"\t\t"+Utils.printDate(payment.paymentDate)+"\t\t"+payment.paymentPlatform+"\t\t"+payment.paymentPurpose);
        }
        }
        else
        {
            System.out.println("No Payments Done");
        }
    }
}
