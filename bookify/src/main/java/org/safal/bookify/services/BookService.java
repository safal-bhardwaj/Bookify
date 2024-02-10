package org.safal.bookify.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.safal.bookify.models.Book;
import org.safal.bookify.models.Users;
import org.safal.bookify.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookService 
{
    @Autowired
    private BookRepository bookrepository;

    private String image_folder = "D:/Projects/bookify/src/main/books data/cover images/";
    private String book_folder = "D:/Projects/bookify/src/main/books data/books file/";
    

    public Book saveBook(String title , String authorname , String description, String category , MultipartFile epubfile , MultipartFile imagefile, Users user) throws IOException
    {
        String img_path = image_folder + imagefile.getOriginalFilename();
        String book_path = book_folder + epubfile.getOriginalFilename();
        
        Book book = new Book();

        book.setUser(user);
        book.setBook_name(title);
        book.setAuthor_name(authorname);
        book.setDescription(description);
        book.setCategory(category.toLowerCase());
        book.setRating(0);
        book.setNo_of_downloads(0);
        book.setNo_of_ratings(0);

        book.setCover_image_path(img_path);
        book.setBook_file_path(book_path);

        imagefile.transferTo(new File(img_path));
        epubfile.transferTo(new File(book_path));

        return bookrepository.save(book);
    }

    public byte[] getCoverImage(long book_id) throws IOException
    {
       
        Optional<Book> bookimg = bookrepository.findById(book_id);
      
        String imgpath = bookimg.get().getCover_image_path();
        File imgFile = new File(imgpath);
        return Files.readAllBytes(imgFile.toPath());
        
    }

    public byte[] getEpubFile(long book_id) throws IOException {
    Optional<Book> bookOptional = bookrepository.findById(book_id);
    Book book = bookOptional.get();
    book.setNo_of_downloads(book.getNo_of_downloads()+1);
    

    if (bookOptional.isPresent()) {
        String bookFilePath = bookOptional.get().getBook_file_path();
        Path path = Paths.get(bookFilePath);
        bookrepository.save(book);
        return Files.readAllBytes(path);
    } else {
        throw new IOException("Book not found with id: " + book_id);
    }
    }


    
    public void delete(long book_id)
    {
        bookrepository.deleteById(book_id);
    }

    public Optional<Book> getById(long book_id)
    {
        return bookrepository.findById(book_id);
    }

    public List<Book> getAll()
    {
       return bookrepository.findAll();
    }

    public String getBookTitle(long book_id)
    {
        Optional<Book> bookOptional = bookrepository.findById(book_id);
        return bookOptional.get().getBook_name();
    }

    public float getBookRating(long book_id)
    {
        Optional<Book> bookOptional = bookrepository.findById(book_id);
         return bookOptional.get().getRating();
        
    }

    public void setBookRating(long book_id , int new_rating)
    {
        Optional<Book> bookOptional = bookrepository.findById(book_id);
    
    if (bookOptional.isPresent()) {
        Book book = bookOptional.get();
        float totalRatings = book.getNo_of_ratings() * book.getRating();
        totalRatings += new_rating; 
        book.setNo_of_ratings(book.getNo_of_ratings() + 1); 
        float averageRating = totalRatings / book.getNo_of_ratings(); 
        book.setRating(averageRating); 
       
        bookrepository.save(book);
    } else {
      
        System.out.println("Book with ID " + book_id + " not found.");
    }
        
    }

}
