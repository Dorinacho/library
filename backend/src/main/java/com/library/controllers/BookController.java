package com.library.controllers;

import com.library.models.Book;
import com.library.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library/books")
@CrossOrigin(origins = "http://localhost:8080")
public class BookController {
    private final BookRepository bookRepository;
    Logger logger
            = LoggerFactory.getLogger(BookController.class);

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books;
        books = bookRepository.findAll();
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<Book> getBookById(@PathVariable(name = "id") long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            bookRepository.save(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Book> updateBook(@RequestBody Book bookData, @PathVariable(name = "id") Long bookID) {
        Optional<Book> book = bookRepository.findById(bookID);
        if (book.isPresent()) {
            Book _book = book.get();
            _book.setAvailability(bookData.getAvailability());
            _book.setAuthor(bookData.getAuthor());
            _book.setTitle(bookData.getTitle());
            _book.setIsbn(bookData.getIsbn());
            _book.setLoans(bookData.getLoans());
            _book.setDescription(bookData.getDescription());
            return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable(name = "id") Long bookID) {
        try {
            bookRepository.deleteById(bookID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
