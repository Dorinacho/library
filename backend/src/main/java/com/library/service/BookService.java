package com.library.service;

import com.library.exceptions.ResourceNotFoundException;
import com.library.models.Book;
import com.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Book bookData, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find book!"));
        book.setAvailability(bookData.getAvailability());
        book.setAuthor(bookData.getAuthor());
        book.setTitle(bookData.getTitle());
        book.setIsbn(bookData.getIsbn());
        book.setLoans(bookData.getLoans());
        book.setDescription(bookData.getDescription());
        bookRepository.save(book);
    }

    public void deleteBook(Long bookID) {
        bookRepository.deleteById(bookID);
    }
}
