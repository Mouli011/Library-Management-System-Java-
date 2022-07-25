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
    public static int returnDateLimit = 14;
    public static double fineFee = 10.0;
    public static double renewalFee = 50;
    public static int noOfRenewalDate = 14;
    public static int maxRenewalCount = 1;
    public static int maximumBookInAShelf = 3;

    public static int getIntValueForRules(String message) {
        System.out.println(message);
        int value = Utils.getInt();
        return Utils.checkNGetPositiveIntValue(value);

    }

    public static double getDoubleValueForRules(String message) {
        System.out.println(message);
        double value = Utils.getDouble();
        return Utils.checkNGetPositiveDoubleValue(value);
    }

}
