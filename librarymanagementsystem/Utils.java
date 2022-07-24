/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package librarymanagementsystem;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author mouli011
 */
public abstract class Utils {
    
    private static final Scanner  scanner = new Scanner(System.in);
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final Console console = System.console();
  
    
   
    
    public static double getPositiveDoubleValue()
    {
        System.out.println("Enter Non Zero Value!!\n");
        double value = scanner.nextInt();
        return Utils.isPositive(value);
    }
    public static double isPositive(double value)
    {
        if(value<=0)
        {
            value = Utils.getPositiveDoubleValue();
        }
        return value;
    }
    
    public static int getPositiveIntValue()
    {
        System.out.println("Enter Non Zero Value!!\n");
        int value = scanner.nextInt();
        return Utils.isPositive(value);
    }
    public static int isPositive(int value)
    {
        if(value<=0)
        {
            value = Utils.getPositiveIntValue();
        }
        return value;
    }
    
    
    
    static public String getNotNull(String value)
    {
        
            try{System.out.println("This Field cannot be empty!!\nEnter Values: ");
            value = bufferedReader.readLine();
            
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
        
        return Utils.checkNotNull(value);
    }
    
    static public String checkNotNull(String value)
    {
        if(value.length()==0)
        {
            value=Utils.getNotNull(value);
        }
        return value;
    }
    
    
    static public String checkMailID(String value)
    {
        int aliasCount=0;
        int index=0;
        int length = value.length();
        while(index<length)
        {
            if(value.charAt(index)=='@')
            {
                aliasCount++;
            }
            index++;
        }
       
        if((aliasCount==1)&&(value.charAt(length-4)=='.'))
        {
            return value;
        }
        return getMailID(value);
    }
    
    public static String getMailID(String value)
    {
        System.out.println("Enter Valid MailID:");
        value = Utils.getString();
        return Utils.checkMailID(value);
    }
    
    static public String checkPhoneNumberConstraint(String value)
    {
        boolean hasString = false;
        
        int index=0;
        while(index<value.length())
        {
            if(!(((value.charAt(index)>='0')&&(value.charAt(index)<='9'))&&(value.length()==10)))
            {
                
                hasString = true;
                break;
           
            }
            index++;
        }
        if(hasString)
        {
            value = Utils.getPhoneNo();
        }
        return value;
    }
    static public String getPhoneNo()
    {
        String value="";
        try{
            
        
        System.out.println("Enter Valid Phone Number");
        value = bufferedReader.readLine();
        return Utils.checkPhoneNumberConstraint(value);
        }
        
        
        catch(Exception e)
        {
            System.out.println("Error Occurred: "+e);
        }
        return value;
    }   
    
    public static double getDouble()
   {
       double value;
       value=scanner.nextDouble();
       return value;
   }
    
   public static int getInt()
   {
       String stringValue;
       stringValue = Utils.getString();
       stringValue = Utils.checkNotNull(stringValue);
       return Utils.checkHasString(stringValue);
   }
   
   public static int checkHasString(String value)
   {
        int index=0;
        boolean hasString = false;
        while(index<value.length())
        {
            if(!((((value.charAt(index)>='0')&&(value.charAt(index)<='9'))&&(value.length()<9))))
            {
                
                hasString = true;
                break;
           
            }
            
            index++;
        }
        if(hasString)
        {
            System.out.println("Enter Numbers Only!!");
            value = Utils.getString();
            return Utils.checkHasString(value);
        }
        return Integer.parseInt(value);
   }
   
    
    static public  String getString()
    {
        String str = "";
        try{
            str = bufferedReader.readLine();
        }
        catch(IOException e)
            {
              System.out.println(e);
            }
        return str;
    
    }
    
    public static Date parseDate(String value)
    {
        Date date = new Date();
        try {
            date = dateFormat.parse(value);
        } catch (Exception e) {
            System.out.println("Error Occured: "+e);
        }
        return date;
    }
    
    public static String printDate(Date date)
    {
        return dateFormat.format(date);
    }
    
    public static String getGender()
    {
        
        final int MALE = 1;
        final int FEMALE = 2;
        
        System.out.println("\t\t1.Male\n\t\t2.Female");
        int loopExit=1;
        while(LibraryManagementSystem.toBoolean(loopExit))
        {
        
        int userChoice = Utils.getInt();

        switch(userChoice)
        {
            case MALE:
                return "MALE";
            
            case FEMALE:
                return "FEMALE";
            
            default:
                System.out.println("Enter Valid Option!!");
        }
        }
        return "";
    }
    
    public static String checkYear(String value)
    {
        int index=0;
        boolean hasString = false;
        while(index<value.length())
        {
            if(!((((value.charAt(index)>='0')&&(value.charAt(index)<='9'))&&(value.length()==4))))
            {
                
                hasString = true;
                break;
           
            }
            
            index++;
        }
        if(hasString)
        {
            value = Utils.getYear();
        }
        return value;
    }
    


    static public String getYear()
    {
        String value;
        System.out.println("Enter Valid Year(yyyy)!!");
        value=Utils.getString();
        return Utils.checkYear(value);
    }   

}