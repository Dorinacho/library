package com.library.repositories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Procedure(procedureName = "searchBooksByTitle")
    List<Book> searchBooksByTitle(String title, int limit, int offset);

    Optional<Book> findByIsbn(String isbn);
}
