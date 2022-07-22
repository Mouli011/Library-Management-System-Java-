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
public class BookPopularityComparator implements Comparator<Book> {
    
    @Override
    public int compare(Book book1,Book book2)
    {
        return book2.borrowedCopies-book1.borrowedCopies;
    }
    
}
