package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LoanService loanService;

    @PersistenceContext
    EntityManager entityManager;

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public void addBook(Book book){
        bookRepository.save(book);
    }

    public void updateBook(Book bookData, Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find book!"));
        book.setAvailability(bookData.getAvailability());
        book.setAuthor(bookData.getAuthor());
        book.setTitle(bookData.getTitle());
        book.setIsbn(bookData.getIsbn());
        book.setLoans(bookData.getLoans());
        bookRepository.save(book);
    }

    public void deleteBook(Long bookID){
//        List<Loan> loans = loanService.getLoans();
//        for(Loan loan: loans){
//            if(loan.getBook().getId().equals(bookID)){
//                loan.setBook(new Book(loan.getBook().getTitle()));
//                loanService.updateLoan(loan, loan.getId());
//            }
//        }
//        entityManager.detach(bookRepository.findById(bookID));
        bookRepository.deleteById(bookID);
    }
}
