/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author mouli011
 */



public class Book extends Rules{
    
    static int bookCount = 0;
    
    ArrayList<Book> bookCopies = new ArrayList<Book>(); 
    ArrayList<Book> toBeDeletedBooks = new ArrayList<Book>();
    
    
    int bookNo;
    String bookName;
    static int bookIDReference=1;
    int bookID;//unique for every books
    String author;
    String publishedIn;
    int availableCopies;
    int genreReference; 
    String genre;
    boolean isAvailable;
    Date borrowedDate;
    Date toBeReturnedDate;
    int totalCopies;
    double bookPrice;
    String bookLocation="";
    int borrowedCopies=0;
    
    
    
    public Book addBook(int bookNo)
    {
            this.bookNo = bookNo;
            System.out.println("Enter Book Name: ");
            bookName = Utils.getString();
            bookName = Utils.checkNotNull(bookName);
            
            System.out.println("Enter Author: ");
            author = Utils.getString();
            author = Utils.checkNotNull(author);
            
            System.out.println("Enter Published Year: ");
            publishedIn = Utils.getString();
            publishedIn = Utils.checkNotNull(publishedIn);
            publishedIn = Utils.checkYear(publishedIn);
            publishedIn = Utils.checkNotNull(publishedIn);
            
            System.out.println("Enter Total Copies: ");
            totalCopies = Utils.getInt();
            System.out.println("Enter Genre: ");
            System.out.println("\t\t1.ACTION\n\t\t2.DRAMA\n\t\t3.SCIENCE FICTION\n\t\t4.ROMANCE\n\t\t5.CRIME\n\t\t6.THRILLER\n\t\t7.HORROR\n\t\t8.DOCUMENTARY\n\t\t9.NOVEL\n\t\t10.HISTORY\n\t\t11.OTHER");
            genreReference = Utils.getInt();
            genre = Utils.assignGenre(genreReference);
            System.out.println("Enter Book Price: ");
            bookPrice = Utils.getDouble();
            bookID = bookIDReference;
            Book book;
            for(int i=0;i<totalCopies;i++)
            {
                book = new Book();
                book.copyContents(bookNo,bookName,author,publishedIn,bookPrice,genre);
                bookCopies.add(book);
                
            }
            
            availableCopies = totalCopies;
            System.out.println("Book Added Successfully!!");
           
        
        return this;
    }
    
    public void copyContents(int bookNo,String bookName,String author,String publishedIn,double bookPrice,String genre)
    {
     this.bookNo=bookNo;
     this.bookName=bookName;
     this.author=author;
     this.publishedIn=publishedIn;
     this.bookPrice = bookPrice;
     this.genre = genre;
     
     bookID = Book.setBookID();
     isAvailable = true; 
    }
    
    public static int setBookID()
    {
        
        return bookIDReference++;
        
        
    }
    
   /* public void setBookIDAndAvailability()
    {
     this.bookID = Book.setBookID();
     this.isAvailable = true;
    }*/
    
    public static void viewBooksOption()
    {
        Book book = new Book();
       
         if(bookCount!=0)
        {
         book.viewBooks();
        int userChoice;
        System.out.println("\n\nEnter Option\n\t\t1.View copies of the book\n\t\t0.Main Page: ");
        userChoice = Utils.getInt();
        if(userChoice==1)
        {
            book.viewBookCopies();
            
        }
        else if(userChoice!=0&&userChoice!=1){
            System.out.println("Enter Valid Option!!!");
            new Book().viewBooks();
        }
        }
            
        else
        {
            System.out.println("No Books Added!! Add Some Books to view them!!");
        }
        
        
    }
    
    public void viewBooks()
    {
       
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");  
        System.out.printf("%5s %20s %20s %20s %20S %20s %20s", "BOOK NO", "BOOK NAME", "AUTHOR", "PUBLISHED IN","GENRE" , "TOTAL COPIES", "AVAILABLE COPIES");  

        System.out.println();  
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");  
        //System.out.println("Book No\t\tBook Name\t\tAuthor\t\tPublisher\t\tTotal Copies\t\tAvailable Copies");
        for(Book book:Resources.books)
        {
            System.out.format("%5s %20s %20s %20s %22s %15s %15s", book.bookNo, book.bookName, book.author,book.publishedIn, book.genre, book.totalCopies, book.availableCopies);  
            System.out.println();  
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");  
            //System.out.println(book.bookNo+"\t\t   "+book.bookName+"\t\t   "+book.author+"\t\t"+book.publishedIn+"\t\t   "+book.totalCopies+"\t\t\t   "+book.availableCopies+"\t\t");
        }
        
        
      
        
    }
    public void viewBookCopies()
    {
        
        int bookNo;
        System.out.println("Enter the Book Number of which you want to enquire: ");
        bookNo = Utils.getInt();
        
        int bookMatch = 0;
        
       
        for(Book book:Resources.books)
        {
            if(book.bookNo==bookNo&&(book.availableCopies>0))
            {
             bookMatch++;
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");  
             System.out.printf("%5s %20s %15s %20s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"AVAILABILITY","BOOK LOCATION");  
            System.out.println();  
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");  
             //System.out.println("Book ID\t\tBook Name\t\tBook No\t\tAuthor\t\tPublisher\t\tAvailability");
             for(Book bookCopy:book.bookCopies){
             System.out.printf("%5s %20s %15s %20s %20s %22s %15s %15s", bookCopy.bookID, bookCopy.bookName,bookCopy.bookNo, bookCopy.author, bookCopy.publishedIn, bookCopy.genre, bookCopy.isAvailable,Shelf.getBookPosition(bookCopy.bookID));  
            System.out.println();  
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");  
            //System.out.println(bookCopy.bookID+"\t\t"+bookCopy.bookName+"\t\t"+bookCopy.bookNo+"\t\t\t"+bookCopy.author+"\t\t"+bookCopy.publishedIn+"\t\t"+bookCopy.isAvailable);
             }
             
            }
            else if((book.bookNo==bookNo)&&(book.availableCopies<=0))
            {
                bookMatch++;
                System.out.println("No Copy is available for the Book:\n\t\tBook Name:" +book.bookName+"\t\tBook Number:"+book.bookNo);
            }
        }
        
        if(bookMatch==0)
        {
            System.out.println("Enter Valid Book Number!!");
            new Book().viewBookCopies();
        }
            
    }
       
    
    public void displaySearchResult()
    {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");  
        System.out.printf("%5s %20s %20s %20s %20s", "BOOK NO", "BOOK NAME", "AUTHOR", "PUBLISHED IN", "AVAILABLE COPIES");  
        System.out.println();  
        System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
        //System.out.println("Book No\t\tBook Name\t\tAuthor\t\tPublisher\t\tAvailable Copies\t\t");
        this.displaySingleBook();
    }
    public void displaySingleBook()
    {
        System.out.format("%5s %20s %20s %20s %20s", bookNo, bookName, author,publishedIn,availableCopies);  
        System.out.println();  
        System.out.println("-----------------------------------------------------------------------------------------------------------------"); 
        //System.out.println(bookNo+"\t\t\t"+bookName+"\t\t"+author+"\t\t"+publishedIn+"\t\t"+availableCopies+"\t\t");
    }
    /*public static Book assignBook(int bookNo,ArrayList<Book> borrowedBooks)
    {
        int bookMatch=0;
        if(bookCount!=0)
        {
        for(Book book:Resources.books)
        {
            if((book.bookNo==bookNo)&&(book.availableCopies!=0))
            {
                bookMatch++;
                Book addedBook = new Book();
                addedBook=book.assignBookCopy(borrowedBooks);
                if(addedBook.bookID>=0)
                {
                    book.availableCopies--;
                    return addedBook;
                }
                else{
                    System.out.println("No Books Available");
                }
            }
        }
        }
        else
        {
            System.out.println("Sorry!!No Books Available!!");
        }
        if(bookMatch==0)
        {
            System.out.println("Enter Valid Book Number:");
            bookNo = Utils.getInt();
            Book addedBook = new Book();
            addedBook=Book.assignBook(bookNo,borrowedBooks);
            return addedBook;
        }
        Book book = new Book();
        book.bookID=-1;
        return book;
    }*/
    public Book assignBookCopy(int bookID)
    {
        int bookMatch=0;
        for(Book book:Resources.books)
        {
        for(Book bookCopy:book.bookCopies)
        {
            if((bookCopy.bookID==bookID))
            {
                bookMatch++;
                if(bookCopy.isAvailable==true)
                {
                    bookCopy.isAvailable = false;
                    bookCopy.setDateLimit();
                    book.availableCopies--;
                    bookCopy.bookLocation="BORROWED";
                    book.borrowedCopies++;
                    Shelf.removeShelf(book.bookNo,bookCopy.bookID);
                    return bookCopy;
                }
                else
                {
                     System.out.println("Sorry!!No Books Available!!");
                }
            }
        }
        }
        if(bookMatch==0)
        {
            System.out.println("Enter Valid Book ID: ");
            bookID = Utils.getInt();
            Book addedBook = new Book();
            addedBook=addedBook.assignBookCopy(bookID);
            return addedBook;
        }
        Book book = new Book();
        book.bookID=-1;
        return book;
    }
    public void setDateLimit()
    {
       
        System.out.println("Enter the date you Borrow the Book(dd-MM-yyyy)");
        String dateInput = Utils.getString();
        
        borrowedDate = Utils.parseDate(dateInput);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowedDate);
        
        calendar.add(Calendar.DATE,returnDateLimit);
        
        toBeReturnedDate = calendar.getTime();
       
        
    }
    
    public void returnBookCopy()
    {
        try{
        isAvailable = true;
        toBeReturnedDate = Utils.parseDate("30-12-3000");
        borrowedDate = null;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public double fineCalculator()
    {
        Date currentDate = new Date();
        long difference = currentDate.getTime() - toBeReturnedDate.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(difference);
        double fineCalculated;
        if(diffInDays>0)
        {
            fineCalculated = diffInDays * 10;    
        }
        else
        {
            fineCalculated = 0;
        }
            
      return fineCalculated;     
    }
    public void renewBookCopy()
    {
        Date currentDate = new Date();
        
        if(currentDate.compareTo(toBeReturnedDate)<=0)
        {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toBeReturnedDate);
        
        calendar.add(Calendar.DATE,noOfRenewalDate);
        
        toBeReturnedDate = calendar.getTime();
        }
        else{
            Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        
        calendar.add(Calendar.DATE,noOfRenewalDate);
        
        toBeReturnedDate = calendar.getTime();
        
        }
    }
    public void deleteBook()
    {
        int deleteCount = 0;
        int userChoice = 1;
        int confirm;
 
        while(LibraryManagementSystem.toBoolean(userChoice))
        {
            if(bookCount!=0)
        {
        Book book = new Book();
        book.viewBooks();
       
        System.out.println("\n\nEnter Option\n\t\t1.View copies of the book\n\t\t2.Delete a Whole Book \n\t\t0.Main Page");
        userChoice = Utils.getInt();
        while((userChoice!=0)&&(userChoice!=1)&&(userChoice!=2))
        {
            System.out.println("Enter Valid Option!!");
            userChoice = Utils.getInt();
        }
        if(userChoice==1)
        {
            book.viewBookCopies();
            
        }
        else if((userChoice!=0)&&(userChoice!=1)&&(userChoice!=2)){
            System.out.println("Enter Valid Option!!!");
            new Book().viewBooks();
        }
        else if(userChoice==0)
        {
            break;
        }
        if(userChoice==2)
        {
            System.out.println("Enter the Book Number To Be Deleted From The Above List: ");
            int bookNo;
            int bookMatch =0;
            bookNo = Utils.getInt();
            System.out.println("Confirm Deletion:\n\t\t1.Yes\n\t\t0.No");
            confirm = Utils.getInt();
            while((confirm!=0)&&(confirm!=1))
            {
                  System.out.println("Enter Valid Option: ");
                  confirm = Utils.getInt();
            }
            if(confirm==0)
            {
                    System.out.println("Book Not Deleted!!");
                    userChoice =0;
                    continue;
            }
            if(confirm==1)
            {   
            Book bookToBeDeleted = new Book();
            for(Book singleBook:Resources.books)
            {
                if((singleBook.bookNo==bookNo)&&(singleBook.availableCopies==singleBook.totalCopies))
                {
                    bookToBeDeleted = singleBook;
                    deleteCount++;
                    bookMatch++;
                    Shelf.removeBookShelfOnly(bookNo);
                }
                if((singleBook.bookNo==bookNo)&&(singleBook.availableCopies!=singleBook.totalCopies))
                {
                    System.out.println("Book with Book No: "+bookNo+" Is Not Eligible for Deletion!!");
                    bookMatch++;
                }
            }
            boolean bookDeleteResult=Resources.books.remove(bookToBeDeleted);
            if(bookDeleteResult)
            {
                System.out.println("Book with Number: "+bookNo+" is Deleted Successfully!!!");
                Book.bookCount--;
                Shelf.reassignShelf(-1);
            }
            if(bookMatch==0)
            {
                System.out.println("Enter Valid Book Number!!\n\n");
                userChoice=1;
                continue;
            }
            if(deleteCount==0)
            {
            System.out.println("No Books Deleted!!");
            deleteCount=0;
            }
        }
            
        }
        
        if(userChoice==1)
        {
        int bookID;
        String bookIDInString;
        
        System.out.println("\n\nEnter the ID of the books to be deleted(Press Enter After Each ID's): \nPress Enter after Completion\n");
        
        ArrayList<Integer> bookIDList = new ArrayList<Integer>();
        
        do
        {
            bookIDInString=Utils.getString();
            if(bookIDInString.isEmpty())
                break;
            bookID = Integer.parseInt(bookIDInString);
            bookIDList.add(bookID);
        }while(true); 
        
        
        /*for(;;)
        {
            bookID = Utils.getInt();
            if(bookID!=-1)
            {
                bookIDList.add(bookID);
                            
                
            }
            else
            {
                break;
            }
        }*/
           System.out.println("Confirm Deletion:\n\t\t1.Yes\n\t\t0.No");
           confirm = Utils.getInt();
           while((confirm!=0)&&(confirm!=1))
            {
                  System.out.println("Enter Valid Option: ");
                  confirm = Utils.getInt();
            }
            if(confirm==0)
            {
                    System.out.println("Book Not Deleted!!");
                    userChoice =0;
                    continue;
            }
            if(confirm==1)
            {   
        for(Book singlebook:Resources.books)
        {
            
            for(Integer ID:bookIDList)
            {
                for(Book bookCopy:singlebook.bookCopies)
                {
                    if((bookCopy.bookID==ID)&&(singlebook.availableCopies>0)&&(singlebook.totalCopies>0)&&(bookCopy.isAvailable==true))
                    {
                        
                        singlebook.toBeDeletedBooks.add(bookCopy);
                        --singlebook.availableCopies;
                        --singlebook.totalCopies;
                        Shelf.removeCopiesOfBookShelfOnly(bookCopy.bookID);
                        System.out.println("Book with Book ID: "+ID+" Is Deleted!!");
                        deleteCount++;
                       
                        
                    }
                    else if((bookCopy.bookID==ID)&&((singlebook.availableCopies<=0)||(singlebook.totalCopies<=0)||(bookCopy.isAvailable==false)))
                    {
                        System.out.println("Book with Book ID: "+ID+" Is Not Eligible for Deletion!!");
                    }
                    
                    

                }
            }
        }
           
            
        }
        for(Book singleBook:Resources.books)
        {
  
            singleBook.bookCopies.removeAll(singleBook.toBeDeletedBooks);
            
        }
        
        if(deleteCount==0)
        {
            System.out.println("No Books Deleted!!");
            
        }
        else if(deleteCount>0)
        {
            System.out.println("Number Of Books Deleted: "+deleteCount);
            deleteCount=0;
        }       
        
        for(Book books:Resources.books)
        {
            books.toBeDeletedBooks.removeAll(books.toBeDeletedBooks);
        }
        Shelf.reassignShelf(bookNo);
        }
        System.out.println("Enter 1.Delete more books");
        System.out.println("Enter 0.Main Page");
        System.out.println("Enter appropraite Option: ");
        userChoice = Utils.getInt();  
        
        
        }
        
        else
        {
            System.out.println("No Books Available!!Add Some Books");
            userChoice=0;
        }
        }
        
    }
    /*public static void displayAddBookOptions()
    {
        if(Book.bookCount==0)
        {
            Book bookAdd = new Book();
            bookAdd = bookAdd.addBook();
            Resources.books.add(bookAdd);
            Book.bookCount++;
                        
        }
        else
        {
        System.out.println("Enter\n\t\t1.Add Books\n\t\t2.Add Copies\n\t\t0.Exit");
        int userChoice;
        userChoice=Utils.getInt();
            if(userChoice==1)
            {
                        Book bookAdd = new Book();
                        bookAdd = bookAdd.addBook();
                        Resources.books.add(bookAdd);
                        Book.bookCount++;
                        
            }
            else if(userChoice==2)
            {
                Book.displayForAddBookCopies();
            }
            else if(userChoice==0)
            {
                
            }
            else
            {
                System.out.println("Enter Appropriate Option!!");
                
            }
        }
    }*/
    public static void displayForAddBookCopies(int bookNo)
    {
        int bookCopied=0;
        System.out.println("Enter total copies to be added: ");
        int totalCopiesToBeAdded = Utils.getInt();
        totalCopiesToBeAdded = Utils.isPositive(totalCopiesToBeAdded);
        int bookFound=0;
        for(Book book:Resources.books)
        {
            if(book.bookNo==bookNo)
            {
                for(int i=0;i<totalCopiesToBeAdded;i++)
            {
                Book addBook = new Book();
                addBook.copyContents(book.bookNo,book.bookName,book.author,book.publishedIn,book.bookPrice,book.genre);
                bookCopied++;
                book.bookCopies.add(addBook);
                
            }
                book.totalCopies=book.totalCopies+totalCopiesToBeAdded;
                book.availableCopies=book.availableCopies+totalCopiesToBeAdded;
                bookFound++;
            }
        }
        if((bookFound!=0)&&(bookCopied!=0))
        {
        System.out.println("Copies Added Successfully!!");
        }
        else if((bookFound!=0)&&(bookCopied==0))
        {
            System.out.println("No Copies Added!!");
        }
    }
    
    public void displaySingleBookCopy()
    {
        System.out.printf("%5s %20s %15s %20s %20s %20s %20s", bookID, bookName,bookNo, author, publishedIn, genre,Shelf.getBookPosition(bookID));  
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------"); 
    }
    
    
    public static void displayForAddBooks()
    {
        System.out.println("\n\nEnter Book Number: ");
        int bookNo=Utils.getInt();
        
        int bookFound=0;
        
        for(Book book:Resources.books)
        {
            if(book.bookNo==bookNo)
            {
                bookFound++;
            }
        }
        
        if(bookFound==0)
        {
            Book bookAdd = new Book();
            bookAdd = bookAdd.addBook(bookNo);
            Resources.books.add(bookAdd);
            Book.bookCount++;
        }
        else if(bookFound>0)
        {
            Book.displayForAddBookCopies(bookNo);
        }
    }
    
    public static void filterBooks()
    {
        final int AUTHORNAME=1;
        final int GENRE=2;
        final int POPULARITY=3;
        final int BORROW=4;
        
        System.out.println("Enter\n\t\t1.Filter By Author Name\n\t\t2.Filter by Genre\n\t\t3.Filter By Popularity\n\t\t4.Borrow");
        int userChoice = Utils.getInt();
        int bookFound;
        switch(userChoice)
        {
            case AUTHORNAME:
                bookFound=Book.filterByAuthorName();
                break;
            case GENRE:
                bookFound=Book.filterByGenre();
                break;
            case POPULARITY:
                Book.filterByPopularity();
                bookFound=1;
                break;
            case BORROW:
                bookFound=1;
                break;
                
            default:
                System.out.println("Enter Valid Option!!");
                bookFound=0;
                break;
                
        }
        if(bookFound==0)
        {
         System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
             System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"BOOK LOCATION");  
            System.out.println();  
             System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  

                for(Book book:Resources.books)
                {
                    if(book.availableCopies>0)
                    {
                        for(Book bookCopy:book.bookCopies)
                        {
                            if(bookCopy.isAvailable==true)
                            {
                                bookCopy.displaySingleBookCopy();
                            }
                        }
                    }
                    
                }   
        }
    }
    public static int filterByAuthorName()
    {
        int bookFound=0;
        System.out.println("Enter Author Name: ");
        String author = Utils.getString();
        for(Book book:Resources.books)
        {
            for(Book bookCopy:book.bookCopies)
            {
                if(((book.author.replaceAll("\\s+","").toLowerCase()).contains(author.replaceAll("\\s+","").toLowerCase()))) 
                {
                        
                    if(bookFound==0)
                    {
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"BOOK LOCATION");  
                        System.out.println();  
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------"); 
                        bookCopy.displaySingleBookCopy();
                    }
                    if(bookFound>0)
                    {
                        bookCopy.displaySingleBookCopy();
                    }
                bookFound++;
                }
            }
        }
       
               if(bookFound==0)
                {
                System.out.println("No Authors Found!!");
                }
        return bookFound;
    }
    public static int filterByGenre()
    {
        int bookFound=0;
        System.out.println("Select the Appropriate Genre: ");
        System.out.println("\t\t1.ACTION\n\t\t2.DRAMA\n\t\t3.SCIENCE FICTION\n\t\t4.ROMANCE\n\t\t5.CRIME\n\t\t6.THRILLER\n\t\t7.HORROR\n\t\t8.DOCUMENTARY\n\t\t9.NOVEL\n\t\t10.HISTORY\n\t\t11.OTHER");
        System.out.println("Enter Option: ");
        int genreReference = Utils.getInt();
        String genre = Utils.assignGenre(genreReference);
        for(Book book:Resources.books)
        {
            for(Book bookCopy:book.bookCopies)
            
                if(((book.genre.replaceAll("\\s+","").toLowerCase()).contains(genre.replaceAll("\\s+","").toLowerCase()))) 
                {
                        
                    if(bookFound==0)
                    {
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
                        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"BOOK LOCATION");  
                        System.out.println();  
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------"); 
                        bookCopy.displaySingleBookCopy();
                    }
                if(bookFound>0)
                {
                            bookCopy.displaySingleBookCopy();
                }
                bookFound++;
                }
            
        }
        if(bookFound==0)
                {
                System.out.println("No Books Found in this Genre!!");
                }
        return bookFound;
    }
    public static void filterByPopularity()
    {
        Collections.sort(Resources.books,new BookPopularityComparator());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");  
        System.out.printf("%5s %20s %15s %20s %20s %15s %20s", "BOOK ID", "BOOK NAME","BOOK NO",  "AUTHOR", "PUBLISHED IN", "GENRE" ,"BOOK LOCATION");  
        System.out.println();  
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------"); 
        for(Book book:Resources.books)
                {
                    if(book.availableCopies>0)
                    {
                        for(Book bookCopy:book.bookCopies)
                        {
                            if(bookCopy.isAvailable==true)
                            {
                                bookCopy.displaySingleBookCopy();
                            }
                        }
                    }
                    
                }
        
        Collections.sort(Resources.books,new BookIDComparator());
        
        
    }
}

