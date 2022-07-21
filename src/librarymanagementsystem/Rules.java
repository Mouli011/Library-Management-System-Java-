/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;

/**
 *
 * @author mouli011
 */
public class Rules {
    //rules
    public static int returnDateLimit;
    public static double fineFee;
    public static double renewalFee;
    public static int noOfRenewalDate;
    public static int maxRenewalCount;
    public static int maximumBookInAShelf=3;
    
    public static void addRules(){
        System.out.println("Enter the number of days after which the book must be returned: ");
        returnDateLimit = Utils.getInt();
        returnDateLimit = Utils.isPositive(returnDateLimit);
        System.out.println("Fine Amount for late returning Per Day in Rupees: Rs.");
        fineFee = Utils.getDouble();
        fineFee = Utils.isPositive(fineFee);
        System.out.println("Enter fee for Renewal: ");
        renewalFee = Utils.getDouble();
        renewalFee = Utils.isPositive(renewalFee);
        System.out.println("Number of days the book is extend after Renewed: ");
        noOfRenewalDate = Utils.getInt();
        noOfRenewalDate = Utils.isPositive(noOfRenewalDate);
        System.out.println("Maximum times a Patron can renew: ");
        maxRenewalCount = Utils.getInt();
        maxRenewalCount = Utils.isPositive(maxRenewalCount);
        /*System.out.println("Maximum Books a Shelf can accomadate");
        maximumBookInAShelf = Utils.getInt();
        maximumBookInAShelf = Utils.isPositive(maximumBookInAShelf);*/
        
    }
    public static void updateRules()
    {
        //switch case constants
        final int RETURNDATE =1;
        final int FINEAMOUNT =2;
        final int RENEWALFEE = 3;
        final int RENEWALDAYS=4;
        final int RENEWALCOUNT=5;
        //final int SETSHELFCAPACITY=6;
        final int EXIT=0;
        
        int userChoice=1;
         while(LibraryManagementSystem.toBoolean(userChoice))
        {
        System.out.println("Enter: ");
        System.out.println("\t\t1.Update Return Date Limit\n\t\t2.Update Fine For Late Submission\n\t\t3.Update Renewal Fee\n\t\t4.Update Renewal days\n\t\t5.Set Maximum Renewal Count\n\t\t0.Previous Page ");
        System.out.println("Enter appropriate option: ");
        userChoice = Utils.getInt();
         
       switch(userChoice)
       {
          
           
           case RETURNDATE:
               System.out.println("Enter the number of days after which the book must be returned: ");
               returnDateLimit = Utils.getInt();
               returnDateLimit = Utils.isPositive(returnDateLimit);
               break;
               
           case FINEAMOUNT:
               System.out.println("Fine Amount for late returning Per Day in Rupees: Rs.");
               fineFee = Utils.getDouble();
               fineFee=Utils.isPositive(fineFee);
               break;
              
           case RENEWALFEE:
               System.out.println("Enter fee for Renewal: ");
               renewalFee = Utils.getDouble();
               renewalFee = Utils.isPositive(renewalFee);
               break;
               
           case RENEWALDAYS:
               System.out.println("Number of days the book is extend after Renewed: ");
               noOfRenewalDate = Utils.getInt();
               noOfRenewalDate = Utils.isPositive(noOfRenewalDate);
               break;
               
           case RENEWALCOUNT:
               System.out.println("Maximum times a Patron can renew: ");
               maxRenewalCount = Utils.getInt();
               maxRenewalCount = Utils.isPositive(maxRenewalCount);
               break;
               
           /*case SETSHELFCAPACITY:
               System.out.println("Maximum Books a Shelf can accomadate: ");
                maximumBookInAShelf = Utils.getInt();
                maximumBookInAShelf = Utils.isPositive(maximumBookInAShelf);
                break;*/
                       
               
           case EXIT:
               break;
              
           default:
               System.out.println("Enter Valid Options");
               break;
       } 
         
         
            
        }
        
    }
}
