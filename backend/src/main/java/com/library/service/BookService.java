//package com.library.service;
//
//import com.library.models.Author;
//import com.library.models.Book;
//import com.library.repositories.AuthorRepository;
//import com.library.repositories.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookService {
//
//    private final BookRepository bookRepository;
//    private final AuthorRepository authorRepository;
//
//    public BookService(BookRepository bookRepository,
//                       AuthorRepository authorRepository) {
//        this.bookRepository = bookRepository;
//        this.authorRepository = authorRepository;
//    }
//
//    public List<Book> getBooks(){
//        List<Book> books = bookRepository.findAll();
//        List<Author> authors = authorRepository.findAll();
//        if(!books.isEmpty() && !authors.isEmpty()){
//            for (Book book : books){
//
//            }
//        }
//    }
//}
