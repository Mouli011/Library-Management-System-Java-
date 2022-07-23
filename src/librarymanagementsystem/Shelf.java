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
public abstract class Shelf{
    
    final static int TOTALGENRE = 11;
    
    static int[][] allShelfs = new int[(TOTALGENRE+1)][Rules.maximumBookInAShelf];
    static ArrayList<Integer> garage = new ArrayList<>();

    public Shelf() {
    }
    
    public static void callSetShelfWRTGenre(Book bookToBeAllotted){
        int shelfAllotted = 0;
        int rows;
        switch(bookToBeAllotted.genre)
        {
            case ACTION:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,0,shelfAllotted);
                break;
            case DRAMA:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,1,shelfAllotted);
                break;
            case SCIENCEFICTION:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,2,shelfAllotted);
                break;
            case ROMANCE:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,3,shelfAllotted);
                break;
            case CRIME:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,4,shelfAllotted);
                break;
            case THRILLER:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,5,shelfAllotted);
                break;
            case HORROR:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,6,shelfAllotted);
                break;
            case DOCUMENTARY:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,7,shelfAllotted);
                break;
            case NOVEL:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,8,shelfAllotted);
                break;
            case HISTORY:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,9,shelfAllotted);
                break;
            case OTHER:
                shelfAllotted = Shelf.setShelf(bookToBeAllotted,10,shelfAllotted);
                    break;
        }
        if(shelfAllotted==0)
                {
                    shelfAllotted = Shelf.setShelf(bookToBeAllotted,11,shelfAllotted);
                    if(shelfAllotted==0)
                    {
                        bookToBeAllotted.bookLocation = "GODOWN";
                    }
                }
    }
    
    public static void assignShelf()
    {
        for(Book book:Resources.books)
        {
            for(Book bookCopy:book.bookCopies)
            {
                if((bookCopy.bookLocation.equals("")))
                {
                Shelf.callSetShelfWRTGenre(bookCopy);
               }
            }
        }
    }
    
    public static String getBookPosition(int bookID)
    {
        
                for(int rows=0;rows<(TOTALGENRE+1);rows++)
                {
                   for(int columns=0;columns<Rules.maximumBookInAShelf;columns++) 
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
        for(int rows=0;rows<(TOTALGENRE+1);rows++)
                {
                   for(int columns=0;columns<Rules.maximumBookInAShelf;columns++) 
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
                
                if((((bookCopy.bookLocation.equals("GODOWN"))||(bookCopy.bookLocation.equals("")))))
                {
                    Shelf.callSetShelfWRTGenre(bookCopy);
                    if(bookCopy.bookLocation.equals("SHELF"))
                    {
                        garage.remove(Integer.valueOf(bookCopy.bookID));
                    }
                }
            }
        }
    }
    
    public static void removeCopiesOfBookShelfOnly(int bookID)
    {
        for(int rows=0;rows<(TOTALGENRE+1);rows++)
                {
                   for(int columns=0;columns<Rules.maximumBookInAShelf;columns++) 
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
                    for(int rows=0;rows<(TOTALGENRE+1);rows++)
                    {
                     for(int columns=0;columns<Rules.maximumBookInAShelf;columns++) 
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
    
    public static int setShelf(Book bookToBeAllotted,int shelf,int shelfAllotted)
    {
        for(int columns=0;columns<Rules.maximumBookInAShelf;columns++)   
                    {
                        if(allShelfs[shelf][columns]==0)
                        {
                            allShelfs[shelf][columns]=bookToBeAllotted.bookID;
                            shelfAllotted++;
                            bookToBeAllotted.bookLocation="SHELF";
                            return shelfAllotted;
                        }
                    }
        return shelfAllotted;
    }
    
}
