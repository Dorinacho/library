package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/books")
@CrossOrigin(origins = "http://localhost:8080")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void addBook(@RequestBody Book book){
        bookService.addBook(book);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void updateBook(@RequestBody Book book, @PathVariable(name = "id") Long bookID){
        bookService.updateBook(book,bookID);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteBook(@PathVariable(name = "id") Long bookID){
        bookService.deleteBook(bookID);
    }
}
