package com.library.controllers;

import com.google.common.collect.Lists;
import com.library.models.Book;
import com.library.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library/books")
@CrossOrigin(origins = "http://localhost:8080")
public class BookController {
    private final BookRepository bookRepository;

    //    @Autowired
//    private BookService bookService;
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
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<List<Book>> getBooksPage(@RequestParam("page") int page, @RequestParam("itemNumber") int itemNumber) {
        Pageable firstPageWithTenElements =  PageRequest.of(page, itemNumber);
        List<Book> booksPage = bookRepository.findAll(firstPageWithTenElements).stream().toList();
        if (!booksPage.isEmpty()) {
            return new ResponseEntity<>(booksPage, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/title")
    @Transactional
    public List<Book> searchBooksByTitle(@RequestParam("search") String search,@RequestParam("limit") int limit,@RequestParam("offset") int offset) {
        // Call the search logic to retrieve the search results
//        List<Book> searchResults = bookRepository.findByTitleContainingIgnoreCase(search);


        List<Book> searchResults = bookRepository.searchBooksByTitle(search, limit, offset);

//        return searchResults.subList(offset, Math.min(offset + limit, searchResults.size()));
        return searchResults;
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
