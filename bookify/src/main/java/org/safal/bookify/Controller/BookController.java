package org.safal.bookify.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.safal.bookify.models.Book;
import org.safal.bookify.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.safal.bookify.services.UserService;



@RestController
public class BookController 
{
    @Autowired
    BookService bookService ;

    @Autowired
    UserService userService;

    @GetMapping("/books")
    public List<Book> retriveAllBooks()
    {
        return bookService.getAll();
    }

    @GetMapping("/books/{book_id}")
    public Optional<Book> retriveBookById(long book_id)
    {
        return bookService.getById(book_id);
    }

    @PostMapping("/books/upload")
   public ResponseEntity<String> upload( @RequestParam ("epubFile") MultipartFile epubFile,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("bookName") String bookName,
            @RequestParam("authorName") String authorName,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("user_id") long user_id) throws IOException
    {
        Book saveBook = bookService.saveBook(bookName, authorName, description, category, epubFile, imageFile, userService.getById(user_id).get());
        if(saveBook!=null)
        {
            return ResponseEntity.ok("Upload Successfull");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Upload Unsuccessful");
        }
    }

  
    @GetMapping("/books/coverimage/{book_id}")
    public ResponseEntity<?> downloadCoverImage(@PathVariable long book_id) throws IOException
    {
        byte[] img = bookService.getCoverImage(book_id);
        if(img!=null)
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(img);
        else
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/books/downloadbookfile/{book_id}")
    public ResponseEntity<?> downloadBookFile(@PathVariable long book_id) throws IOException
    {
        byte[] epubContent = bookService.getEpubFile(book_id);
    if (epubContent != null) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "" + bookService.getBookTitle(book_id) + ".epub");
        return new ResponseEntity<>(epubContent, headers, HttpStatus.OK);
    } else {
        return ResponseEntity.notFound().build();
    }
    }

    @GetMapping("/books/getratings/{book_id}")
    public float getRatings(@PathVariable long book_id) throws IOException
    {
        float ratings = bookService.getBookRating(book_id);
        return ratings;
    }

    @PostMapping("/books/setratings/{book_id}")
    public ResponseEntity<String> setRatings(@PathVariable long book_id , @RequestParam("rating") int rating) throws IOException
    {
        bookService.setBookRating(book_id, rating);
        return ResponseEntity.ok("Ratings Set Successsful");
    }

    @GetMapping("/books/numberofdownloads/{book_id}")
    public long getnoOfDownloads(@PathVariable long book_id)
    {
        return bookService.getnoOfDownloads(book_id);
    }
    
    
    
    
   
    
    
    
            
    
    

    
}
