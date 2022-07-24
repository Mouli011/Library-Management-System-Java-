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
    public static int returnDateLimit=14;
    public static double fineFee=10.0;
    public static double renewalFee=50;
    public static int noOfRenewalDate=14;
    public static int maxRenewalCount=1;
    public static int maximumBookInAShelf=3;
    
    
    
    public static int setRulesForInt(String message)
    {
        System.out.println(message);
        int value = Utils.getInt();
        return Utils.isPositive(value);

    }
    
    public static double setRulesForDouble(String message)
    {
        System.out.println(message);
        double value = Utils.getInt();
        return Utils.isPositive(value);
    }
    
}
