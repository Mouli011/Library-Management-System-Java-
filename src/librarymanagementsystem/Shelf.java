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
public abstract class Shelf extends Rules{
    
    static int[][] allShelfs = new int[12][maximumBookInAShelf];
    static ArrayList<Integer> garage = new ArrayList<>();

    public Shelf() {
    }
    
    public static boolean setBookShelf(Book bookToBeAllotted){
        int shelfAllotted = 0;
        int rows;
        switch(bookToBeAllotted.genre)
        {
            case "ACTION":
                
                rows=0;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "DRAMA":
               rows=1;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "SCIENCEFICTION":
               rows=2;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "ROMANCE":
                rows=3;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "CRIME":
                rows=4;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "THRILLER":
               rows=5;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "HORROR":
               rows=6;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "DOCUMENTARY":
                rows=7;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "NOVEL":
               rows=8;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "HISTORY":
               rows=9;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                break;
            case "OTHER":
               rows=10;
                    for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                    break;
        }
        if(shelfAllotted==0)
                {
                   rows=11;
                   for(int columns=0;columns<maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[rows][columns]==0)
                        {
                            allShelfs[rows][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            return true;
                        }
                    }
                }
        return false;
        
    }
    
    public static void assignShelf()
    {
        for(Book book:Resources.books)
        {
            for(Book bookCopy:book.bookCopies)
            {
                if((bookCopy.bookLocation.equals("")))
                {
               boolean shelfAllotted = Shelf.setBookShelf(bookCopy);
               if(shelfAllotted==false)
               {
                   bookCopy.bookLocation="GODOWN";
                   garage.add(bookCopy.bookID);
               }
               else{
                   bookCopy.bookLocation="SHELF";
               }    
               }
            }
        }
    }
    
    public static String getBookPosition(int bookID)
    {
        
                for(int rows=0;rows<12;rows++)
                {
                   for(int columns=0;columns<maximumBookInAShelf;columns++) 
                   {
                       if(bookID==allShelfs[rows][columns])
                        {
                            return "S"+(rows+1)+" - B"+(columns+1);
                        } 
                   }
                }
                
                for(Book book:Resources.books)
                {
                    for(Book bookCopy:book.bookCopies)
                    {
                        if(bookCopy.bookID==bookID)
                        {
                            return bookCopy.bookLocation;
                        }
                    }
                }
                
            
        return "GODOWN";
    }
    
    public static void removeShelf(int bookNo,int bookID)
    {
        for(int rows=0;rows<12;rows++)
                {
                   for(int columns=0;columns<maximumBookInAShelf;columns++) 
                   {
                       if(bookID==allShelfs[rows][columns])
                        {
                            allShelfs[rows][columns]=0;
                            Shelf.reassignShelf(bookNo);
                        } 
                   }
                }
    }
    
    public static void reassignShelf(int bookNo)
    {
        
        for(Book book:Resources.books)
        {
            for(Book bookCopy:book.bookCopies)
            {
                if((bookCopy.bookNo!=bookNo)&&(((bookCopy.bookLocation.equals("GODOWN"))||(bookCopy.bookLocation.equals("")))))
                    
                {
                    boolean shelfAllotted = Shelf.setBookShelf(bookCopy);
                    if(shelfAllotted)
                    {
                        bookCopy.bookLocation="SHELF";
                        garage.remove(Integer.valueOf(bookCopy.bookID));
                    }
                    
                }
                else if((((bookCopy.bookLocation.equals("GODOWN"))||(bookCopy.bookLocation.equals("")))))
                {
                    boolean shelfAllotted = Shelf.setBookShelf(bookCopy);
                    if(shelfAllotted)
                    {
                        bookCopy.bookLocation="SHELF";
                        garage.remove(Integer.valueOf(bookCopy.bookID));
                    }
                }
            }
        }
    }
    
    public static void removeCopiesOfBookShelfOnly(int bookID)
    {
        for(int rows=0;rows<12;rows++)
                {
                   for(int columns=0;columns<maximumBookInAShelf;columns++) 
                   {
                       if(bookID==allShelfs[rows][columns])
                        {
                            allShelfs[rows][columns]=0;
                        } 
                   }
                }
    }
    
    public static void removeBookShelfOnly(int bookNo)
    {
        
        for(Book book:Resources.books)
        {
            if(book.bookNo==bookNo)
            {
                for(Book bookCopy:book.bookCopies)
                {
                    for(int rows=0;rows<12;rows++)
                    {
                     for(int columns=0;columns<maximumBookInAShelf;columns++) 
                     {
                       if(bookCopy.bookID==allShelfs[rows][columns])
                        {
                            allShelfs[rows][columns]=0;
                        } 
                     }
                    }
                }
            }
        }
    }
    
}
