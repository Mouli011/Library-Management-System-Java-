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
    
    
   /*static public String notNull(String value,String pass)
    {
        char[] returnValue;
        if(value.length()==0)
        {
            try{System.out.println("This Field cannot be empty!!\nEnter Values: ");
            returnValue = console.readPassword();
            value = Utils.charToString(returnValue);
            return Utils.notNull(value);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        return value;
    }*/
    static public String checkMailID(String value)
    {
        String emailRegex = "[A-Z]+[a-zA-Z_]+@\b([a-zA-Z]+.){2}\b?.[a-zA-Z]+";
        boolean isValid = value.matches(emailRegex);
        if(isValid)
        {
            return value;
        }
        else{
            try{
           System.out.println("Enter valid Mail ID");
       
       value = bufferedReader.readLine();
       }
       catch(IOException e)
       {
           System.out.println("Error Occured "+e);
       }
       return Utils.checkMailID(value);
       }
        
        
       /*int index=0;
       int aliasSymbolCount=0;      
       int length = value.length();
       while(index<value.length())
       {
           if((value.length()>0)&&(value.charAt(index)=='@'))
           {
               aliasSymbolCount++;
           }
           index++;
       }
       if(aliasSymbolCount==1&&((((value.charAt(length-2)=='c')&&(value.charAt(length-1)=='o'))&&(value.charAt(length)=='m'))))
       {
           return value;
       }
       else
       {
           try{
           System.out.println("Enter valid Mail ID");
       
       value = bufferedReader.readLine();
       }
       catch(Exception e)
       {
           System.out.println("Error Occured "+e);
       }
       return Utils.checkMailID(value);
       }*/
       
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
       /*int value;
       value=scanner.nextInt();
       return value;*/
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
    
    
    /*static public String charToString(char[] value)
    {
       StringBuffer returnString = new StringBuffer();
        int index=0;
        for(int i=0;i<value.length;i++)
        {  
          returnString = returnString.append(value[i]);
        }
        return returnString.toString();
    }*/
    
    public static String assignGenre(int genreReference)
    {
        final int ACTION=1;
        final int DRAMA=2;
        final int SCIENCEFICTION=3;
        final int ROMANCE=4;
        final int CRIME=5;
        final int THRILLER=6;
        final int HORROR=7;
        final int DOCUMENTARY=8;
        final int NOVEL=9;
        final int HISTORY=10;
        final int OTHER=11;
        
              
        switch(genreReference)
        {
            case ACTION:
                return "ACTION";
                
            case DRAMA:
                return "DRAMA";
                
            case SCIENCEFICTION:
                return "SCIENCEFICTION";
                
            case ROMANCE:
                return "ROMANCE";
                
            case CRIME:
                return "CRIME";
                
            case THRILLER:
                return "THRILLER";
                
            case HORROR:
                return "HORROR";
                
            case DOCUMENTARY:
                return "DOCUMENTARY";
                
            case NOVEL:
                return "NOVEL";
                
            case HISTORY:
                return "HISTORY";
                
            case OTHER:
                return "OTHER";
                
            default:
                String genre=Utils.getGenre();
                return genre;
                
        }
    }
    
    public static String getGenre()
    {
        System.out.println("Enter Proper Option: ");
        int genreReference = scanner.nextInt();
        return Utils.assignGenre(genreReference);
    }
    
    /*public static int setShelfZero()
    {
        for(int rows=0;rows<12;rows++)
        {
            for(int columns=0;columns<Rules.maximumBookInAShelf;columns++)
            {
                Shelf.allShelfs[rows][columns]=0;
            }
        }
        return 1;
    }*/
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
