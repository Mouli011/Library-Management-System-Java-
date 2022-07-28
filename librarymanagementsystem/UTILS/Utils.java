/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.regex.*;
/**
 *
 * @author mouli011
 */
public class Utils {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final Console console = System.console();

    public static double getValidPositiveDoubleValue() {
        System.out.println("Enter Non Zero Value!!");
        double value = scanner.nextDouble();
        return value;
    }

    public static boolean checkPositiveDoubleValue(double value) {
        return value > 0;
    }

    public static int getValidPositiveIntValue() {
        System.out.println("Enter Non Zero Value!!");
        int value = scanner.nextInt();
        return value;
    }

    public static boolean checkPositiveIntValue(int value) {
        return value > 0;
    }

    public static String getNotNull() {

        System.out.println("This Field cannot be empty!!\nEnter Values: ");
        return Utils.getString();
    }

    public static boolean checkNotNull(String value) {
        return value.length() > 0;
    }

    public static boolean checkValidPhoneNumber(String value) {
        return Pattern.matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d", value);
    }

    public static String getValidPhoneNo() {
        System.out.println("Enter Valid Phone Number");
        return Utils.getString();
    }

    public static double getDouble() {
        String stringValue;
        stringValue = Utils.getString();
        while (!(checkHasString(stringValue) && (checkNotNull(stringValue)))) {
            stringValue = getValidDouble();
        }
        return Double.parseDouble(stringValue);
    }

    public static int getInt() {
        String stringValue;
        stringValue = Utils.getString();
        while (!(checkHasString(stringValue) && (checkNotNull(stringValue)))) {
            stringValue = getValidInt();
        }
        return Integer.parseInt(stringValue);
    }

    public static boolean checkHasString(String value) {
        int index = 0;
        boolean hasString = false;
        while (index < value.length()) {
            if (!((((value.charAt(index) >= '0') && (value.charAt(index) <= '9')) && (value.length() < 9)))) {

                hasString = true;
                break;

            }

            index++;
        }
        return !hasString;
    }

    public static String getValidInt() {
        System.out.println("Enter Numbers Only!!");
        return Utils.getString();
    }

    public static String getValidDouble() {

        System.out.println("Enter Numbers Only!!");
        return Utils.getString();
    }

    public static String getString() {
        String str = "";
        try {
            str = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }
        return str;

    }

    public static Date parseDate(String value) {
        Date date = new Date();
        try {
            date = dateFormat.parse(value);
        } catch (Exception e) {
            System.out.println("Error Occured: " + e);
        }
        return date;
    }

    public static String printDate(Date date) {
        return dateFormat.format(date);
    }

    public static String getGender() {

        final int MALE = 1;
        final int FEMALE = 2;
        final int OTHER = 3;

        System.out.println("\t\t1.Male\n\t\t2.Female\n\t\t3.Other");
        int loopExit = 1;
        while (loopExit != Constants.EXIT) {
            int userChoice = Utils.getInt();
            switch (userChoice) {
                case MALE:
                    return Constants.MALE;

                case FEMALE:
                    return Constants.FEMALE;

                case OTHER:
                    return Constants.OTHER;

                default:
                    System.out.println("Enter Valid Option!!");
            }
        }
        return "";
    }

    public static boolean isAValidYear(String value) {
        int index = 0;
        boolean hasString = false;
        while (index < value.length()) {
            if (!((((value.charAt(index) >= '0') && (value.charAt(index) <= '9')) && (value.length() == 4)))) {
                hasString = true;
                break;
            }
            index++;
        }

        return !hasString;
    }

    public static String getValidYear() {
        System.out.println("Enter Valid Year(yyyy)!!");
        return Utils.getString();
    }

    public static boolean isAValidMailID(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9-]+\\.[[a-zA-Z0-9-]+\\.]*[a-zA-Z0-9-]+$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static String getValidMailID() {
        System.out.println("Enter Valid MailID:");
        return Utils.getString();
    }
    
    public static boolean isAValidDate(String value){
        return Pattern.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d",value);
    }
    
    public static String getValidDate() {
            System.out.println("Enter Date In The Format(dd-mm-YYYY)");
            return Utils.getString();
    }

}
